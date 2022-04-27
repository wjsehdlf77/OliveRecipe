package com.example.oliverecipe.navigation

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.provider.MediaStore
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf

import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.R
import com.example.oliverecipe.databinding.FragmentAddBinding
import com.example.oliverecipe.databinding.FragmentMyAddBinding

import com.example.oliverecipe.navigation.model.DetectionResult
import com.example.oliverecipe.navigation.view.oliveListAdapter
import com.example.oliverecipe.refrigeratoritem.add.MyAddFragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.content_main.*


import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_foodbank.view.*
import kotlinx.android.synthetic.main.fragment_my_add.*

import kotlinx.android.synthetic.main.fragment_my_add.view.*

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import org.tensorflow.lite.support.image.TensorImage

import org.tensorflow.lite.task.vision.detector.ObjectDetector
import org.w3c.dom.Text
import retrofit2.http.Url


import java.io.File
import java.io.IOException

import java.lang.Exception
import java.net.MalformedURLException

import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

private var _binding: FragmentAddBinding? = null
private val binding get() = _binding!!



private const val MAX_FONT_SIZE = 96F
private val listItem = mutableListOf<String>()
private val listgrade = mutableListOf<Float>()
lateinit var ItemName:String



class AddFoodViewFragment : Fragment() {

    lateinit var filePath: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAdd.setOnClickListener {



//            val data = listItem.distinct().toString()

            val result = ItemName

            setFragmentResult("requestKey", bundleOf("bundleKey" to result))

            findNavController().navigate(R.id.action_action_add_to_myAddFragment)  //버튼을 누르면 addFragment로 화면전환합니다.

        }



        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        {
            try {
                val uri = it.data!!.data!!
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val source = ImageDecoder.createSource(mainActivity.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)?.let {
                        val bitmap = resizeBitmap(it, 900f, 0f)
                        val btp = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                        runObjectDetection(btp)

                    }
                } else {
                    MediaStore.Images.Media.getBitmap(mainActivity.contentResolver, uri)?.let {
                        val bitmap = resizeBitmap(it, 900f, 0f)
                        val btp = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                        runObjectDetection(btp)
                    }
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
        binding.btnGallary.setOnClickListener {
            //gallery app........................
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        //카메라후처리
        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        {
            try {


                val file = File(filePath)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val source =
                        ImageDecoder.createSource(mainActivity.contentResolver, Uri.fromFile(file))
                    ImageDecoder.decodeBitmap(source)?.let {
                        val bitmap = resizeBitmap(it, 900f, 0f)
                        val btp = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                        runObjectDetection(btp)

                    }
                } else {
                    MediaStore.Images.Media.getBitmap(
                        mainActivity.contentResolver,
                        Uri.fromFile(file)
                    )
                        ?.let {
                            val bitmap = resizeBitmap(it, 900f, 0f)
                            val btp = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                            val ingredient = runObjectDetection(btp)
                            // api call
                            oliveData.getOliveData("ingredient") {
                                view.areyclerView.adapter = oliveListAdapter(it.cOOKRCP01?.row!!)
//
                            }
//                            ingredient = runObjectDetection(btp)
                        }
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }

        binding.btnCamera.setOnClickListener {

            val file = imageFile()
            filePath = file.absolutePath
            val photoURI: Uri = FileProvider.getUriForFile(
                mainActivity,
                "com.example.oliverecipe.fileprovider", file
            )

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            requestCameraFileLauncher.launch(intent)
        }

        binding.btnRaspberry.setOnClickListener {
            binding.imageView.visibility = View.VISIBLE
            val url = "http://172.30.1.22:8000/mjpeg/snapshot"

            try {
                CoroutineScope(Dispatchers.Main).launch {
                    val bitmap = withContext(Dispatchers.IO) {
                        imageLoader.loadImage(url)
                    }
                    runObjectDetection(bitmap)
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(),"연결 오류",Toast.LENGTH_SHORT).show()
            }
        }
        }


    private fun imageFile(): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            mainActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        var file = File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
        return file

    }


    private fun runObjectDetection(bitmap: Bitmap): String{
        // Step 1: Create TFLite's TensorImage object
        val image = TensorImage.fromBitmap(bitmap)

        // Step 2: Initialize the detector object
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(1)
            .setScoreThreshold(0.8f)
            .build()
        val detector = ObjectDetector.createFromFileAndOptions(
            mainActivity,
            "model.tflite",
            options
        )

        // Step 3: Feed given image to the detector
        val results = detector.detect(image)

        // Step 4: Parse the detection result and show it
        val resultToDisplay = results.map {
            // Get the top-1 category and craft the display text
            val category = it.categories.first()

            val text = "${category.label}"

            // Create a data object to display the detection result
            DetectionResult(it.boundingBox, text, category)
        }
        // Draw the detection result on the bitmap and show it.
        val imgWithResult = drawDetectionResult(bitmap, resultToDisplay)
        mainActivity.runOnUiThread {

            Glide.with(this).load(imgWithResult).into(binding.imageView)
        }
        return results[0].categories[0].displayName
    }
    private fun drawDetectionResult(
       bitmap: Bitmap,
        detectionResults: List<DetectionResult>
    ): Bitmap{
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()

        pen.textAlign = Paint.Align.LEFT

        detectionResults.forEach {
            // draw bounding box
            pen.color = Color.RED
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)


            val tagSize = Rect(0, 0, 0, 0)

            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.YELLOW
            pen.strokeWidth = 2F

            pen.textSize = MAX_FONT_SIZE
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            // adjust the font size so texts are inside the bounding box
            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
//            listItem.add(it.category.label)
            ItemName = it.category.label
        }
//        retrun ingredient
        return outputBitmap
    }


    private fun resizeBitmap(src: Bitmap, size: Float, angle: Float): Bitmap {
        val width = src.width
        val height = src.height
        var newWidth = 0f
        var newHeight = 0f
        if (width > height) {
            newWidth = size
            newHeight = height.toFloat() * (newWidth / width.toFloat())
        } else {
            newHeight = size
            newWidth = width.toFloat() * (newHeight / height.toFloat())
        }
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postRotate(angle);
        matrix.postScale(scaleWidth, scaleHeight)
        val resizedBitmap = Bitmap.createBitmap(src, 0, 0, width, height, matrix, true)
        return resizedBitmap
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    object imageLoader {

        suspend fun loadImage(imageUrl: String): Bitmap {


            val url = URL(imageUrl)
            val stream = url.openStream()

            return BitmapFactory.decodeStream(stream)

        }
    }
}


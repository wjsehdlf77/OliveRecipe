package com.example.oliverecipe.navigation

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.R
import com.example.oliverecipe.databinding.FragmentAddBinding
import com.example.oliverecipe.navigation.model.ItemData
import com.example.oliverecipe.navigation.view.ItemAdapter
import kotlinx.android.synthetic.main.fragment_refrigerator.view.*

import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_add.*
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector



import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private var _binding: FragmentAddBinding? = null

private val binding get() = _binding!!


//lateinit var mainActivity: MainActivity
private const val MAX_FONT_SIZE = 96F

class AddFoodViewFragment : Fragment() {

    lateinit var filePath: String
    private lateinit var itemList: ArrayList<ItemData>
    private lateinit var itemAdapter: ItemAdapter




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

        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            try {
                // inSampleSize 비율 계산, 지정
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )
                val option = BitmapFactory.Options()
                option.inSampleSize = calRatio
                // 이미지 로딩
                var inputStream = mainActivity.contentResolver.openInputStream(it.data!!.data!!)
                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
                inputStream!!.close()
                inputStream = null
                bitmap?.let {
//                    binding.imageView.setImageBitmap(bitmap)
                    runObjectDetection(bitmap)
                } ?: let {
                    Log.d("kkang", "bitmap null")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        binding.btnGallary.setOnClickListener {
            //gallery app........................
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }


        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        {
            val calRatio = calculateInSampleSize(
                Uri.fromFile(File(filePath)),
                resources.getDimensionPixelSize(R.dimen.imgSize),
                resources.getDimensionPixelSize(R.dimen.imgSize)
            )
            val option = BitmapFactory.Options()
            option.inSampleSize = calRatio
            val bitmap = BitmapFactory.decodeFile(filePath, option)

//            bitmap?.let {
//                binding.imageView.setImageBitmap(bitmap)
//            }
            runObjectDetection(bitmap)
        }

        binding.btnCamera.setOnClickListener {
//            val timeStamp: String =
//                SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//            val storageDir: File? =
//                mainActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//            val file = File.createTempFile(
//                "JPEG_${timeStamp}_",
//                ".jpg",
//                storageDir
//            )
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

        itemList = ArrayList()
        itemAdapter = ItemAdapter(requireContext(),itemList)

        val inflter = LayoutInflater.from(requireContext())
        val v = inflter.inflate(R.layout.fragment_refrigerator,null)

        v.mRecycler.layoutManager = LinearLayoutManager(requireContext())
        v.mRecycler.adapter = itemAdapter

        binding.buttonAdd.setOnClickListener {
            val inflter = LayoutInflater.from(requireContext())
            val v = inflter.inflate(R.layout.add_item,null)
            /**set view*/
//            val additemName = v.findViewById<EditText>(R.id.add_item_name)
//            val additemvalid = v.findViewById<EditText>(R.id.add_item_valid)
            val names = binding.addItemName.text.toString()
            val number = binding.addItemValid.text.toString()
            itemList.add(ItemData("재료 이름 : $names","유통 기한 : $number"))
            itemAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(),"재료를 성공적으로 추가하였습니다", Toast.LENGTH_SHORT).show()
        }

    }

    private fun imageFile() :File {
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


    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = mainActivity.contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun runObjectDetection(bitmap: Bitmap) {
        // Step 1: Create TFLite's TensorImage object
        val image = TensorImage.fromBitmap(bitmap)

        // Step 2: Initialize the detector object
        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(5)
            .setScoreThreshold(0.3f)
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
            val text = "${category.label}, ${category.score.times(100).toInt()}%"

            // Create a data object to display the detection result
            DetectionResult(it.boundingBox, text)
        }
        // Draw the detection result on the bitmap and show it.


        val imgWithResult = drawDetectionResult(bitmap, resultToDisplay)
        mainActivity.runOnUiThread {

            Glide.with(this).load(imgWithResult).into(binding.imageView)
        }
    }
    private fun drawDetectionResult(
        bitmap: Bitmap,
        detectionResults: List<DetectionResult>
    ): Bitmap {
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
        }
        return outputBitmap
    }

    data class DetectionResult(val boundingBox: RectF, val text: String)



}


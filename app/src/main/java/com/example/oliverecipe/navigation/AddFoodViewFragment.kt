package com.example.oliverecipe.navigation

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide

import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.R
import com.example.oliverecipe.databinding.FragmentAddBinding

import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_bag.view.*
import kotlinx.android.synthetic.main.fragment_refrigerator.view.*
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category
import org.tensorflow.lite.task.vision.detector.Detection
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import java.io.ByteArrayOutputStream



import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private var _binding: FragmentAddBinding? = null

private val binding get() = _binding!!

private const val MAX_FONT_SIZE = 96F

class AddFoodViewFragment : Fragment() {

    lateinit var filePath: String


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val view = binding.root

        view.buttonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_action_add_to_myAddFragment) //버튼을 누르면 addFragment로 화면전환합니다.
        }

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
            val uri = it.data!!.data!!
//            try {
//                // inSampleSize 비율 계산, 지정
//                val calRatio = calculateInSampleSize(
//                    it.data!!.data!!,
//                    resources.getDimensionPixelSize(R.dimen.imgWidthSize),
//                    resources.getDimensionPixelSize(R.dimen.imgHeightSize)
//                )
//                val option = BitmapFactory.Options()
//                option.inSampleSize = calRatio
//                // 이미지 로딩
                var inputStream = mainActivity.contentResolver.openInputStream(uri)
//                val bitmap = BitmapFactory.decodeStream(inputStream, null, option)
//                inputStream!!.close()
//                inputStream = null
//                bitmap?.let {
////                    binding.imageView.setImageBitmap(bitmap)
//                    runObjectDetection(bitmap)
//                } ?: let {
//                    Log.d("kkang", "bitmap null")
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }


            val ei = ExifInterface(inputStream!!)

            val orientation: Int = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
            val angle = when(orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> 0f
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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
        }
        binding.btnGallary.setOnClickListener {
            //gallery app........................
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(intent)
        }

        //카메라후처리
        val requestCameraFileLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        {
            val ei = ExifInterface(filePath)
            val file = File(filePath)
            val orientation: Int = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
            val angle = when(orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> 270f
                else -> 0f
            }
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val source = ImageDecoder.createSource(mainActivity.contentResolver, Uri.fromFile(file))
                ImageDecoder.decodeBitmap(source)?.let {
                    val bitmap = resizeBitmap(it, 900f, 0f)
                    val btp = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                    runObjectDetection(btp)

                }
            } else {
                MediaStore.Images.Media.getBitmap(mainActivity.contentResolver, Uri.fromFile(file))?.let {
                    val bitmap = resizeBitmap(it, 900f, 0f)
                    val btp = bitmap.copy(Bitmap.Config.ARGB_8888, true)
                    runObjectDetection(btp)
                }
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

//        itemList = ArrayList()
//        itemAdapter = ItemAdapter(requireContext(),itemList)
//
//        val inflter = LayoutInflater.from(requireContext())
//        val v = inflter.inflate(R.layout.fragment_refrigerator,null)
//
//        v.mRecycler.layoutManager = LinearLayoutManager(requireContext())
//        v.mRecycler.adapter = itemAdapter
//
//        binding.buttonAdd.setOnClickListener {
//            val inflter = LayoutInflater.from(requireContext())
//            val v = inflter.inflate(R.layout.add_item,null)
//            /**set view*/
////            val additemName = v.findViewById<EditText>(R.id.add_item_name)
////            val additemvalid = v.findViewById<EditText>(R.id.add_item_valid)
//            val names = binding.addItemName.text.toString()
//            val number = binding.addItemValid.text.toString()
//            itemList.add(ItemData("재료 이름 : $names","유통 기한 : $number"))
//            itemAdapter.notifyDataSetChanged()
//            Toast.makeText(requireContext(),"재료를 성공적으로 추가하였습니다", Toast.LENGTH_SHORT).show()
//        }

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
            DetectionResult(it.boundingBox, text, category)
        }
        // Draw the detection result on the bitmap and show it.


        val imgWithResult = drawDetectionResult(bitmap, resultToDisplay)

//


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
        val listClass = mutableListOf<String>()
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
            listClass.add(it.category.label)
        }
        return outputBitmap
    }

    data class DetectionResult(val boundingBox: RectF, val text: String, val category: Category)


    fun getImageUri(inContext: Context?, inImage: Bitmap?): Uri? {
        val bytes = ByteArrayOutputStream()
        if (inImage != null) {
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        }
        val path = MediaStore.Images.Media.insertImage(
            inContext?.getContentResolver(),
            inImage,
            "Title" + " - " + Calendar.getInstance().getTime(),
            null
        )
        return Uri.parse(path)
    }
    private fun resizeBitmap(src: Bitmap, size: Float, angle: Float): Bitmap {
        val width = src.width
        val height = src.height
        var newWidth = 0f
        var newHeight = 0f
        if(width > height) {
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
}


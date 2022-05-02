package com.example.oliverecipe.navigation.model

import android.graphics.RectF
import org.tensorflow.lite.support.label.Category

data class DetectionResult(
    val boundingBox: RectF,
    val text: String,
    val category: Category
)

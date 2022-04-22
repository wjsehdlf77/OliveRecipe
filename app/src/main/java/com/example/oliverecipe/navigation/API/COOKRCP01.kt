package com.example.oliverecipe.navigation.API
import com.google.gson.annotations.SerializedName

data class COOKRCP01(
    @SerializedName("RESULT")
    val rESULT: RESULT? = null,
    @SerializedName("row")
    val row: List<Row>? = null,
    @SerializedName("total_count")
    val totalCount: String? = null
)
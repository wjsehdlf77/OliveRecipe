package com.example.oliverecipe.navigation.API
import com.google.gson.annotations.SerializedName

data class RESULT(
    @SerializedName("CODE")
    val cODE: String? = null,
    @SerializedName("MSG")
    val mSG: String? = null
)
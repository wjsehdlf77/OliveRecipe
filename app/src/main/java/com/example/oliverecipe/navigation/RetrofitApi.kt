package com.example.oliverecipe.navigation


import android.util.Log
import com.example.oliverecipe.navigation.API.oliverecipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



const val APPID="67a57680871e4140b78d"

interface OliveDataService {
    @GET("$APPID/COOKRCP01/json/1/5/RCP_PARTS_DTLS={VALUE}")
    fun getOliveData(
        @Path("VALUE") ingredient: String
    ): Call<oliverecipe> }

object oliveData{
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://openapi.foodsafetykorea.go.kr/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(OliveDataService::class.java)

    fun getOliveData(ingredient: String): oliverecipe {
        return service.getOliveData(ingredient).execute().body()!!
    }
}







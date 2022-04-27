package com.example.oliverecipe.navigation


import android.util.Log
import com.example.oliverecipe.navigation.API.oliverecipe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query



const val APPID="67a57680871e4140b78d"
interface OliveDataService {
        @GET("$APPID/COOKRCP01/json/1/5")
    fun getOliveData(
            @Query("RCP_PARTS_DTLS") ingredient: String
        ): Call<oliverecipe> }

object oliveData{
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://openapi.foodsafetykorea.go.kr/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(OliveDataService::class.java)

    fun getOliveData(ingredient: String, callback:(oliverecipe) -> Unit ){
        service.getOliveData(ingredient)
            .enqueue(object : Callback<oliverecipe>{

                override fun onResponse(
                    call: Call<oliverecipe>,
                    response: Response<oliverecipe>)
                {
//                    Log.i("URL", call.request().url.toString())
                    if(response.isSuccessful){
                        val data = response.body()
                        callback(data!!) }

                }
                override fun onFailure(call: Call<oliverecipe>, t: Throwable){
                    Log.d("-----", t.toString())
                }
            })
    }
}







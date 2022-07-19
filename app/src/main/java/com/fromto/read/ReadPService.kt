package com.fromto.read

import android.util.Log
import com.fromto.config.Application
import com.fromto.home.HomeView
import com.fromto.retrofit.HomeResponse
import com.fromto.retrofit.ReadResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReadPService (val View : ReadPView){
    val RetrofitInterface = Application.sRetrofit.create(com.fromto.retrofit.RetrofitInterface::class.java)

    fun tryReadLetter(){
        RetrofitInterface.readingLetter().enqueue(object : Callback<ReadResponse> {
            override fun onResponse(call: Call<ReadResponse>, response: Response<ReadResponse>) {
                var result : ReadResponse = response.body()!!
                val resp = response.body()!!

                View.onLoading()
                Log.d("ReadLetter/API", result.toString())

                when(resp.code){
                    1000 -> { //성공
                        View.onReadingLetterSuccess(response.body() as ReadResponse)
                        Log.d("ReadLetter", "성공")}
                    2000 -> {
                        Log.d("ReadLetter", "JWT 토큰을 입력해주세요.")}
                    3000 -> {
                        Log.d("ReadLetter", "JWT 토큰 검증 실패")}
                }}

            override fun onFailure(call: Call<ReadResponse>, t: Throwable) {
                View.onReadingLetterFailure(t.message?:"ReadLetter/API-ERROR")
            }
        })

    }

}
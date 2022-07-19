package com.fromto.write

import android.util.Log
import com.fromto.config.Application
import com.fromto.retrofit.AuthResponse
import com.fromto.retrofit.MypageResponse
import com.fromto.retrofit.RetrofitInterface
import com.fromto.retrofit.WritePContens
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WritePService (val View : WritePView){
    val RetrofitInterface = Application.sRetrofit.create(RetrofitInterface::class.java)

    fun tryWritingLetter(){
        RetrofitInterface.writingLetter().enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                var result : AuthResponse = response.body()!!
                val resp = response.body()!!

                View.onLoading()
                Log.d("WritingLetter/API", result.toString())

                when(resp.code){
                    1000 -> { //성공
                        View.onWritingLetterSuccess(response.body() as AuthResponse)
                        Log.d("WritingLetter", "성공.")}
                    2000 -> { Log.d("WritingLetter", "JWT 토큰을 입력해주세요.")}
                    3000 -> { Log.d("WritingLetter", "JWT 토큰 검증 실패")}
                }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                View.onWritingLetterFailure(t.message?:"WritingLetter/API-ERROR")
            }
        })
    }

    fun trySendingLetter(writePContens: WritePContens){
        RetrofitInterface.sendingLetter(writePContens).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                var result : AuthResponse = response.body()!!
                val resp = response.body()!!

                Log.d("SendingLetter/API", result.toString())

                when(resp.code){
                    1000 -> { //성공
                        View.onSendingLetterSuccess(response.body() as AuthResponse)
                        Log.d("SendingLetter", "성공.")}
                    else -> {
                        Log.d("SendingLetter", result.toString())}
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                View.onSendingLetterFailure(t.message?:"SendingLetter/API-ERROR")
                Log.d("SendingLetter/API-ERROR", t.toString())
            }
        })
    }



}
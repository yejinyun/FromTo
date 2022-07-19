package com.fromto.reply

import android.util.Log
import com.fromto.config.Application
import com.fromto.retrofit.ReplyResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReplyPService(val View: ReplyPView) {
    val RetrofitInterface = Application.sRetrofit.create(com.fromto.retrofit.RetrofitInterface::class.java)

    fun tryReplyToLetter(replyContents: String) {
        RetrofitInterface.replyToLetter(replyContents).enqueue(object : Callback<ReplyResponse> {
            override fun onResponse(call: Call<ReplyResponse>, response: Response<ReplyResponse>) {
                var result: ReplyResponse? = response.body()

                when(result?.code){
                    1000 -> { //성공
                        View.onReplyToLetterSuccess(response.body() as ReplyResponse)
                        Log.d("ReplyP/API-RESPONSE", result.toString())
                    }
                    2000 -> { //성공
                        Log.d("ReplyP", "JWT 토큰을 입력해주세요.")}
                    3000 -> { //성공
                        Log.d("ReplyP", "JWT 토큰 검증 실패")}
                }
            }
            override fun onFailure(call: Call<ReplyResponse>, t: Throwable) {
                Log.d("ReplyP/API-ERROR", t.toString())
            }
        })
    }
}
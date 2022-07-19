package com.fromto.mailbox

import android.util.Log
import com.fromto.config.Application
import com.fromto.retrofit.MailboxResponse
import com.fromto.retrofit.PosterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MailBoxService (val View: MailboxView) {
    val RetrofitInterface = Application.sRetrofit.create(com.fromto.retrofit.RetrofitInterface::class.java)

    fun tryLetterBox(){
        RetrofitInterface.letterBox().enqueue(object : Callback<MailboxResponse> {
            override fun onResponse(call: Call<MailboxResponse>, response: Response<MailboxResponse>) {
                var result: MailboxResponse? = response.body()
                val resp = response.body()!!

                when(resp.code){
                    1000 -> { //성공
                        View.onLetterBoxSuccess(response.body() as MailboxResponse)
                        Log.d("LetterBox/API-RESPONSE", result.toString())}
                    2000 -> { //성공
                        Log.d("LetterBox", "JWT 토큰을 입력해주세요.")}
                    3000 -> { //성공
                        Log.d("LetterBox", "JWT 토큰 검증 실패")}
                }
            }
            override fun onFailure(call: Call<MailboxResponse>, t: Throwable) {
                Log.d("LetterBox/API-ERROR", t.toString())
            }
        })
    }

}
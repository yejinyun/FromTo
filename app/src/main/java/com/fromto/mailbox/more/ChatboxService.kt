package com.fromto.mailbox.more

import android.util.Log
import com.fromto.config.Application.Companion.prefs
import com.fromto.config.Application.Companion.sRetrofit
import com.fromto.retrofit.ChatboxResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatboxService (val View: ChatboxView) {
    val RetrofitInterface = sRetrofit.create(com.fromto.retrofit.RetrofitInterface::class.java)

    fun tryChatBox(posterIdx: Int) {
        RetrofitInterface.chatBox(posterIdx).enqueue(object : Callback<ChatboxResponse> {
            override fun onResponse(call: Call<ChatboxResponse>, response: Response<ChatboxResponse>) {
                var result: ChatboxResponse? = response.body()
                val resp = response.body()!!

                when(resp.code){
                    1000 -> { //성공
                        View.onChatBoxSuccess(response.body() as ChatboxResponse)
                        Log.d("ChatBox/API-RESPONSE", result.toString())
                    }
                    2000 -> { //성공
                        Log.d("ChatBox", "JWT 토큰을 입력해주세요.")}
                    3000 -> { //성공
                        Log.d("ChatBox", "JWT 토큰 검증 실패")}
                }
            }
            override fun onFailure(call: Call<ChatboxResponse>, t: Throwable) {
                Log.d("ChatBox/API-ERROR", t.toString())
            }
        })
    }
}
package com.fromto.mypage.wirttenmovie

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.fromto.config.Application
import com.fromto.retrofit.MypageResponse
import com.fromto.retrofit.PosterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WrittenService (val View: WrittenView){
    val RetrofitInterface = Application.sRetrofit.create(com.fromto.retrofit.RetrofitInterface::class.java)

    fun tryWrittenPoster(){
        RetrofitInterface.readingMyMovie().enqueue(object : Callback<PosterResponse> {
            override fun onResponse(call: Call<PosterResponse>, response: Response<PosterResponse>) {
                var result: PosterResponse? = response.body()
                val resp = response.body()!!

                when(resp.code){
                    1000 -> { //성공
                        View.onWrittenPosterSuccess(response.body() as PosterResponse)
                        Log.d("Written/API-RESPONSE", result.toString())}
                    2000 -> { //성공
                        Log.d("Written", "JWT 토큰을 입력해주세요.")}
                    3000 -> { //성공
                        Log.d("Written", "JWT 토큰 검증 실패")}
                }
        }
            override fun onFailure(call: Call<PosterResponse>, t: Throwable) {
                Log.d("Written/API-ERROR", t.toString())
            }
        })
    }
}
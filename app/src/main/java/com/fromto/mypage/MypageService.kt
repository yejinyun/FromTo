package com.fromto.mypage

import android.util.Log
import com.fromto.config.Application
import com.fromto.retrofit.MypageResponse
import com.fromto.retrofit.RetrofitInterface
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageService(val View: MypageFragment){
    val RetrofitInterface = Application.sRetrofit.create(RetrofitInterface::class.java)

    fun tryWrittenPosterurl(){
        RetrofitInterface.writtenPosterurl().enqueue(object : Callback<MypageResponse> {
            override fun onResponse(call: Call<MypageResponse>, response: Response<MypageResponse>) {
                var result : MypageResponse = response.body()!!
                val resp = response.body()!!

                Log.d("writtenPosterurl/API", result.toString())

                when(resp.code){
                    1000 -> { //성공
                        View.onWrittenPosterSuccess(response.body() as MypageResponse)
                        Log.d("writtenPosterurl", "성공.")}
                    2000 -> { //성공
                        Log.d("writtenPosterurl", "JWT 토큰을 입력해주세요.")}
                    3000 -> { //성공
                        Log.d("writtenPosterurl", "JWT 토큰 검증 실패")}
                }}

            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                View.onWrittenPosterFailure(t.message?:"writtenPoster/API-ERROR")
                Log.d("writtenPoster/API-ERROR", t.toString())
            }
        })
    }

    fun tryUserInfo(){
        RetrofitInterface.userInfo().enqueue(object : Callback<MypageResponse> {
            override fun onResponse(call: Call<MypageResponse>, response: Response<MypageResponse>) {
                var result: MypageResponse = response.body()!!
                View.onuserInfoSuccess(response.body() as MypageResponse)
                Log.d("userInfo/API", result.toString())

            }
            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                View.onuserInfoFailure(t.message?:"userInfo/API-ERROR")
            }
        })
    }

    fun tryChangeProfileImgUrl(img : MultipartBody.Part){
        RetrofitInterface.changeProfileImgUrl(img).enqueue(object : Callback<MypageResponse> {
            override fun onResponse(call: Call<MypageResponse>, response: Response<MypageResponse>) {
                var result : MypageResponse = response.body()!!
                val resp = response.body()!!

                Log.d("ProfileImg/API-RESPONSE", result.toString())

                when(resp.code){
                    1000 -> { //성공
                        Log.d("ProfileImg", "성공.")
                        View.onChangeProfileImgSuccess(response.body() as MypageResponse)
                        Log.d("로그 ",""+response?.body().toString())
                    }
                    2000 -> { //성공
                        Log.d("ProfileImg", "JWT 토큰을 입력해주세요.")}
                    3000 -> { //성공
                        Log.d("ProfileImg", "JWT 토큰 검증 실패")}
                }}

            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
            }
        })
    }
}
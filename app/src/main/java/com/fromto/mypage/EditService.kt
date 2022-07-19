package com.fromto.mypage

import android.util.Log
import com.fromto.config.Application
import com.fromto.retrofit.MypageResponse
import com.fromto.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditService (val View : EditView){
    val RetrofitInterface = Application.sRetrofit.create(RetrofitInterface::class.java)

    fun trySetting(){
        RetrofitInterface.settings().enqueue(object : Callback<MypageResponse> {
            override fun onResponse(call: Call<MypageResponse>, response: Response<MypageResponse>) {
                var result : MypageResponse = response.body()!!
                val resp = response.body()!!

                Log.d("Settings/API", result.toString())

                when(resp.code){
                    1000 -> { //성공
                        View.onSettingsSuccess(response.body() as MypageResponse)
                        Log.d("Settings", "성공.")}
                    2000 -> { //성공
                        Log.d("Settings", "JWT 토큰을 입력해주세요.")}
                    3000 -> { //성공
                        Log.d("Settings", "JWT 토큰 검증 실패")}
                }}

            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                View.onSettingsFailure(t.message?:"Settings/API-ERROR")
                Log.d("Settings/API-ERROR", t.toString())
            }
        })
    }

    fun tryAlarmActive(alarm: Boolean) {
        RetrofitInterface.alarmActive(alarm).enqueue(object : Callback<MypageResponse> {
            override fun onResponse(call: Call<MypageResponse>, response: Response<MypageResponse>) {
                var result : MypageResponse = response.body()!!
                val resp = response.body()!!

                Log.d("Alarm/API-RESPONSE", result.toString())

                when(resp.code){
                    1000 -> { Log.d("Alarm", "ON")
                        View.onAlarmActiveSuccess(response.body() as MypageResponse)
                    }
                    1001 -> { Log.d("Alarm", "OFF")
                        View.onAlarmActiveSuccess(response.body() as MypageResponse)
                    }
                    2000 -> { //성공
                        Log.d("Alarm", "JWT 토큰을 입력해주세요.")}
                    3000 -> { //성공
                        Log.d("Alarm", "JWT 토큰 검증 실패")}
                }}

            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                View.onAlarmActiveFailure(t.message?:"Alarm/API-ERROR")
                Log.d("Alarm/API-ERROR", t.toString())
            }
        })
    }

    fun tryDelAccount() {
        RetrofitInterface.delAccount().enqueue(object : Callback<MypageResponse> {
            override fun onResponse(call: Call<MypageResponse>, response: Response<MypageResponse>) {
                var result : MypageResponse = response.body()!!
                val resp = response.body()!!

                Log.d("DelAccount/API-RESPONSE", result.toString())

                when(resp.code){
                    1000 -> { Log.d("DelAccount", "성공")
                        View.onDelAccountSuccess(response.body() as MypageResponse) }
                    2000 -> { //성공
                        Log.d("DelAccount", "JWT 토큰을 입력해주세요.")}
                    3000 -> { //성공
                        Log.d("DelAccount", "JWT 토큰 검증 실패")}
                }}

            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                Log.d("DelAccount/API-ERROR", t.toString())
            }
        })
    }


}
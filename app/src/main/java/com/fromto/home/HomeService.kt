package com.fromto.home
import android.util.Log
import com.fromto.config.Application
import com.fromto.retrofit.AuthResponse
import com.fromto.retrofit.HomeResponse
import com.fromto.retrofit.MypageResponse
import com.fromto.retrofit.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeService (val View : HomeView){
    val RetrofitInterface = Application.sRetrofit.create(RetrofitInterface::class.java)


    fun tryCheckedLetter(){
        RetrofitInterface.ischeckedLetter().enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                var result : HomeResponse = response.body()!!
                val resp = response.body()!!

                View.onLoading()
                Log.d("CheckedLetter/API", result.toString())

                when(resp.code){
                    1000 -> { //성공
                        View.onCheckedLetterSuccess(response.body() as HomeResponse)
                        Log.d("CheckedLetter", "편지가 있는 경우")}
                    1001 -> { //성공
                        View.onCheckedLetterSuccess(response.body() as HomeResponse)
                        Log.d("CheckedLetter", "편지가 없는 경우")}
                    2000 -> {
                        Log.d("CheckedLetter", "JWT 토큰을 입력해주세요.")}
                    3000 -> {
                        Log.d("CheckedLetter", "JWT 토큰 검증 실패")}
                }}

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                View.onCheckedLetterFailure(t.message?:"CheckedLetter/API-ERROR")
            }
        })
    }
}
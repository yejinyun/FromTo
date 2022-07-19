package com.fromto.login
import android.util.Log
import android.widget.Toast
import com.fromto.config.Application
import com.fromto.retrofit.AuthResponse
import com.fromto.retrofit.MypageResponse
import com.fromto.retrofit.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginService (val View : LoginView) {
    val RetrofitInterface = Application.sRetrofit.create(com.fromto.retrofit.RetrofitInterface::class.java)

    fun tryLogin(user: User){
        RetrofitInterface.login(user).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                var result: AuthResponse? = response.body()
                val resp = response.body()!!

                Log.d("LOGINACT/API-RESPONSE", result.toString())

                when(resp.code){ //성공
                    1000 -> {
                        View.onLoginSuccess(response.body() as AuthResponse)
                        spfManager.saveJwt(resp.result!!.jwt.toString())
                        spfManager.setUserId(resp.result.userId.toString())
                        Log.d("Login", "성공.")
                    } 3003-> { //존재하지 않는 아이디입니다.
                    Log.d("Login", "존재하지 않는 아이디입니다.")
                    } 3004-> { //비밀번호가 일치하지 않습니다.
                    Log.d("Login", "비밀번호가 일치하지 않습니다")
                } else ->  {
                    Log.d("Login", "로그인 실패 : 서버 오류")
                } }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
            }
    })}

    fun tryAutoLogin(){
        RetrofitInterface.autoLogin().enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                var result : AuthResponse = response.body()!!
                val resp = response.body()!!

                Log.d("autoLogin/API", result.toString())

                when(resp.code){
                    1001 -> { //성공
                        View.onAutoLoginSuccess(response.body() as AuthResponse)
                        Log.d("autoLogin", "성공.")}
                    2000 -> { //성공
                        Log.d("autoLogin", "JWT 토큰을 입력해주세요.")}
                    3000 -> { //성공
                        Log.d("autoLogin", "JWT 토큰 검증 실패")}
                }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("autoLogin/API-ERROR", t.toString())
            }
        })
    }
}
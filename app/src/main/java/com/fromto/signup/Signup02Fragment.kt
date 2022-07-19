package com.fromto.signup

import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fromto.config.Application
import com.fromto.databinding.*
import com.fromto.login.spfManager
import com.fromto.retrofit.AuthResponse
import com.fromto.retrofit.RetrofitBuilder
import com.fromto.retrofit.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Signup02Fragment : Fragment() {
    lateinit var binding: FragmentSignup02Binding
    lateinit var sActivity: SignupActivity
    val prefs = Application.prefs

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sActivity = context as SignupActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSignup02Binding.inflate(inflater, container, false)

        initClikckListener()

        return binding.root
    }

    private fun initClikckListener() {
        binding.signupNicknameBtn.setOnClickListener{
            Nicknamecheck() }

        binding.signupFinishBtn.setOnClickListener{
            Signup() }
    }

    private fun Nicknamecheck() {
        val nickname = binding.signupNickname.text.toString()

        RetrofitBuilder.api.nicknamecheck(nickname).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                var result: AuthResponse? = response.body()
                val resp = response.body()!!

                    Log.d("NAMECHECK/API-RESPONSE", result.toString())

                when(resp.code){
                    1000 -> {
                        Toast.makeText(sActivity,"사용가능한 닉네임입니다.", Toast.LENGTH_SHORT).show()
                        prefs.edit().putString("Nickname",binding.signupNickname.text.toString()).apply()
                    }
                    3002 -> {
                        Toast.makeText(sActivity,"사용중인 닉네임입니다.", Toast.LENGTH_SHORT).show()
                        binding.signupNicknameWarnTv.visibility
                    }
                    else -> { Toast.makeText(sActivity,"서버 오류", Toast.LENGTH_SHORT).show() }
                }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("NAMECHECK/API-ERROR", t.toString())
            }
        })}



    //2. 회원가입
    private fun Signup() {
        val id = prefs.getString("Id", "").toString()
        val password = prefs.getString("PS", "").toString()
        val nickname = prefs.getString("Nickname", "").toString()
        val birth = prefs.getString("Birth", "")
        val gender = prefs.getString("Gender", "")

        val user = User(id, password, nickname, birth, gender)

        RetrofitBuilder.api.signUp(user).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()!!

                Log.d("USER-INFO", user.toString())
                Log.d("AUTHEMAIL/API-RESPONSE", response.body().toString())

                when(resp.code){
                    1000 -> { deleteAuthCode() }
                    2001 -> { Toast.makeText(sActivity,"모든 정보를 입력해주세요.",Toast.LENGTH_SHORT).show() }
                    else -> { Toast.makeText(sActivity,"서버 오류",Toast.LENGTH_SHORT).show() }
                }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("AUTHEMAIL/API-ERROR", t.toString())
            }
        })}

    //3. 인증번호 삭제
    private fun deleteAuthCode(){
        val id = prefs.getString("Id", "").toString()

        RetrofitBuilder.api.delAuthCode(id).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                var result: AuthResponse? = response.body()
                val resp = response.body()!!

                Log.d("DEL-CODE/API-RESPONSE", result.toString())

                when(resp.code){
                    1000 -> { //성공
                        Toast.makeText(sActivity,"${prefs.getString("Nickname", "").toString()}님 회원가입이 완료되었습니다!",Toast.LENGTH_SHORT).show()
                        sActivity.changeFragment(1)
                    }
                    else -> { Toast.makeText(sActivity,"서버 오류", Toast.LENGTH_SHORT).show() }
                }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("DEL-CODE/API-ERROR", t.toString())
            }
        })}

}

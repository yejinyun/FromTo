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
import com.fromto.retrofit.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Signup01Fragment : Fragment() {
    lateinit var binding: FragmentSignup01Binding
    lateinit var sActivity: SignupActivity
    val prefs = Application.prefs

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sActivity = context as SignupActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSignup01Binding.inflate(inflater, container, false)

        initClikckListener()

        return binding.root
    }

    private fun initClikckListener() {
        binding.signupEmailSendBtn.setOnClickListener {
            idcheck()
        }

        binding.signupEmailCheckBtn.setOnClickListener{
            AuthcodeCheck()
        }

        binding.signupNextBtn.setOnClickListener {
            if (binding.signupPsEt.text.toString() != binding.signupPsCheckEt.text.toString()) {
                binding.signupPsWarnTv.visibility = View.VISIBLE
                Toast.makeText(sActivity,"비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show()
            }else{
                saveUserSpf()
                sActivity.changeFragment(3)
            }
        }
    }

    private fun saveUserSpf() {
        prefs.edit().putString("PS", binding.signupPsEt.text.toString()).apply()
        prefs.edit().putString("Birth", binding.signupBirthEt.text.toString()).apply()
        prefs.edit().putString("Gender", binding.signupSexEt.text.toString()).apply()
    }

    //1. 계정 중복 확인
    private fun idcheck(){
        val id = binding.signupEmail.text.toString()

        RetrofitBuilder.api.idcheck(id!!).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                var result: AuthResponse? = response.body()
                val resp = response.body()!!

                Log.d("IDCHECK/API-RESPONSE", result.toString())

                when(resp.code){
                    1000 -> { //성공
                        AuthEmail()
                    }
                    3001 -> { Toast.makeText(sActivity,"이미 가입된 이메일입니다.", Toast.LENGTH_SHORT).show() }
                    else -> { Toast.makeText(sActivity,"서버 오류", Toast.LENGTH_SHORT).show() }
                }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("IDCHECK/API-ERROR", t.toString())
            }
        })}

    //2. 인증번호 전송
    private fun AuthEmail(){
        val email = binding.signupEmail.text.toString()
        Log.d("log", email)

        RetrofitBuilder.api.authEmail(email).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()!!

                Log.d("AUTHEMAIL/API-RESPONSE", response.body().toString())

                when(resp.code){
                    1000 -> { Toast.makeText(sActivity,"인증번호가 전송되었습니다.",Toast.LENGTH_SHORT).show() }
                    else -> { Toast.makeText(sActivity,"서버 오류",Toast.LENGTH_SHORT).show() }
                }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("AUTHEMAIL/API-ERROR", t.toString())
            }
        })}

    private fun AuthcodeCheck(){
        val checkcode : Int = Integer.parseInt(binding.signupEmailConfirm.text.toString())
        val email = binding.signupEmail.text.toString()

        RetrofitBuilder.api.authcodeCheck(checkcode, email).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                var result: AuthResponse? = response.body()
                val resp = response.body()!!

                Log.d("CODECHECK/checkcode", checkcode.toString())
                Log.d("CODECHECK/email", email)

                Log.d("CODECHECK/API-RESPONSE", result.toString())

                when(resp.code){
                    1000 -> { Toast.makeText(sActivity,"인증번호가 일치합니다.",Toast.LENGTH_SHORT).show()
                        prefs.edit().putString("Id", binding.signupEmail.text.toString()).apply()
                    }
                    2008 -> { Toast.makeText(sActivity,"인증번호를 입력해주세요.",Toast.LENGTH_SHORT).show() }
                    2009 -> { Toast.makeText(sActivity,"인증번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show() }
                    else -> { Toast.makeText(sActivity,"서버 오류",Toast.LENGTH_SHORT).show() }
                }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("CODECHECK/API-ERROR", t.toString())
            }
        })}

}
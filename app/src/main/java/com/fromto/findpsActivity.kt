package com.fromto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fromto.databinding.ActivitySignupBinding
import com.fromto.databinding.ActiviyFindpsBinding
import com.fromto.login.LoginActivity
import com.fromto.login.spfManager
import com.fromto.retrofit.AuthResponse
import com.fromto.retrofit.ChangePSuser
import com.fromto.retrofit.RetrofitBuilder
import com.fromto.retrofit.User
import com.fromto.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_readmail.*
import kotlinx.android.synthetic.main.activiy_findps.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class findpsActivity : AppCompatActivity() {
    lateinit var binding: ActiviyFindpsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActiviyFindpsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClikckListener()
    }

    private fun initClikckListener() {
        binding.findpsNewpsBtn.setOnClickListener {
            psAuthcodeCheck()
        }
        binding.findpsBtn.setOnClickListener{
            changePs()
        }

        findps_close_iv.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //새 비밀번호 받기
    private fun psAuthcodeCheck(){
        val birth  = binding.findpsBirthEt.inputType
        val gender = binding.findpsGenderEt.inputType
        val id = binding.findpsEmail.text.toString()

        RetrofitBuilder.api.psAuthcodeCheck(birth, gender, id).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp = response.body()!!

                Log.d("PSCODE/API-RESPONSE", response.body().toString())

                when(resp.code){
                    1000 -> { //성공
                        Toast.makeText(this@findpsActivity,"비밀번호가 ${id}로 전송되었습니다.",Toast.LENGTH_SHORT).show()
                    }
                    2014 -> { Toast.makeText(this@findpsActivity,"입력된 정보와 일치하는 계정이 존재하지 않습니다.", Toast.LENGTH_SHORT).show() }
                    else -> { Toast.makeText(this@findpsActivity,"delIdAuthCode 서버 오류", Toast.LENGTH_SHORT).show() }
                }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("PSCODE/API-ERROR", t.toString())
            }
        })
    }

    //1
    private fun changePs(){
        val birth : Int = Integer.parseInt(findps_birth_et.text.toString())
        val checkcode : Int = Integer.parseInt(findps_recievedps.text.toString())
        val gender : Int = Integer.parseInt(findps_gender_et.text.toString())
        val id : String = binding.findpsEmail.text.toString()
        val password1 : String = binding.findpsNewpsEt.text.toString()
        val password2 : String = binding.findpsNewpsCheckEt.text.toString()

        val changePSuser = ChangePSuser(birth,gender,checkcode,id, password1, password2)

        RetrofitBuilder.api.changePs(changePSuser).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                var result: AuthResponse? = response.body()
                val resp = response.body()!!

                Log.d("PS-CHECK/changePSuser", changePSuser.toString())
                Log.d("PS-CHECK/API-RESPONSE", result.toString())

                when(resp.code){
                    1000 -> { //성공
                        delPsAuthCode()
                    }
                    2015 -> { Toast.makeText(this@findpsActivity,"모든 정보를 입력해주세요.", Toast.LENGTH_SHORT).show() }
                    2016 -> { Toast.makeText(this@findpsActivity,"새로 발급된 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show() }
                    2017 -> { Toast.makeText(this@findpsActivity,"새로운 비밀번호가 서로 같지 않습니다.", Toast.LENGTH_SHORT).show() }
                    else -> { Toast.makeText(this@findpsActivity,"psAuthcodeCheck 서버 오류", Toast.LENGTH_SHORT).show() }
                }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("PS-CHECK/API-ERROR", t.toString())
            }
        })
    }

    //2
    private fun delPsAuthCode(){
        val id : String = binding.findpsEmail.text.toString()
        val intent = Intent(this, LoginActivity::class.java)

        RetrofitBuilder.api.delPsAuthCode(id).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                var result : AuthResponse? = response.body()
                val resp = response.body()

                Log.d("email",id)
                Log.d("DEL-CODE/API-RESPONSE", result.toString())

                when(resp?.code){
                    1000 -> { //성공
                        //하고 로그아웃 시키기
                        spfManager.ClearJwt()
                        startActivity(intent)
                        Toast.makeText(this@findpsActivity,"다시 로그인 해주세요!", Toast.LENGTH_SHORT).show()
                    }
                    else -> { Toast.makeText(this@findpsActivity,"delIdAuthCode 서버 오류", Toast.LENGTH_SHORT).show() }
                }}

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("DEL-CODE/API-ERROR", t.toString())
            }
        })
    }
}

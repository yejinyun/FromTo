package com.fromto.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fromto.MainActivity
import com.fromto.config.Application
import com.fromto.databinding.ActivityLoginBinding
import com.fromto.findpsActivity
import com.fromto.retrofit.*
import com.fromto.signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding
    val service = LoginService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        service.tryAutoLogin()
        initClikckListener()
    }

    private fun initClikckListener() {
        binding.loginLoginBtn.setOnClickListener {
            val id: String = binding.loginEmailEt.text.toString()
            val password: String = binding.loginPsEt.text.toString()
            val getUser = User(id, password, null, null,null)
            service.tryLogin(getUser)
        }

        binding.loginSignupTv.setOnClickListener{
            val intent= Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginFindPsTv.setOnClickListener{
            val intent = Intent(this, findpsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onLoading() {
    }

    override fun onLoginSuccess(response: AuthResponse) {
        when(response.code){
            1000 -> {
                startActivity(Intent(this, MainActivity::class.java))
                Log.d("Login", "성공.")}
             3003-> { //존재하지 않는 아이디입니다.
                Toast.makeText(this,"존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show()
            } 3004-> { //비밀번호가 일치하지 않습니다.
                Toast.makeText(this,"비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
            } 200 -> {
                Toast.makeText(this,"200 Error", Toast.LENGTH_SHORT).show()
            } else ->  {
                Toast.makeText(this,"로그인 실패 : 서버 오류", Toast.LENGTH_SHORT).show()
        }}}

    override fun onAutoLoginSuccess(response: AuthResponse) {
        Toast.makeText(this,"자동 로그인 되었습니다", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()}
}


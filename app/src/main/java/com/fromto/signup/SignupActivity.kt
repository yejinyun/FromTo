package com.fromto.signup

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fromto.login.LoginActivity
import com.fromto.R
import com.fromto.databinding.*

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initClikckListener()

    }

    private fun initClikckListener() {
        supportFragmentManager.beginTransaction().replace(R.id.signup_frm, Signup00Fragment())
            .commitAllowingStateLoss()

        binding.signupCloseIv.setOnClickListener{
                changeFragment(1)
            }
        }

    fun changeFragment(index: Int){
        when (index) {
            1->{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent) }

            2 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.signup_frm, Signup01Fragment())
                    .commitAllowingStateLoss()
            }
            3 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.signup_frm, Signup02Fragment())
                    .commitAllowingStateLoss()
            }
        }
    }



//    fun setDataFragment(fragment:Fragment, title:String){
//        val bundle = Bundle()
//        bundle.putString("title", title)
//
//        fragment.arguments = bundle
//        setFragment()
//    }
//
//    fun setFragment(fragment:Fragment){
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace()
//    }
}


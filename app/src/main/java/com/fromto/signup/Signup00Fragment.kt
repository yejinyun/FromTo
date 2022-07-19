package com.fromto.signup

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import com.fromto.R
import com.fromto.databinding.*
import com.fromto.mypage.mypageBottomdialog
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_signup_00.*

class Signup00Fragment : Fragment() {
    lateinit var binding: FragmentSignup00Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSignup00Binding.inflate(inflater, container, false)

        initClikckListener()

        return binding.root
    }

    private fun initClikckListener() {

        binding.signupTermsTv1More.setOnClickListener {
            val bottomDialog = Signup01Dialog()
            bottomDialog.show(childFragmentManager, bottomDialog.tag)
        }

        binding.signupTermsTv2More.setOnClickListener {
            val bottomDialog = Signup02Dialog()
            bottomDialog.show(childFragmentManager, bottomDialog.tag)
        }

        binding.signupCheckUpBtn.setOnClickListener{
            val sActivity = activity as SignupActivity
            sActivity.changeFragment(2)
        }
    }
}

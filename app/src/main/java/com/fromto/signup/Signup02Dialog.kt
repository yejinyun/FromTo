package com.fromto.signup

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fromto.R
import com.fromto.config.Application
import com.fromto.databinding.DialogMypageprofileBinding
import com.fromto.databinding.DialogSignup001Binding
import com.fromto.databinding.DialogSignup002Binding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import okhttp3.MultipartBody.Part as MultipartBodyPart

//interface BottomSheetClickListener {
//    fun onButtomSheetClicked(type: String)
//}

class Signup02Dialog : BottomSheetDialogFragment() {
    lateinit var binding : DialogSignup002Binding
    lateinit var bottomSheetClickListener: BottomSheetClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogSignup002Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
          bottomSheetClickListener = context as BottomSheetClickListener
        } catch(e: Exception){
           Log.e("BottomDialog","onAttatch Error")
         }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClikckListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun initClikckListener() {
    }

}

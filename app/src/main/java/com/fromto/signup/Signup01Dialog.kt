package com.fromto.signup

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fromto.R
import com.fromto.databinding.DialogSignup001Binding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.fromto.mypage.BottomSheetClickListener

interface BottomSheetClickListener {
    fun onButtomSheetClicked(type: String)
}

class Signup01Dialog : BottomSheetDialogFragment() {
    lateinit var binding : DialogSignup001Binding
    lateinit var bottomSheetClickListener: BottomSheetClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogSignup001Binding.inflate(inflater, container, false)
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

package com.fromto.mailbox.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.fromto.MainActivity
import com.fromto.config.Application.Companion.prefs
import com.fromto.databinding.FragmentChatreadBinding
import com.fromto.retrofit.Chatbox
import com.google.gson.Gson
import com.module.GlideApp

class ChatreadFragment : Fragment() {
    lateinit var binding: FragmentChatreadBinding

    val gson: Gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatreadBinding.inflate(inflater, container, false)

        val chatData = arguments?.getString("chatbox")
        val chatbox = gson.fromJson(chatData, Chatbox::class.java)
        Log.d("chatData",chatbox.toString())

        initClikckListener()
        setView(chatbox)
        return binding.root
    }

    private fun initClikckListener() {
        val mActivity = activity as MainActivity

        binding.chatreadCloseIv.setOnClickListener {
            mActivity.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }

        binding.chatreadReportIv.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSexMNLdco-p_NtVXgoW11mcPsKjgwdAI4ijccJcfz1D5DB1FA/viewform?usp=pp_url&entry.938550151=%EC%95%85%EC%84%B1+url"))
            startActivity(intent)
        }

        binding.chatreadSendIv.setOnClickListener {
            mActivity.changeFragment(6)
        }
    }

    private fun setView(chatbox : Chatbox) {
        val posterImg = prefs.getString("chatImg","").toString()
        val letterTitle = prefs.getString("chatTitle","").toString()
        val movieTitle = prefs.getString("chatMovie","").toString()

        GlideApp.with(requireContext()).load(posterImg).centerCrop().into(binding.chatreadPosterIv)
        binding.chatreadMailtitleTv.text = letterTitle
        binding.chatreadMovietitleTv.text = movieTitle
        binding.chatreadMailContentsTv.text = chatbox.contents
        binding.chatreadFromNameTv.text = chatbox.sender
        binding.chatreadToNameTv.text = chatbox.recipient
    }
}

package com.fromto.reply

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.fromto.MainActivity
import com.fromto.config.Application.Companion.prefs
import com.fromto.databinding.FragmentReplypersonalBinding
import com.fromto.retrofit.*

class ReplyPFragment  : Fragment(),ReplyPView {
    lateinit var binding: FragmentReplypersonalBinding
    val service = ReplyPService(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentReplypersonalBinding.inflate(inflater, container, false)

        initClikckListener()
        setView()

        return binding.root
    }

    private fun initClikckListener() {
        val mActivity = activity as MainActivity

        binding.replyPSendIv.setOnClickListener {
            val getReplyContents: String = binding.replyPContentsEt.text.toString()
            Log.d("getReplyPContents",getReplyContents)

            service.tryReplyToLetter(getReplyContents)
            mActivity.changeFragment(1)
        }
    }

    private fun setView() {
        val posterImg = prefs.getString("chatImg","").toString()
        val letterTitle = prefs.getString("chatTitle","").toString()
        val movieTitle = prefs.getString("chatMovie","").toString()
        val recipient = prefs.getString("chatRecipient","").toString()
        val sender = prefs.getString("chatSender","").toString()

        Glide.with(requireContext()).load(posterImg).centerCrop().into(binding.replyPPosterIv)
        binding.replyPMailtitleTv.text = letterTitle
        binding.replyPMovietitleTv.text = movieTitle
        binding.replyPToTv.append(recipient)
        binding.replyPFromTv.append(sender)
    }

    override fun onReplyToLetterSuccess(response: ReplyResponse) {
        when(response.code){
            1000 -> {
                Toast.makeText(requireContext(),"성공적으로 회신되었습니다!", Toast.LENGTH_SHORT).show()
        }
            2028 -> {
                Toast.makeText(requireContext(),"내용을 입력해주세요", Toast.LENGTH_SHORT).show() }

            2000 -> {
                Toast.makeText(requireContext(),"JWT 토큰을 입력해주세요", Toast.LENGTH_SHORT).show() }
            3000 -> {
                Toast.makeText(requireContext(),"JWT 토큰 검증 실패", Toast.LENGTH_SHORT).show() }
        }
    }
}
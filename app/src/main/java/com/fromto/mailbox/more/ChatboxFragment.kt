package com.fromto.mailbox.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fromto.MainActivity
import com.fromto.R
import com.fromto.config.Application.Companion.prefs
import com.fromto.databinding.FragmentMailboxMoreBinding
import com.fromto.retrofit.*
import com.google.gson.Gson
import com.module.GlideApp

class ChatboxFragment : Fragment(), ChatboxView{
    lateinit var binding: FragmentMailboxMoreBinding
    val service = ChatboxService(this)
    val gson : Gson = Gson()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View {
            binding = FragmentMailboxMoreBinding.inflate(inflater, container, false)

            val mailData = arguments?.getString("mailbox")
            val mailbox = gson.fromJson(mailData, Mailbox::class.java)
//            Log.d("maildata",mailbox.toString())

            val posterIdx = mailbox.posterIdx

            setView(mailbox)
            initClickListener()

            service.tryChatBox(posterIdx!!)
            return binding.root
        }

    private fun initClickListener() {
        val mActivity = activity as MainActivity

        binding.mailboxMoreCloseIv.setOnClickListener{
            mActivity.changeFragment(5)
        }
    }

    private fun setView(mailbox : Mailbox) {
        val posterImg = mailbox.imgUrl
        val letterTitle = mailbox.letterTitle
        val movieTitle = mailbox.movieTitle

        prefs.edit().putString("chatImg", posterImg.toString()).apply()
        prefs.edit().putString("chatTitle", letterTitle.toString()).apply()
        prefs.edit().putString("chatMovie",movieTitle.toString()).apply()

        GlideApp.with(requireContext()).load(posterImg).into(binding.mailboxMoreIv)
        binding.mailboxMoreLettertitleTv.text = letterTitle
        binding.mailboxMoreMovietitleTv.text = movieTitle
    }

    private fun setAdapter(chatboxList : ArrayList<Chatbox>){
        val chatBoxAdapter = ChatboxRvAdapter(chatboxList)
        binding.chatboxRv.adapter = chatBoxAdapter
        binding.chatboxRv.layoutManager = LinearLayoutManager(requireContext())
        binding.chatboxRv.setHasFixedSize(false)

        chatBoxAdapter.setMyItemClickListener(object  : ChatboxRvAdapter.MyItemClickListener{
            override fun onItemClick(chatbox: Chatbox) {
                changeChatFragment(chatbox)
            }

            override fun onRemoveAlbum(position: Int) {
//                TODO("Not yet implemented")
            }
        })
    }

    override fun onLoading() {
    }

    override fun onChatBoxSuccess(response: ChatboxResponse) {
        when(response.code){
            1000 -> {
                response?.let { setAdapter(it.result!!) }
            }
            2000 -> {
                Toast.makeText(requireContext(),"JWT 토큰을 입력해주세요", Toast.LENGTH_SHORT).show() }
            3000 -> {
                Toast.makeText(requireContext(),"JWT 토큰 검증 실패", Toast.LENGTH_SHORT).show() }
        }
    }

    private fun changeChatFragment(chatbox: Chatbox) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .add(R.id.main_frm, ChatreadFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val chatboxJson = gson.toJson(chatbox!!)
                    putString("chatbox", chatboxJson)
                }
            })
            .commitAllowingStateLoss()
    }
}

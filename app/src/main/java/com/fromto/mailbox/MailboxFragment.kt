package com.fromto.mailbox

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.fromto.MainActivity
import com.fromto.R
import com.fromto.databinding.FragmentMailboxBinding
import com.fromto.mailbox.more.ChatboxFragment
import com.fromto.retrofit.Mailbox
import com.fromto.retrofit.MailboxResponse
import com.google.gson.Gson

class MailboxFragment : Fragment(), MailboxView{
    lateinit var binding: FragmentMailboxBinding
    val service = MailBoxService(this)

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View {
            binding = FragmentMailboxBinding.inflate(inflater, container, false)

            initClickListener()

            service.tryLetterBox()
            return binding.root
        }

    private fun initClickListener() {
        val mActivity = activity as MainActivity

        binding.mailboxCloseIv.setOnClickListener{
            mActivity.changeFragment(1)
        }
    }

    private fun setAdapter(mailBoxList : ArrayList<List<Mailbox>>){
        val mailboxAdapter = MailBoxRvAdapter(mailBoxList,requireContext())
        binding.mailboxRv.adapter = mailboxAdapter
        binding.mailboxRv.layoutManager = LinearLayoutManager(requireContext())
        binding.mailboxRv.setHasFixedSize(false)

        mailboxAdapter.setMyItemClickListener(object  : MailBoxRvAdapter.MyItemClickListener{
            override fun onItemClick(mailbox: List<Mailbox>) {
                changeMailFragment(mailbox)
            }

            override fun onRemoveAlbum(position: Int) {
//                TODO("Not yet implemented")
            }
        })
    }

    override fun onLoading() {
    }

    override fun onLetterBoxSuccess(response: MailboxResponse) {
        when(response.code){
            1000 -> {
                binding.mailboxEmptyTv.visibility = View.GONE
                response?.let { setAdapter(it.result!!) }
            }
            2000 -> {
                binding.mailboxEmptyTv.append("\n로그인을 해주세요 :)")
                Toast.makeText(requireContext(),"JWT 토큰을 입력해주세요", Toast.LENGTH_SHORT).show() }
            3000 -> {
                Toast.makeText(requireContext(),"JWT 토큰 검증 실패", Toast.LENGTH_SHORT).show() }
        }
    }

    private fun changeMailFragment(mailbox: List<Mailbox>) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, ChatboxFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val mailboxJson = gson.toJson(mailbox!![0])
                    putString("mailbox", mailboxJson)
                }
            })
            .commitAllowingStateLoss()
    }
}
package com.fromto

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fromto.databinding.ActivityMainBinding
import com.fromto.mypage.MypageFragment
import com.fromto.community.CoummunityFragment
import com.fromto.home.HomeFragment
import com.fromto.mailbox.MailboxFragment
import com.fromto.mailbox.more.ChatboxFragment
import com.fromto.mypage.BottomSheetClickListener
import com.fromto.mypage.EditprofileFragment
import com.fromto.mypage.wirttenmovie.WrittenmovieFragment
import com.fromto.reply.ReplyPFragment
import kotlinx.android.synthetic.main.fragment_mypage.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), BottomSheetClickListener {
    lateinit var binding: ActivityMainBinding
    val Gallery = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.main_frm, HomeFragment())
            .commit()
        overridePendingTransition(R.anim.fade_in, R.anim.none)

        initNavigation()
    }



    private fun initNavigation() {
//        supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment())
//            .commitAllowingStateLoss()
//
        binding.mainBnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mypageFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MypageFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.communityFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()

                    return@setOnItemSelectedListener true
                }

                R.id.letterboxFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MailboxFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    fun changeFragment(index: Int){
        when (index){
            1 -> { supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, HomeFragment()).commit() }
            2 -> { supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm,MypageFragment()).commitAllowingStateLoss() }
            3 -> { supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm,EditprofileFragment()).commitAllowingStateLoss() }
            4 -> { supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, WrittenmovieFragment()).commitAllowingStateLoss() }
            5 -> { supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ChatboxFragment()).commitAllowingStateLoss() }
            6 -> { supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm, ReplyPFragment()).commitAllowingStateLoss() }

        }
    }

    override fun onButtomSheetClicked(type: String) {
        Toast.makeText(this, type, Toast.LENGTH_SHORT).show()
    }
}
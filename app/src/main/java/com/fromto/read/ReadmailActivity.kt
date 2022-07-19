package com.fromto.read

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.fromto.MainActivity
import com.fromto.R
import com.fromto.databinding.ActivityReadmailBinding
import com.fromto.mypage.MypageService
import com.fromto.retrofit.ReadResponse
import kotlinx.android.synthetic.main.activity_readmail.*

class ReadmailActivity : AppCompatActivity(), ReadPView {
    lateinit var binding: ActivityReadmailBinding
    val service = ReadPService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.fade_in, R.anim.none)
        
        binding = ActivityReadmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        overridePendingTransition(R.anim.fade_in, R.anim.none)

        service.tryReadLetter()
        initClikckListener()
    }

    private fun initClikckListener() {
        read_close_iv.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.readReportIv.setOnClickListener {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSexMNLdco-p_NtVXgoW11mcPsKjgwdAI4ijccJcfz1D5DB1FA/viewform?usp=pp_url&entry.938550151=%EC%95%85%EC%84%B1+url"))
                startActivity(intent)
        }
    }

    override fun onLoading() {
    }

    override fun onReadingLetterSuccess(response: ReadResponse) {
        binding.readMailtitleTv.setText(response.result?.letterTitle)
        binding.readMovietitleTv.setText(response.result?.movieTitle)
        binding.readMailContentsTv.setText(response.result?.contents)
        Glide.with(this).load(response.result?.posterurl).into(binding.readPosterIv)
        binding.readToNameTv.setText(response.result?.recipientNickname)
        binding.readFromNameTv.setText(response.result?.senderNickname)
    }

    override fun onReadingLetterFailure(message: String) {
    }

}

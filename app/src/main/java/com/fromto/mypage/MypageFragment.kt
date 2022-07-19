package com.fromto.mypage

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.fromto.MainActivity
import com.fromto.databinding.FragmentMypageBinding
import com.fromto.login.LoginActivity
import com.fromto.retrofit.AuthResponse
import com.fromto.retrofit.MypageResponse
import kotlinx.android.synthetic.main.activiy_findps.*
import kotlinx.android.synthetic.main.dialog_mypageprofile.*
import kotlinx.android.synthetic.main.fragment_mypage.*
import okhttp3.*

class MypageFragment : Fragment(), MypageView {
    lateinit var binding: FragmentMypageBinding
    val service = MypageService(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        initViews()
        initClikckListener()

        return binding.root
    }

    private fun initClikckListener() {
        val mActivity = activity as MainActivity

        binding.mypageCloseIv.setOnClickListener { mActivity.changeFragment(1) }
        binding.mypageSettingIv.setOnClickListener { mActivity.changeFragment(3) }
        binding.mypageWirttenMovieTv.setOnClickListener { mActivity.changeFragment(4) }
        binding.mypageProfileIv.setOnClickListener {
            val bottomDialog = mypageBottomdialog()
            bottomDialog.show(childFragmentManager, bottomDialog.tag)
        }

    }

    //View
    private fun initViews() {
        service.tryWrittenPosterurl()
        service.tryUserInfo()
    }

    override fun onLoading() {
    }

    override fun onWrittenPosterSuccess(response: MypageResponse) {
        Glide.with(requireContext()).load(response.result?.가장먼저쓴포스터3개?.get(0)!!)
            .into(binding.mypageWrittenPosterIv01)
        Glide.with(requireContext()).load(response.result?.가장먼저쓴포스터3개?.get(1)!!)
            .into(binding.mypageWrittenPosterIv02)
        Glide.with(requireContext()).load(response.result?.가장먼저쓴포스터3개?.get(2)!!)
            .into(binding.mypageWrittenPosterIv03)
    }

    override fun onWrittenPosterFailure(message: String) {
        Log.d("writtenPoster/API-ERROR", message)
    }

    override fun onuserInfoSuccess(response: MypageResponse) {
        when(response.code){
            1000 -> { //성공
                Log.d("userInfo/OK", "성공")
                binding.mypageNicknameTv.setText(response.result?.nickname)
                if(response.result?.profileImgUrl == "No Img"){
                    //기본 이미지
                } else{
                    Glide.with(requireContext()).load(response.result?.profileImgUrl).centerCrop().into(binding.mypageProfileIv)}
            }
            2000 -> { //성공
                Toast.makeText(requireContext(),"로그인을 해주세요!", Toast.LENGTH_SHORT).show()
//                val intent = Intent(requireContext(), LoginActivity::class.java)
//                startActivity(intent)
                Log.d("userInfo/로그인X", "JWT 토큰을 입력해주세요.")}
            3000 -> { //성공
                Log.d("userInfo", "JWT 토큰 검증 실패")}
        }
    }

    override fun onuserInfoFailure(message: String) {
        Log.d("UserInfo/API-ERROR", message)
    }

    override fun onChangeProfileImgSuccess(response: MypageResponse) {
        when (response.code) {
            1000 -> {
                Toast.makeText(requireContext(), "프로필 사진 변경 완료", Toast.LENGTH_SHORT).show()
            }
            2000 -> {
                Toast.makeText(requireContext(), "JWT 토큰을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            3000 -> {
                Toast.makeText(requireContext(), "JWT 토큰 검증 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
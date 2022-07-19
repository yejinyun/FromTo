package com.fromto.mypage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fromto.MainActivity
import com.fromto.R
import com.fromto.databinding.FragmentEditprofileBinding
import com.fromto.findpsActivity
import com.fromto.login.LoginActivity
import com.fromto.login.spfManager
import com.fromto.login.spfManager.getJwt
import com.fromto.retrofit.MypageResponse

class EditprofileFragment : Fragment(), EditView {
    lateinit var binding: FragmentEditprofileBinding
    val service = EditService(this)

    private var AlarmToggle : Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): ScrollView {
        binding = FragmentEditprofileBinding.inflate(inflater, container, false)

        val currentUser = spfManager.getUserId()
        binding.editprofileEmailMail.setText(currentUser)
        initClickListener()

        initViews()

        return binding.root
    }

    private fun initViews(){
        //jwt를 가져오는 함수
        val jwt = getJwt()

        service.trySetting()

        if(jwt==""){
            binding.editprofileLogoutTv.text = "로그인"
            binding.editprofileLogoutTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
            binding.editprofileEmailMail.setText("로그인을 해주세요 :)")
        } else {
            binding.editprofileLogoutTv.text = "로그아웃"
            binding.editprofileEmailMail.setText(spfManager.getUserId())

            Log.d("id",spfManager.getUserId())

            binding.editprofileLogoutTv.setOnClickListener {
                //로그아웃을 시켜주는 함수
                spfManager.ClearJwt()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }


    }

    private fun initClickListener(){
        val mActivity = activity as MainActivity

        binding.editprofileToggleIv.setOnClickListener{
            AlarmToggle = !AlarmToggle

            if(AlarmToggle){
                service.tryAlarmActive(true)
                binding.editprofileToggleIv.setImageResource(R.drawable.ic_baseline_toggle_on_24)
                binding.editprofileToggleTv.setText("ON")
                Toast.makeText(getActivity(),"알림이 켜졌습니다.", Toast.LENGTH_SHORT).show()
            } else {
                service.tryAlarmActive(false)
                binding.editprofileToggleIv.setImageResource(R.drawable.ic_baseline_toggle_off_24)
                binding.editprofileToggleTv.setText("OFF")
                Toast.makeText(getActivity(),"알림이 꺼졌습니다.", Toast.LENGTH_SHORT).show()
            }
            Log.d("AlarmToggle",AlarmToggle.toString())
        }

        binding.editprofileCloseIv.setOnClickListener {
            mActivity.changeFragment(2)
        }

        binding.editprofileChangepsTv.setOnClickListener{
            startActivity(Intent(requireContext(), findpsActivity::class.java))
        }

        binding.editprofileSignoutTv.setOnClickListener{
            service.tryDelAccount()
        }

    }

    override fun onLoading() {
    }

    override fun onSettingsSuccess(response: MypageResponse) {
        Log.d("Setting","Success")
    }

    override fun onSettingsFailure(message: String) {
    }

    override fun onAlarmActiveSuccess(response: MypageResponse) {
    }

    override fun onAlarmActiveFailure(message: String) {
    }

    override fun onDelAccountSuccess(response: MypageResponse) {
        Toast.makeText(requireContext(),"탈퇴",Toast.LENGTH_SHORT).show()
        spfManager.ClearJwt()
        startActivity(Intent(activity, MainActivity::class.java))
    }
}
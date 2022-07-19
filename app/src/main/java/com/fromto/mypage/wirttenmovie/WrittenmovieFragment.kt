package com.fromto.mypage.wirttenmovie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fromto.MainActivity
import com.fromto.R
import com.fromto.config.Application
import com.fromto.config.Application.Companion.prefs
import com.fromto.databinding.FragmentWrittenmovieBinding
import com.fromto.mypage.MypageService
import com.fromto.retrofit.AuthResponse
import com.fromto.retrofit.PosterResponse
import com.fromto.retrofit.RetrofitBuilder
import com.fromto.retrofit.WrittenPoster
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_writtenmovie.*
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.URL
import java.net.URLEncoder

class WrittenmovieFragment : Fragment(), WrittenView {
    lateinit var binding: FragmentWrittenmovieBinding
    val service = WrittenService(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentWrittenmovieBinding.inflate(inflater, container, false)

//        val toolbar = findViewId<View>(R.id.toolbar) as Toolbar
//        val toolbar = binding.toolbar
//        toolbar.title = "내가 쓴 영화"
//        (activity as AppCompatActivity).setSupportActionBar(toolbar)
//        if (supportActionBar != null) supportActionBar!!.setDisplayHomeAsUpEnabled(false) //툴바에 백키(<-) 보이게할거면 이거 사용
        initClickListener()

//        val posters = ArrayList<List<WrittenPoster>>()
//        val posterAdapter = WrittenmovieRvAdapter(posters, requireContext())
//        binding.writtenmovieRv.adapter = posterAdapter
//        binding.writtenmovieRv.layoutManager = LinearLayoutManager(requireContext())
//        binding.writtenmovieRv.setHasFixedSize(false)


        service.tryWrittenPoster()
        return binding.root
    }

    private fun initClickListener() {
        val mActivity = activity as MainActivity

    }

    private fun setAdapter(posterList : ArrayList<List<WrittenPoster>>){
        val mAdapter = WrittenmovieRvAdapter(posterList,requireContext())
        binding.writtenmovieRv.adapter = mAdapter
        binding.writtenmovieRv.layoutManager = GridLayoutManager(requireContext(),2)
        binding.writtenmovieRv.setHasFixedSize(false)
    }

    override fun onLoading() {

    }

    override fun onWrittenPosterSuccess(response: PosterResponse) {
        when(response.code){
            1000 -> {
                response?.let {
                        setAdapter(it.result!!)
                    }
                Toast.makeText(requireContext(),"프로필 사진 변경 완료", Toast.LENGTH_SHORT).show()
            }
            2000 -> {
                Toast.makeText(requireContext(),"JWT 토큰을 입력해주세요", Toast.LENGTH_SHORT).show() }
            3000 -> {
                Toast.makeText(requireContext(),"JWT 토큰 검증 실패", Toast.LENGTH_SHORT).show() }
        }

    }

}
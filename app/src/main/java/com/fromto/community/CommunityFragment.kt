package com.fromto.community
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fromto.MainActivity
import com.fromto.R
import com.fromto.databinding.FragmentCommunityBinding
import com.fromto.write.AddphotoActivity
import com.fromto.write.model.ContentDTO
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_community.*
import kotlinx.android.synthetic.main.fragment_community.view.*
import kotlinx.android.synthetic.main.item_commudetail.view.*

class CoummunityFragment : Fragment() {
    lateinit var binding: FragmentCommunityBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCommunityBinding.inflate(inflater,container,false)

        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_community, container, false)

        initClickListener()

        return view
    }

    private fun initClickListener() {
        val mActivity = activity as MainActivity

        //fragment>fragment
        binding.communityCloseIv.setOnClickListener{
            mActivity.changeFragment(1)
        }
        //fragment > activity
        //사진 추가
        binding.communityWriteIb.setOnClickListener {
            val intent = Intent(getActivity(), AddphotoActivity::class.java)
            startActivity(intent)
        }
    }

}


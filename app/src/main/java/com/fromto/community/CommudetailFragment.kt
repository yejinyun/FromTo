package com.fromto.community

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fromto.databinding.FragmentCommudetailBinding

class CommudetailFragment : Fragment() {
    lateinit var binding: FragmentCommudetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCommudetailBinding.inflate(inflater, container, false)

        initClikckListener()

        return binding.root
    }


    private fun initClikckListener() {
        TODO("Not yet implemented")
    }
}


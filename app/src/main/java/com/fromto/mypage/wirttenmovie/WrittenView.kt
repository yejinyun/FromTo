package com.fromto.mypage.wirttenmovie

import com.fromto.retrofit.PosterResponse

interface WrittenView {
    fun onLoading()

    fun onWrittenPosterSuccess(response: PosterResponse)
}
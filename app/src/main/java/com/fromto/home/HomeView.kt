package com.fromto.home

import com.fromto.retrofit.AuthResponse
import com.fromto.retrofit.HomeResponse

interface HomeView {
    fun onLoading()

    fun onCheckedLetterSuccess(response: HomeResponse)
    fun onCheckedLetterFailure(message: String)

    fun onReadingLetterSuccess(response: HomeResponse)
    fun onReadingLetterFailure(message: String)
}
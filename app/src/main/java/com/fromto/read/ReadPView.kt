package com.fromto.read

import com.fromto.retrofit.ReadResponse

interface ReadPView {
    fun onLoading()

    fun onReadingLetterSuccess(response: ReadResponse)
    fun onReadingLetterFailure(message: String)
}
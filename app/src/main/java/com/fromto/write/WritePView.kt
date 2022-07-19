package com.fromto.write

import com.fromto.retrofit.AuthResponse

interface WritePView {
    fun onLoading()

    fun onWritingLetterSuccess(response: AuthResponse)
    fun onWritingLetterFailure(message: String)

    fun onSendingLetterSuccess(response: AuthResponse)
    fun onSendingLetterFailure(message: String)
}

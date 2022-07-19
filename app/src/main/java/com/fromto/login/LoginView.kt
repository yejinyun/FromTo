package com.fromto.login

import com.fromto.retrofit.AuthResponse

interface LoginView {
    fun onLoading()

    fun onAutoLoginSuccess(response: AuthResponse)

    fun onLoginSuccess(response: AuthResponse)
}
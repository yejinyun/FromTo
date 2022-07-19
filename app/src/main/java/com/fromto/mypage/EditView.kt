package com.fromto.mypage

import com.fromto.retrofit.MypageResponse

interface EditView {
    fun onLoading()

    fun onSettingsSuccess(response: MypageResponse)
    fun onSettingsFailure(message: String)

    fun onAlarmActiveSuccess(response: MypageResponse)
    fun onAlarmActiveFailure(message: String)

    fun onDelAccountSuccess(response: MypageResponse)
}
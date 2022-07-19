package com.fromto.mypage
import com.fromto.retrofit.AuthResponse
import com.fromto.retrofit.MypageResponse

interface MypageView {
    fun onLoading()

    fun onWrittenPosterSuccess(response: MypageResponse)
    fun onWrittenPosterFailure(message: String)

    fun onuserInfoSuccess(response: MypageResponse)
    fun onuserInfoFailure(message: String)

    fun onChangeProfileImgSuccess(response: MypageResponse)
}

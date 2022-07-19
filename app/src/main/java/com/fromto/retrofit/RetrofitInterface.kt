package com.fromto.retrofit
//signupservice
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitInterface {
    //callback_ call 오면 authresponse를 다시 파싱해주는 작업

    //회원가입
    @POST("/app/newusers")
    fun signUp(@Body user: User): Call<AuthResponse>

    //계정 중복확인
    @GET("/app/users/idcheck/{id}")
    fun idcheck(@Path("id") id : String) : Call<AuthResponse>

    //탈퇴
    @GET("/app/users/deleteAccount")
    fun delAccount() : Call<MypageResponse>

    //닉네임 중복확인
    @GET("/app/users/nicknamecheck/{nickname}")
    fun nicknamecheck(@Path("nickname") nickname: String) : Call<AuthResponse>

    //로그인
    @POST("/app/login")
    fun login(@Body user: User): Call<AuthResponse>

    //이메일 인증코드
    @FormUrlEncoded
    @POST("/app/newusers/authemail")
    fun authEmail(@Field("email") email: String) : Call<AuthResponse>

    //이메일 인증코드 체크
    @FormUrlEncoded
    @POST("/app/newusers/authcodeCheck")
    fun authcodeCheck(
    @Field("checkcode") checkcode: Int,
    @Field("email") email: String) : Call<AuthResponse>

    //회원가입 인증코드 삭제
    @FormUrlEncoded
    @POST("/app/newusers/deleteAuthcode")
    fun delAuthCode(@Field("email") email: String) : Call<AuthResponse>

    //8.비밀번호 바꾸기 인증번호
    @FormUrlEncoded
    @POST("/app/users/passwordAuthcodeCheck")
    fun psAuthcodeCheck(
        @Field("birth") birth: Int,
        @Field("gender") gender: Int,
        @Field("id") id: String
    ) : Call<AuthResponse>

    //9.비밀번호 바꾸기
    @POST("/app/newusers/changePassword")
    fun changePs(@Body changePSuser: ChangePSuser) : Call<AuthResponse>

    //10.비밀번호 인증코드 삭제
    @POST("/app/newusers/deletePasswordAuthcode")
    fun delPsAuthCode(@Body email: String) : Call<AuthResponse>

    //11.자동 로그인
    @GET("/app/auto-login")
    fun autoLogin() : Call<AuthResponse>

    //mypage
    //12.닉네임, 프로필url 불러오기 API (마이페이지 누를 때)
    @GET("/app/login/mypage/userInfo")
    fun userInfo() : Call<MypageResponse>

    //13. 내가쓴편지 포스터url 불러오기 API (마이페이지 누를 때)
    @GET("/app/login/mypage/writtenPosterurl")
    fun writtenPosterurl() : Call<MypageResponse>

    //14.프로필사진 변경 API (프로필 누를 때)
    @Multipart
    @POST("/app/login/mypage/changeProfileImgUrl")
    fun changeProfileImgUrl(@Part img : MultipartBody.Part?) : Call<MypageResponse>

    //15.셋팅 API (마이페이지에서 셋팅 누를 때)
    @GET("/app/login/mypage/settings")
    fun settings() : Call<MypageResponse>

    //16.알람설정 API (셋팅에서 alarm기능 활성화/비활성화 누를 때)
    @FormUrlEncoded
    @POST("/app/login/mypage/settings/alarmActive")
    fun alarmActive(@Field("alarm") alarm : Boolean) : Call<MypageResponse>

    //16.5 필명 변경
    @FormUrlEncoded
    @POST("/app/login/mypage/settings/changeNickname")
    fun changeNickname(@Field("newNickname") nickname: String) : Call<NicknameResponse>

    //17.편지 작성 API (홈에서 깃털 누를 때)
    @GET("/app/login/writingLetter")
    fun writingLetter() : Call<AuthResponse>

    //18.편지 보내기 API (작성페이지에서 보내기를 누를 때)
    @POST("/app/login/sendingLetter")
    fun sendingLetter(@Body writePContens: WritePContens) : Call<AuthResponse>

    //19.안읽은 편지 유무 API (홈으로 들어갈 때)
    @GET("/app/login/ischeckedLetter")
    fun ischeckedLetter() : Call<HomeResponse>

    //20.안읽은 편지 클릭 API (편지 누를 때)
    @GET("/app/login/readingLetter")
    fun readingLetter() : Call<ReadResponse>

    //20.5 랜덤 재전송 API (스포 포함 아니요 누를때)
    @GET("/app/login/readingLetter/resending")
    fun reSending() : Call<ReadResponse>

    //20.6 안읽은 편지 클릭 API (편지 누를 때)
    @GET("/app/login/readingLetter/deletePreferData")
    fun deletePreferData() : Call<ReadResponse>

    //21. 편지 회신하기
    @FormUrlEncoded
    @POST("/app/login/readingLetter/replyToLetter")
    fun replyToLetter(@Field("replyContents") replyContents: String) : Call<ReplyResponse>

    //22.내가 쓴 영화 API (22번 UI)
    @GET("/app/login/mypage/readingMyMovie")
    fun readingMyMovie() : Call<PosterResponse>

    //23. 편지함
    @GET("/app/login/mypage/letterbox")
    fun letterBox() : Call<MailboxResponse>

    //24. 채팅 편지함
    @GET("/app/login/mypage/letterbox/chatbox/{posterIdx}")
    fun chatBox(@Path("posterIdx") posterIdx: Int) : Call<ChatboxResponse>
}


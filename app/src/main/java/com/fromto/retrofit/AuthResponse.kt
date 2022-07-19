package com.fromto.retrofit

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Array

//API 출력값

//Auth? > 붙여서 널값 허용

data class AuthResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Auth?
    )

data class Auth(
    @SerializedName("userId") var userId: String?,
    @SerializedName("useridx") val useridx: Int?,
    @SerializedName("jwt") val jwt: String?,
    @SerializedName("nickname") val nickname: String?
)

data class MypageResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Mypage?
)

data class Mypage(
    @SerializedName("nickname") val nickname: String?,
    @SerializedName("profileImgUrl") val profileImgUrl: String?,
    @SerializedName("가장먼저쓴포스터3개") val 가장먼저쓴포스터3개: ArrayList<String>?,
    @SerializedName("Alarm Status") val alarm: Boolean?,
)

data class NicknameResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Nickname?
)

data class Nickname(
    @SerializedName("newNickname") val nickname: String?
)
data class HomeResponse(
@SerializedName("isSuccess") val isSuccess: Boolean,
@SerializedName("code") val code: Int,
@SerializedName("message") val message: String,
@SerializedName("result") val result: Home?
)

data class Home(
    @SerializedName("안읽은 편지의 개수") val 안읽은편지의개수: Int?
)

data class ReadResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Read?
)

data class Read(
    @SerializedName("letterTitle") val letterTitle: String?,
    @SerializedName("movieTitle") val movieTitle: String?,
    @SerializedName("contents") val contents: String?,
    @SerializedName("posterurl") val posterurl: String?,
    @SerializedName("senderNickname") val senderNickname: String?,
    @SerializedName("recipientNickname") val recipientNickname: String?
)

data class PosterResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<List<WrittenPoster>>?
)

data class WrittenPoster(
    @SerializedName("letterTitle") val letterTitle: String?,
    @SerializedName("movieImgUrlForLetter") val imgUrl: String?,
)

data class MailboxResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<List<Mailbox>>?
)

data class Mailbox(
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0,
    @SerializedName("letterTitle") val letterTitle: String?,
    @SerializedName("posterIdx") val posterIdx: Int?,
    @SerializedName("movieTitle") val movieTitle: String?,
    @SerializedName("movieImgUrlForLetter") val imgUrl: String?,
    @SerializedName("nickname") val nickname: String?
    )

data class ChatboxResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: ArrayList<Chatbox>?
)

data class Chatbox(
    @SerializedName("senderIdx") val senderIdx: Int?,
    @SerializedName("recipientIdx") val recipientIdx: Int?,
    @SerializedName("date") val date: String?,
    @SerializedName("contents") val contents: String?,
    @SerializedName("ischecked") val ischecked: Int?,
    @SerializedName("recipientNickname") val recipient: String?,
    @SerializedName("senderNickname") val sender: String?
    )

data class ReplyResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Reply?
)

data class Reply(
    @SerializedName("senderNickname") val senderNickname: String?,
    @SerializedName("recipientNickname") val recipientNickname: String?
)

package com.fromto.retrofit

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "UserTable")
data class User(
    var id: String,
    var password: String,
    var nickname: String?,
    var birth: String?,
    var gender: String?
)

data class ChangePSuser(
    var birth: Int,
    var gender: Int,
    var checkcode: Int,
    var email: String,
    var password1: String,
    var password2: String
)

data class WritePContens(
    @SerializedName("letterTitle") val letterTitle: String,
    @SerializedName("movieTitle") val movieTitle: String,
    @SerializedName("contents") val contents: String,
    @SerializedName("posterurl") val posterurl: String,
    @SerializedName("preferAge") val preferAge: Int,
    @SerializedName("preferGender") val preferGender: Int,
    @SerializedName("spoStatus") val spoStatus: Boolean
)

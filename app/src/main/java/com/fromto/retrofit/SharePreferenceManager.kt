package com.fromto.login
import com.fromto.config.Application


object spfManager {
    var prefs = Application.prefs

    fun saveJwt(jwt: String?){
        prefs.edit().putString(Application.X_ACCESS_TOKEN,jwt).apply()
    }

    fun getJwt(): String {
        return prefs.getString(Application.X_ACCESS_TOKEN, "").toString()
    }

    fun ClearJwt() {
        prefs.edit().clear().commit()
    }

    fun setUserId(input: String) {
        prefs.edit().putString("MY_ID", input).apply() }

    fun getUserId(): String {
        return prefs.getString("MY_ID", "").toString() }

    fun setUserNickname(input: String) {
        prefs.edit().putString("MY_NICKNAME", input).apply()
    }

    fun getUserNickname(): String {
        return prefs.getString("MY_NICKNAME", "").toString()
    }
}
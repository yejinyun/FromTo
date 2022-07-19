package com.fromto.retrofit

import com.fromto.login.spfManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    var api: RetrofitInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://3.34.130.115:3000")
            .client(provideOkHttpClient(AppInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(RetrofitInterface::class.java)
    }
}

private fun provideOkHttpClient(interceptor: AppInterceptor): OkHttpClient
        = OkHttpClient.Builder().run {
    addInterceptor(interceptor)
    build()
}

class AppInterceptor : Interceptor {
//    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
//    spfManager.getJwt()
        val newRequest = request().newBuilder()
            .addHeader("x-access-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJoc2g5MzEyMTJAbmF2ZXIuY29tIiwidXNlcklkeCI6MTMsImlhdCI6MTY0NDM4MTU3NSwiZXhwIjoxNjc1OTE3NTc1LCJzdWIiOiJ1c2VySW5mbyJ9.S42TxRyAWeY1xXI2msvnAFuLRHKnitA-Qjf56Je7ujA")
            .build()
        proceed(newRequest)
    }
}
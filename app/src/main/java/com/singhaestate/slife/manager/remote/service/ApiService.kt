package com.singhaestate.slife.manager.remote.service

import com.singhaestate.slife.manager.remote.service.model.LoginFacebookRequest
import com.singhaestate.slife.manager.remote.service.model.LoginRequest
import com.singhaestate.slife.manager.remote.service.model.RegisterRequest
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {

    @POST("auth/login")
    fun login(@Body login: LoginRequest): Observable<ResponseBody>

    @POST("auth/link-facebook-account")
    fun loginFacebook(@Body login: LoginFacebookRequest): Observable<ResponseBody>

    @POST("auth/register")
    fun register(@Body register: RegisterRequest): Observable<ResponseBody>
}
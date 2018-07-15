package com.singhaestate.slife.manager.remote.service.model

data class LoginFacebookRequest(
        val clientId: String,
        val clientSecret: String,
        val scope: String,
        val facebook_id: String,
        val accessToken: String
)
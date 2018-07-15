package com.singhaestate.slife.manager.remote.service.model

data class LoginRequest(
        val clientId: String,
        val clientSecret: String,
        val scope: String,
        val facebook_id: String?,
        val username: String?,
        val password: String?,
        val language: String,
        val noti_token: String,
        val device_token: String,
        val os_type: String,
        val os_version: String,
        val app_version: String,
        val user_agent: String
)
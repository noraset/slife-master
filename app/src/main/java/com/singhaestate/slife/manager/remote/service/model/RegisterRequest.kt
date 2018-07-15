package com.singhaestate.slife.manager.remote.service.model

data class RegisterRequest(
        val clientId: String,
        val clientSecret: String,
        val scope: String,
        val facebook_id: String?,
        val username: String?,
        val password: String?,
        val first_name: String,
        val last_name: String,
        val birthdate: String,
        val mobile_phone: String?,
        val email: String,
        val identification: String,
        val notification_enabled: Boolean,
        val notification_enabled_email: Boolean,
        val language: String,
        val noti_token: String,
        val device_token: String,
        val os_type: String,
        val os_version: String,
        val app_version: String,
        val user_agent: String
)
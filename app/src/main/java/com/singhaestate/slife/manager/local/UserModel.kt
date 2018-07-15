package com.singhaestate.slife.manager.local


data class UserModel(
        val id: Int,
        val total_points: Int,
        val notification_count: Int,
        val notification_opened_count: Int,
        val status: String,
        val first_name: String,
        val last_name: String,
        val local_first_name: Any,
        val local_last_name: Any,
        val identification: String,
        val mobile_phone: String,
        val birthdate: String,
        val email: String,
        val notification_enabled_email: Int,
        val notification_enabled: Int,
        val consumer_tier_id: Any,
        val scrm_id: Int,
        val prospect_id: Any,
        val verify: Int,
        val full_name: String,
        val full_address: Any,
        val profile_picture_url: Any,
        val profile_thumbnail_url: Any,
        val points_expiring: Int,
        val facebook_id: Any,
        val points: Int,
        val user_name: String,
        val expiring: List<Expiring>,
        val primary_address: Any,
        val profile_picture: Any,
        val profile_thumbnail: Any,
        val token: Token
)

data class Token(
        val refresh_token: String,
        val token_type: String,
        val access_token: String,
        val expires_in: Int
)

data class Expiring(
        val date: String,
        val points: Int
)
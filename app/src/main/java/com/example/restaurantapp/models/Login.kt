package com.example.restaurantapp.models

data class LoginResponse(
    val `data`: DataX,
    val result_code: Int,
    val result_msg: String
)

data class LoginRequest(
    val phoneNumber: String,
    val password: String
)

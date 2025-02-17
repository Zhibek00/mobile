package com.example.restaurantapp.models

data class RegisterRequest (
    val name: String,
    val phoneNumber: String,
    val password: String
)
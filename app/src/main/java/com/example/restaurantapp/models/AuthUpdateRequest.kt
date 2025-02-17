package com.example.restaurantapp.models

data class AuthUpdateRequest (
    val rid: String,
    val uid: String,
    val path: String,
    val password: String
)
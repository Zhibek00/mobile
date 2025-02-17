package com.example.restaurantapp.models

data class CardAddRequest(
    val cardNumber: Long,
    val cvv: Int,
    val uid: String,
    val validthru: String
)
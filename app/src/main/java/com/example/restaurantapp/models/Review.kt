package com.example.restaurantapp.models

data class ReviewResponse(
    val `data`: DataXXXXXXXXXXXX,
    val result_code: Int,
    val result_msg: String
)

data class ReviewAddRequest(
    val comment: String,
    val createTime: Int,
    val rating: Int,
    val rid: String,
    val uid: String
)

data class ReviewRequest(
    val rid: String
)
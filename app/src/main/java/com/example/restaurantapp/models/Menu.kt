package com.example.restaurantapp.models

data class MenuResponse(
    val `data`: DataXXXX,
    val result_code: Int,
    val result_msg: String
)

data class MenuRequest(
    val filter: Filter,
    val rid: String
)
data class MenuCategoryResponse(
    val `data`: DataXXXXXXX,
    val result_code: Int,
    val result_msg: String
)
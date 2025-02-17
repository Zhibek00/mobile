package com.example.restaurantapp.api

import com.example.restaurantapp.models.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ClientApi {

    //restaurant
    @POST("restaurant/list")
    fun restaurantList(): Call<RestaurantListResponse>
    @POST("restaurant/get")
    fun restaurantGet(@Body reviewRequest: ReviewRequest): Call<RestaurantGetResponse>
    @POST("restaurant/list")
    fun categoryList(@Body categoryRequest: CategoryRequest): Call<RestaurantListResponse>

    //authorization
    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
    @POST("auth/register")
    fun register(@Body loginRequest: RegisterRequest): Call<JsonObject>
    @POST("auth/update")
    fun update(@Body authUpdate: AuthUpdateRequest): Call<JsonObject>

    //favorite
    @POST("favorite/add")
    fun favorite(@Body favoriteRequest: FavoriteRequest): Call<JsonObject>
    @POST("favorite/remove")
    fun favoriteRemove(@Body favoriteRequest: FavoriteRequest): Call<JsonObject>
    @POST("favorite/list")
    fun favoriteList(@Body favoriteRequest: FavoriteRequest1): Call<RestaurantListResponse>

    //category
    @POST("category/list")
    fun categoryList(): Call<CategoryResponse123>

    //upload
    @Multipart
    @POST("upload/file")
    fun putImageFile(@Part file: MultipartBody.Part): Call<JsonObject>

    //review
    @POST("review/add")
    fun reviewAdd(@Body reviewAddRequest: ReviewAddRequest): Call<JsonObject>
    @POST("review/list")
    fun reviewList(@Body reviewRequest: ReviewRequest): Call<ReviewResponse>

    //table
    @POST("table/list")
    fun tableList(@Body table: ReviewRequest): Call<TableResponse>

    //menu
    @POST("menu/list")
    fun menuList(@Body menuRequest: MenuRequest): Call<MenuResponse>
    @POST("menu/category/list")
    fun categoryList1(): Call<MenuCategoryResponse>

    //reservation
    @POST("reservation/list")
    fun reservationList(@Body review: FavoriteRequest): Call<ReservationNewResponse>
    @POST("reservation/delete")
    fun reservationDelete(@Body reservation: ReservationDelete): Call<JsonObject>
    @POST("reservation/add")
    fun reservationAdd(@Body reservationRequest: ReservationRequest): Call<JsonObject>

    //banner
    @POST("banner/list")
    fun bannerList(): Call<BannerResponse>

    //card
    @POST("card/add")
    fun cardAdd(@Body cardAddRequest: CardAddRequest): Call<JsonObject>
    @POST("card/get")
    fun cardGet(@Body cardGetRequest: CardGetRequest): Call<CardGetResponse>
    @POST("card/delete")
    fun cardDelete(@Body cardGetRequest: CardGetRequest): Call<JsonObject>





}
package com.example.restaurantapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.api.ClientApi
import com.example.restaurantapp.models.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailedRestaurantViewModel(application: Application) : AndroidViewModel(application){
    private val localStorage: LocalStorage
    private val _menuData = MutableLiveData<List<RowXX>>()
    val menuData: LiveData<List<RowXX>> = _menuData

    private val _reviewData = MutableLiveData<List<RowXXXXXXX>>()
    val reviewData: LiveData<List<RowXXXXXXX>> = _reviewData

    private val _reviewData2 = MutableLiveData<DataXXXXXXXXXXXX>()
    val reviewData2: LiveData<DataXXXXXXXXXXXX> = _reviewData2

    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean> = _refresh

    private val _category = MutableLiveData<List<String>>()
    val category: LiveData<List<String>> = _category

    init {
        // Initialize LocalStorage with application context
        localStorage = LocalStorage(application)
    }
    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://192.168.0.153:1001/")
        .build()
        .create(ClientApi::class.java)

    fun getMenuCategory(){
        retrofitBuilder.categoryList1().enqueue(object : Callback<MenuCategoryResponse> {
            override fun onResponse(call: Call<MenuCategoryResponse>, response: Response<MenuCategoryResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.data?.rows
                    _category.value = responseBody?.map { it.name }?.toList()

                    Log.i("category", responseBody.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("HomeViewModel", "Failed to fetch data: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<MenuCategoryResponse>, t: Throwable) {
                // Handle failure
                Log.d("HomeViewModel", "Failed to fetch data: ${t.message}")
            }


        })
    }
    fun getMenu(rid: String, filter: Filter) {
        val reviewRequest = MenuRequest(
            rid = rid,
            filter = filter
        )
        retrofitBuilder.menuList(reviewRequest).enqueue(object : Callback<MenuResponse> {
            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.data?.rows
                    _menuData.value = responseBody
                    Log.i("iiii111", responseBody.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("HomeViewModel", "Failed to fetch data: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                // Handle failure
                Log.d("HomeViewModel", "Failed to fetch data: ${t.message}")
            }


        })
    }
    fun addReviews(comment: String, createTime: Int, rating: Int, rid: String, uid: String){
        val reviewAdd = ReviewAddRequest(
            comment = comment,
            createTime = createTime,
            rating = rating,
            rid = rid,
            uid = uid
        )
        Log.i("gggggsalemm", reviewAdd.toString())
        retrofitBuilder.reviewAdd(reviewAdd).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _refresh.value = true
                    Log.i("succes adding review", responseBody.toString())

                    // Update _restaurantData with the new data
                } else {
                    _refresh.value = false

                    Log.d("HomeViewModel", "Failed to fetch data: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Handle failure
                _refresh.value = false

                Log.d("HomeViewModel", "Failed to fetch data: ${t.message}")
            }


        })
    }
    fun getReviews(rid: String) {
        val reviewRequest = ReviewRequest(
            rid = rid
        )
        Log.i("riiiiid", reviewRequest.toString())
        retrofitBuilder.reviewList(reviewRequest).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                   _reviewData.value = responseBody?.data?.rows
                    _reviewData2.value = responseBody?.data
                    Log.i("reviewwwww", responseBody.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("Review", "Failed to fetch data: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                // Handle failure
                Log.d("ReviewList", "Failed to fetch data: ${t.message}")
            }


        })
    }


}
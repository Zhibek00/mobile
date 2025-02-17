package com.example.restaurantapp.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.restaurantapp.LocalStorage
import com.example.restaurantapp.R
import com.example.restaurantapp.api.ClientApi
import com.example.restaurantapp.models.*
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class RestaurantViewModel(application: Application) : AndroidViewModel(application) {
    private val localStorage: LocalStorage

    init {
        // Initialize LocalStorage with application context
        localStorage = LocalStorage(application)
    }

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://192.168.0.153:1001/")
        .build()
        .create(ClientApi::class.java)

    private val _restaurantData = MutableLiveData<List<Row>>()
    val restaurantData: LiveData<List<Row>> = _restaurantData

    private val _favouriteData = MutableLiveData<List<Row>>()
    val favouriteData: LiveData<List<Row>> = _favouriteData

    private val _bannerList = MutableLiveData<List<RowXXXX>>()
    val bannerList: LiveData<List<RowXXXX>> = _bannerList


    private val _restaurant = MutableLiveData<DataXXXXXXXXX>()
    val restaurant: LiveData<DataXXXXXXXXX> = _restaurant




    private val _deleteSuccess = MutableLiveData<Boolean>()
    val deleteSuccess: LiveData<Boolean>
        get() = _deleteSuccess

    private var _categoriesLiveData = MutableLiveData<List<RowXXXXX>>()
    var categoriesLiveData : LiveData<List<RowXXXXX>> = _categoriesLiveData

    private var _crestaurantData = MutableLiveData<List<Row>>()
    var crestaurantData: LiveData<List<Row>> = _crestaurantData



    fun favouriteList(uid: String){
        val loginRequest = FavoriteRequest1(

            uid = uid
        )

        retrofitBuilder.favoriteList(loginRequest).enqueue(object : Callback<RestaurantListResponse> {
            override fun onResponse(call: Call<RestaurantListResponse>, response: Response<RestaurantListResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.data?.rows
                    val names = responseBody?.map { it.name }?.toSet() as Set<String>
                    localStorage.saveStringSet("favorites", names)

                    _favouriteData.value = responseBody
                    Log.i("FavoriteList", responseBody.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("FavoriteList", "Failed to fetch data: ${response.errorBody()?.string()}")
                }                       }

            override fun onFailure(call: Call<RestaurantListResponse>, t: Throwable) {
                Log.e("FavoriteList", "Error fetching restaurant data: ${t.message}", t)

            }


        })

        }
    fun favouriteAdd( rid:String, uid: String){
        val favoriteRequest = FavoriteRequest(
            rid = rid ,
            uid = uid
        )
        Log.i("uid", uid)
        Log.d("rid", rid)
        retrofitBuilder.favorite(favoriteRequest).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    favouriteList(uid)

                    Log.i("FavAdd", responseBody.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("FavAdd", "Failed to fetch data: ${response.errorBody()?.string()}")
                }            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("FavAdd", "Failed to fetch data: ${t.message}")
            }

        })
    }

    fun favouriteDelete( rid:String, uid: String){
        val favoriteRequest = FavoriteRequest(
            rid = rid ,
            uid = uid
        )
        retrofitBuilder.favoriteRemove(favoriteRequest).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.i("FavDelete", responseBody.toString())
                    _deleteSuccess.postValue(true)


                    // Update _restaurantData with the new data
                } else {
                    _deleteSuccess.postValue(false)

                    Log.d("FavDelete", "Failed to fetch data: ${response.errorBody()?.string()}")
                }            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.d("FavDelete", "Failed to fetch data: ${t.message}")
            }

        })
    }

    fun fetchData() {
        retrofitBuilder.restaurantList().enqueue(object : Callback<RestaurantListResponse> {
            override fun onResponse(call: Call<RestaurantListResponse>, response: Response<RestaurantListResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.data?.rows

                    _restaurantData.value = responseBody
                    Log.i("RestaurantList", responseBody.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("RestaurantList", "Failed to fetch data: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RestaurantListResponse>, t: Throwable) {
                // Handle failure
                Log.d("RestaurantList", "Failed to fetch data: ${t.message}")
            }


        })
    }

    fun uploadImage(imageFile: File){
        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())

        // Create multipart file part
        val body = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)

        retrofitBuilder.putImageFile(body).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {


                    val categories = response.body()

                    Log.i("iiii", categories.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("HomeViewModel", "Failed to fetch data: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                // Handle failure
                Log.d("HomeViewModel", "Failed to fetch data: ${t.message}")
            }

        })

    }
    fun fetchCategory() {
        retrofitBuilder.categoryList().enqueue(object : Callback<CategoryResponse123> {
            override fun onResponse(call: Call<CategoryResponse123>, response: Response<CategoryResponse123>) {
                if (response.isSuccessful) {


                    val categories = response.body()
                    val responseBody = response.body()?.data?.rows
                    _categoriesLiveData.value = responseBody
                    Log.i("CategoryList", categories.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("CategoryList", "Failed to fetch data: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<CategoryResponse123>, t: Throwable) {
                // Handle failure
                Log.d("CategoryList", "Failed to fetch data: ${t.message}")
            }


        })
    }
    fun categoryGet(categoryName: String){
        val categoryRequest = CategoryRequest(
            keyword = "",
            categorySort = categoryName
        )
        retrofitBuilder.categoryList(categoryRequest).enqueue(object : Callback<RestaurantListResponse> {
            override fun onResponse(call: Call<RestaurantListResponse>, response: Response<RestaurantListResponse>) {
                if (response.isSuccessful) {

                    val loginRespons = response.body()?.data?.rows

                    _crestaurantData.value = loginRespons

                    Log.i("CategoryGet", _crestaurantData.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("CategoryGet", "Failed to fetch data: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RestaurantListResponse>, t: Throwable) {
                // Handle failure
                Log.d("CategoryGet", "Failed to fetch data: ${t.message}")
            }


        })
    }
    fun getRestaurant(rid: String){
        val rev= ReviewRequest(
            rid = rid
        )
        retrofitBuilder.restaurantGet(rev).enqueue(object : Callback<RestaurantGetResponse> {
            override fun onResponse(call: Call<RestaurantGetResponse>, response: Response<RestaurantGetResponse>) {
                if (response.isSuccessful) {

                    val loginRespons = response.body()?.data
                    _restaurant.value = loginRespons

                    Log.i("GetRestaurant", loginRespons.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("GetRestaurant", "Failed to fetch data: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RestaurantGetResponse>, t: Throwable) {
                // Handle failure
                Log.d("GetRestaurant", "Failed to fetch data: ${t.message}")
            }


        })

    }

    fun bannerGet(){
        retrofitBuilder.bannerList().enqueue(object : Callback<BannerResponse> {
            override fun onResponse(call: Call<BannerResponse>, response: Response<BannerResponse>) {
                if (response.isSuccessful) {

                    val loginRespons = response.body()?.data
                        _bannerList.value = loginRespons?.rows!!


                    Log.i("BannerList", _bannerList.value.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("Banner", "Failed to fetch data: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<BannerResponse>, t: Throwable) {
                // Handle failure
                Log.d("Banner", "Failed to fetch data: ${t.message}")
            }


        })

    }
}





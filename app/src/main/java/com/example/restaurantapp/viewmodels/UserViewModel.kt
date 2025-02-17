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

class UserViewModel(application: Application) : AndroidViewModel(application)  {
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

    private val _loginResponse = MutableLiveData<User>()
    val loginResponse: LiveData<User> = _loginResponse

    private val _checkerResponse = MutableLiveData<Boolean>()
    val checkerResponse: LiveData<Boolean> = _checkerResponse

    private val _cardResponse = MutableLiveData<DataXXX>()
    val cardResponse: LiveData<DataXXX> = _cardResponse






    fun register(name: String, phoneNumber: String, password: String){
        val registerRequest = RegisterRequest(
            name = name,
            phoneNumber = phoneNumber,
            password = password
        )

        retrofitBuilder.register(registerRequest).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    // Registration successful
                    val jsonObject = response.body()
                    Log.e("Success", jsonObject.toString())

                    _checkerResponse.value = true
                } else {
                    // Registration failed
                    Log.e("Register", "Failed to register: ${response.errorBody()?.string()}")
                    _checkerResponse.value = false
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Register", "Failed to register: ${t.message}")
                _checkerResponse.value = false

            }

        })
    }


    fun update(password: String){
        val updateRequest = AuthUpdateRequest(
            rid = "",
            uid = localStorage.getString("uid"),
            path = "",
            password = password
        )

        retrofitBuilder.update(updateRequest).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    // Registration successful
                    val jsonObject = response.body()
                    Log.e("Success", jsonObject.toString())
                    _checkerResponse.value = true
                    val userObject = jsonObject?.getAsJsonObject("user")
                    val password = userObject?.get("password")?.asString
                    localStorage.saveString("password",password!!)



                } else {
                    // Registration failed
                    Log.e("Register", "Failed to register: ${response.errorBody()?.string()}")
                    _checkerResponse.value = false
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("Register", "Failed to register: ${t.message}")
                _checkerResponse.value = false

            }

        })
    }


    fun login( phoneNumber: String, password: String) {
        val loginRequest = LoginRequest(
            phoneNumber = phoneNumber,
            password = password
        )


        retrofitBuilder.login(loginRequest).enqueue(object : Callback<LoginResponse?> {
            override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                if (response.isSuccessful) {

                    val loginRespons = response.body()?.data?.user
                    _loginResponse.value = loginRespons
                    val full_name = _loginResponse.value?.name
                    val phone = _loginResponse.value?.phoneNumber
                    val uid = _loginResponse.value?.uid
                    val password = _loginResponse.value?.password


                    localStorage.saveString("uid", uid!!)
                    localStorage.saveString("fullName", full_name!!)
                    localStorage.saveString("phone", full_name!!)
                    localStorage.saveString("password", password!!)

                    Log.i("RestaurantViewModel", "User's name: $full_name")
                } else {
                    // Handle unsuccessful response
                    Log.d("RestaurantViewModel", "Failed to login: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                // Handle failure
                Log.d("RestaurantViewModel", "Failed to login: ${t.message}")
            }
        })
    }




    fun cardGet( cardId: String){
        val card = CardGetRequest(
            cardId = cardId,
            uid = localStorage.getString("uid")
        )

        retrofitBuilder.cardGet(card).enqueue(object : Callback<CardGetResponse> {
            override fun onResponse(call: Call<CardGetResponse>, response: Response<CardGetResponse>) {
                if (response.isSuccessful) {
                    // Registration successful
                    val jsonObject = response.body()
                    Log.e("Success", jsonObject.toString())
                    _checkerResponse.value = true
                    _cardResponse.value = jsonObject?.data


                } else {
                    // Registration failed
                    Log.e("Register", "Failed to register: ${response.errorBody()?.string()}")
                    _checkerResponse.value = false

                }
            }

            override fun onFailure(call: Call<CardGetResponse>, t: Throwable) {
                Log.e("CardGet", "Failed to register: ${t.message}")
                _checkerResponse.value = false

            }

        })
    }


    fun cardDelete( cardId: String){
        val card = CardGetRequest(
            cardId = cardId,
            uid = localStorage.getString("uid")
        )

        retrofitBuilder.cardDelete(card).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    // Registration successful
                    val jsonObject = response.body()
                    Log.e("Success", jsonObject.toString())
                    _checkerResponse.value = true


                } else {
                    // Registration failed
                    Log.e("Card", "Failed to register: ${response.errorBody()?.string()}")
                    _checkerResponse.value = false

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("CardDelete", "Failed to register: ${t.message}")
                _checkerResponse.value = false

            }

        })
    }


    fun cardAdd( cardNumber: Long, cvv: Int, validthru: String){
        val card = CardAddRequest(
            uid = localStorage.getString("uid"),
            cardNumber = cardNumber,
            cvv = cvv,
            validthru = validthru
        )

        retrofitBuilder.cardAdd(card).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    // Registration successful
                    val jsonObject = response.body()
                    Log.e("Success", jsonObject.toString())
                    _checkerResponse.value = true

                    val userObject = jsonObject?.getAsJsonObject("data")
                    val cardId = userObject?.get("cardId")?.asString
                    localStorage.saveString("cardId", cardId!!)

                } else {
                    // Registration failed
                    Log.e("Card", "Failed to register: ${response.errorBody()?.string()}")
                    _checkerResponse.value = false

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("CardAdd", "Failed to register: ${t.message}")
                _checkerResponse.value = false

            }

        })
    }


}
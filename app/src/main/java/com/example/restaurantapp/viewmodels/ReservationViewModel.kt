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

class ReservationViewModel(application: Application) : AndroidViewModel(application) {
    private val localStorage: LocalStorage
    private val _tableData = MutableLiveData<List<DataXXXXX>>()
    val tableData: LiveData<List<DataXXXXX>> = _tableData

    private val _reservationData = MutableLiveData<List<RowXXXXXX>>()
    val reservationData: LiveData<List<RowXXXXXX>> = _reservationData

    init {
        // Initialize LocalStorage with application context
        localStorage = LocalStorage(application)
    }

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("http://192.168.0.153:1001/")
        .build()
        .create(ClientApi::class.java)

    fun tableList(rid: String){
        val t = ReviewRequest(
            rid = rid
        )

        retrofitBuilder.tableList(t).enqueue(object : Callback<TableResponse> {
            override fun onResponse(call: Call<TableResponse>, response: Response<TableResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()?.data
                    _tableData.value = responseBody

                    Log.i("iiii", responseBody.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("Fav", "Failed to fetch data: ${response.errorBody()?.string()}")
                }                       }

            override fun onFailure(call: Call<TableResponse>, t: Throwable) {
                Log.e("kk", "Error fetching restaurant data: ${t.message}", t)

            }


        })

    }
    fun reservationGet(uid: String){
        val f = FavoriteRequest(
            uid = uid,
            rid = ""

        )

        retrofitBuilder.reservationList(f).enqueue(object : Callback<ReservationNewResponse> {

            override fun onResponse(call: Call<ReservationNewResponse>, response: Response<ReservationNewResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    _reservationData.value = responseBody?.data?.rows
                    Log.i("iiii", responseBody.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("Fav", "Failed to fetch data: ${response.errorBody()?.string()}")
                }                       }

            override fun onFailure(call: Call<ReservationNewResponse>, t: Throwable) {
                Log.e("kk", "Error fetching restaurant data: ${t.message}", t)

            }


        })


        }
    fun reservationAdd(e: ReservationRequest){



        retrofitBuilder.reservationAdd(e).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()

                    Log.i("iiii", responseBody.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("Fav", "Failed to fetch data: ${response.errorBody()?.string()}")
                }                       }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("kk", "Error fetching restaurant data: ${t.message}", t)

            }


        })

    }
    fun reservationDelete(rid: String){
        val r = ReservationDelete(
            reservationId = rid
        )
        retrofitBuilder.reservationDelete(r).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    reservationGet(localStorage.getString("uid",""))

                    Log.i("iiii", responseBody.toString())

                    // Update _restaurantData with the new data
                } else {
                    Log.d("Fav", "Failed to fetch data: ${response.errorBody()?.string()}")
                }                       }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Log.e("kk", "Error fetching restaurant data: ${t.message}", t)

            }


        })

    }

}
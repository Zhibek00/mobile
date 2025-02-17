package com.example.restaurantapp

import android.content.Context

class LocalStorage(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("my_app", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun saveInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun saveStringSet(key: String, set: Set<String>) {
        sharedPreferences.edit().putStringSet(key, set).apply()
    }

    fun getStringSet(key: String, defaultValue: Set<String> = emptySet()): Set<String> {
        return sharedPreferences.getStringSet(key, defaultValue) ?: defaultValue
    }

    // Add other functions for saving/retrieving data as needed
}

// Example usage
fun main() {
    // Assume you have a context, e.g., from an Android activity or application
    val context: Context = TODO()

    val localStorage = LocalStorage(context)

    // Save a string
    localStorage.saveString("username", "John")

    // Retrieve a string
    val username = localStorage.getString("username")
    println("Username: $username")
}

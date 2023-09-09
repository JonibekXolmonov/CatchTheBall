package com.catchthisball.catcher.utils

import android.content.Context
import com.frt.cardsuits.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPref @Inject constructor(@ApplicationContext val context: Context) {

    private val sharedPref = context.getSharedPreferences("sharedPref", Context.MODE_PRIVATE)

    fun saveBoolean(key: String, data: Boolean) {
        sharedPref.edit().putBoolean(key, data).apply()
    }

    fun getBoolean(key: String) = sharedPref.getBoolean(key, false)

    fun saveInt(key: String, data:Int) {
        sharedPref.edit().putInt(key, data).apply()
    }

    fun getInt(key: String) = sharedPref.getInt(key, R.drawable.soccer_ball)

    fun getIntScore(key: String) = sharedPref.getInt(key, 0)
}
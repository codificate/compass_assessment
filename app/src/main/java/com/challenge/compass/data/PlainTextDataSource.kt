package com.challenge.compass.data

import android.content.SharedPreferences
import javax.inject.Inject

class PlainTextDataSource @Inject constructor(private val preferences: SharedPreferences?) {
    companion object {
        private const val MY_STRING_KEY = "plain_text_key"
    }

    fun saveString(value: String) {
        preferences?.edit()?.putString(MY_STRING_KEY, value)?.apply()
    }

    fun getString(): String? {
        return preferences?.getString(MY_STRING_KEY, null)
    }
}

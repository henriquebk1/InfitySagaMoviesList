package com.henriquebecker.infinitysaga.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class Prefs(context: Context) {
    private val p: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    var order: Int
        get() = p.getInt("order_pref", 0)
        set(value) = p.edit().putInt("order_pref", value).apply()
}
package com.utiq.utiq_loader_android_webview

import android.content.Context

object SharedPreferenceUtils {

    private const val CONSENT = "CONSENT"

    private fun getSharedPreferences(context: Context) = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)

    fun getConsent(context: Context) = getSharedPreferences(context).getString(CONSENT, null)

    fun setConsent(context: Context, value: String?) {
        getSharedPreferences(context).edit().putString(CONSENT, value).apply()
    }
}
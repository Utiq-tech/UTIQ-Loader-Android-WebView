package com.utiq.utiq_loader_android_webview

import android.webkit.JavascriptInterface
import androidx.fragment.app.FragmentManager

class WebAppInterface(
    private val sfm: FragmentManager,
    private val action: (consent: Boolean) -> Unit
) {
    @JavascriptInterface
    fun showConsentManager() {
        UtiqConsent.open(sfm) { consent ->
            action.invoke(consent)
        }
    }
}


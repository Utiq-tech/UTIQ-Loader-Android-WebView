package com.resonic.utiq_loader_android_webview

import android.webkit.WebView
import android.webkit.JavascriptInterface

class WebAppInterface(private val webView: WebView) {

    @JavascriptInterface
    fun onConsentChange(isConsentGranted: String) {
        println("UTIQ: $isConsentGranted")
        val script = "document.getElementById('mobile-anchor').innerText = \"$isConsentGranted\""
        webView.post {
            webView.evaluateJavascript(script) { value -> // 'value' contains the result of the JavaScript code
                print("Label is updated with message: $value")
            }
        }
    }
}
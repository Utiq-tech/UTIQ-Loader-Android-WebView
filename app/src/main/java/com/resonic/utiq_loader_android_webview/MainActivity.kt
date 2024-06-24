package com.resonic.utiq_loader_android_webview

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        WebView.setWebContentsDebuggingEnabled(true)
        webView.also {
            it.settings.javaScriptEnabled = true
            it.settings.domStorageEnabled = true
            it.addJavascriptInterface(WebAppInterface(it), "AndroidFunction")
            it.loadUrl("https://utiq-test.brand-demo.com/utiq/mobile/mobile-page.html")
        }
    }
}
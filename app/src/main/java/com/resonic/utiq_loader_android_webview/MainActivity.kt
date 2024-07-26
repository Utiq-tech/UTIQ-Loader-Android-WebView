package com.resonic.utiq_loader_android_webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView
    val token = "54fc04fcfdee14ad8268d59b3303baf8854bc4d27308a2668dd47ed2a8a54cae"

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
            it.loadUrl("https://utiq-test.utest1.work/utiq/mobile/mobile-page.html?utiq_stub=$token")
        }
    }
}
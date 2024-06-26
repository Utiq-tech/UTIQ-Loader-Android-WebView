package com.resonic.utiq_loader_android_webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //WebStorage.getInstance().deleteAllData()
        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.addJavascriptInterface(WebAppInterface(webView, this), "AndroidFunction")
        webView.loadUrl("http://192.168.1.129:8080/stage/utiq/mobile/native-page.html")
        //webView.loadUrl("https://utiq-test.brand-demo.com/utiq/mobile/native-page.html")
    }

    private fun updateScreenTitle(title: String) {
        setTitle(title)
    }
}


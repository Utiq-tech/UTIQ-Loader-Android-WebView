package com.resonic.utiq_loader_android_webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebResourceResponse
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        WebStorage.getInstance().deleteAllData()
        webView = findViewById(R.id.webView)
        webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            // From API 21 we should use another overload
            override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
                return if ("mobile-page" in url || "token-stub" in url || "mno-precheck" in url || "mno-selector" in url || "tmi-stub" in url || "data" in url) handleRequest(
                    url
                ) else null
                //return null
            }

            private fun handleRequest(url: String): WebResourceResponse? {
                try {
                    val call: Call = okHttpClient.newCall(
                        Request.Builder()
                            .addHeader(
                                "x-cryptip",
                                "54fc04fcfdee14ad8268d59b3303baf8854bc4d27308a2668dd47ed2a8a54cae"
                            )
                            .url(url)
                            .build()
                    )
                    val response: Response = call.execute()
                    updateScreenTitle(response.code.toString())

                    return WebResourceResponse(
                        response.header(
                            "content-type",
                            "text/plain"
                        ),  // You can set something other as default content-type
                        response.header(
                            "content-encoding",
                            "utf-8"
                        ),  // Again, you can set another encoding as default
                        response.body!!.byteStream()
                    )
                } catch (e: Exception) {
                    // TODO: Figure out how to show a custom error screen when we fail
                    return null
                }
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.addJavascriptInterface(WebAppInterface(webView), "AndroidFunction")
        webView.loadUrl("https://utiq-test.brand-demo.com/utiq/mobile/mobile-page.html")
    }

    private fun updateScreenTitle(title: String) {
        setTitle(title)
    }
}

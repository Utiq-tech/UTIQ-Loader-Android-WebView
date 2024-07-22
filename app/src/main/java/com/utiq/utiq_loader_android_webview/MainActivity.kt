package com.utiq.utiq_loader_android_webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.teavaro.utiqTech.data.models.UTIQOptions
import com.teavaro.utiqTech.initializer.UTIQ

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initializeUtiq {
            setWebViewTextField("UTIQ SDK Initialized")
        }
        webView = findViewById<WebView?>(R.id.webView).apply {
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            addJavascriptInterface(WebAppInterface(this@MainActivity, { martTechPass, adTechPass ->
                setUtiqIdsOnWeb(martTechPass, adTechPass)
            }, { text ->
                setWebViewTextField(text)
            }), "AndroidFunction")
            loadUrl("https://utiq-test.brand-demo.com/utiq/mobile/native-page.html")
        }
    }

    private fun initializeUtiq(action: (() -> Unit)) {
        val config = resources.openRawResource(R.raw.utiq_configs)
            .bufferedReader()
            .use { it.readText() }
        val options = UTIQOptions()
            .enableLogging()
            .setFallBackConfigJson(config)
        UTIQ.initialize(application, "R&Ai^v>TfqCz4Y^HH2?3uk8j", options, {
            action.invoke()
        }, { e ->
            println("Error: $e")
        })
    }

    private fun setUtiqIdsOnWeb(adTechPass: String, marTechPass: String) {
        val script = "refreshIds('$adTechPass', '$marTechPass')"
        webView.post {
            webView.evaluateJavascript(script) {
                print(script)
            }
        }
    }

    private fun setWebViewTextField(string: String) {
        val script = "showTextMessage('$string')"
        webView.post {
            webView.evaluateJavascript(script) {
                print(script)
            }
        }
    }
}


package com.utiq.utiq_loader_android_webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.teavaro.utiqTech.data.models.UTIQOptions
import com.teavaro.utiqTech.initializer.UTIQ

class MainActivity : AppCompatActivity() {

    lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val webInterface = WebAppInterface(supportFragmentManager){ consent ->
            if (UTIQ.isInitialized()) {
                if (consent) {
                    UTIQ.acceptConsent()
                    UTIQ.startService(
                        "523393b9b7aa92a534db512af83084506d89e965b95c36f982200e76afcb82cb",
                        { data ->
                            updateTextsOnWeb(atid = data.atid, mtid = data.mtid)
                            updateTextsOnWeb(text = "UTIQ IDs fetched")
                        },
                        { e ->
                            println("Error1: $e")
                        })
                } else {
                    updateTextsOnWeb(atid = "", mtid = "")
                    updateTextsOnWeb(text = "UTIQ Consent rejected")
                    UTIQ.rejectConsent()
                }
            }
        }

        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.addJavascriptInterface(webInterface, "AndroidFunction")
        //webView.loadUrl("http://192.168.1.129:8080/stage/utiq/mobile/native-page.html")
        webView.loadUrl("https://utiq-test.brand-demo.com/utiq/mobile/native-page.html")

        initializeUtiq {
            updateTextsOnWeb(text = "UTIQ Initialized")
        }
    }

    private fun initializeUtiq(action: (() -> Unit)) {
        println("iran -> 1")
        val config = resources.openRawResource(R.raw.utiq_configs)
            .bufferedReader()
            .use { it.readText() }
        val options = UTIQOptions()
        options.enableLogging()
        options.setFallBackConfigJson(config)
        UTIQ.initialize(application, "R&Ai^v>TfqCz4Y^HH2?3uk8j", options, {
            println("iran -> 2")
            action.invoke()
        }, { e ->
            println("Error2: $e")
        })
        println("iran -> 3")
    }

    private fun updateTextsOnWeb(atid: String? = null, mtid: String? = null, text: String? = null){
        var script = ""
        if(atid != null && mtid != null ) {
            script = "refreshIds('$atid', '$mtid')"
            webView.post {
                webView.evaluateJavascript(script) {
                    print(script)
                }
            }
        }
        if(text != null){
            script = "showTextMessage('$text')"
            webView.post {
                webView.evaluateJavascript(script) {
                    print(script)
                }
            }
        }
    }
}


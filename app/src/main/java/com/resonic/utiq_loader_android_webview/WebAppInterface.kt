package com.resonic.utiq_loader_android_webview

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.teavaro.utiqTech.data.models.UTIQOptions
import com.teavaro.utiqTech.initializer.UTIQ

class WebAppInterface(private val webView: WebView, private val context: Context) {

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

    @JavascriptInterface
    fun showConsentManager() {
        initializeUtiq {
            showDialog({
                UTIQ.acceptConsent()
                UTIQ.startService(
                    "523393b9b7aa92a534db512af83084506d89e965b95c36f982200e76afcb82cb",
                    { data ->
                        println("iran -> 4")
                        val script = "refreshIds('${data.atid}', '${data.mtid}')"
                        webView.post {
                            webView.evaluateJavascript(script) {
                                print("Label is updated with message")
                            }
                        }
                    },
                    { e ->
                        println("Error1: $e")
                    })
            }, {
                UTIQ.rejectConsent()
            })
        }
    }

    private fun initializeUtiq(action: (() -> Unit)) {
        println("iran -> 1")
        val app = context.applicationContext as Application
        val config = app.resources.openRawResource(R.raw.utiq_configs)
            .bufferedReader()
            .use { it.readText() }
        val options = UTIQOptions()
        options.enableLogging()
        options.setFallBackConfigJson(config)
        UTIQ.initialize(app, "R&Ai^v>TfqCz4Y^HH2?3uk8j", options, {
            println("iran -> 2")
            action.invoke()
        }, { e ->
            println("Error2: $e")
        })
        println("iran -> 3")
    }

    private fun showDialog(acceptAction: (() -> Unit), rejectAction: (() -> Unit)) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("UTIQ Consent")
        builder.setPositiveButton("Accept") { dialog, which ->
            acceptAction.invoke()
        }
        builder.setNegativeButton("Reject") { dialog, which ->
            rejectAction.invoke()
        }
        builder.show()
    }
}


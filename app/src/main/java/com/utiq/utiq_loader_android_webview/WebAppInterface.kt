package com.utiq.utiq_loader_android_webview

import android.webkit.JavascriptInterface
import androidx.appcompat.app.AppCompatActivity
import com.teavaro.utiqTech.initializer.UTIQ

class WebAppInterface(private val activity: AppCompatActivity, private val idcData: (adTechPass: String, marTechPass: String) -> Unit, private val webViewTextField: (string: String) -> Unit) {

    @JavascriptInterface
    fun showConsentManager() {
        if (UTIQ.isInitialized()) {
            UtiqConsentDialogFragment.open(activity.supportFragmentManager) { consent ->
                if (consent) {
                    UTIQ.acceptConsent()
                    UTIQ.startService(
                        "523393b9b7aa92a534db512af83084506d89e965b95c36f982200e76afcb82cb", {
                            idcData(it.atid!!, it.mtid!!)
                            webViewTextField("UTIQ IDs fetched")
                        }, { e ->
                            println("Error1: $e")
                        })
                }
                else {
                    idcData("", "")
                    webViewTextField("UTIQ Consent rejected")
                    UTIQ.rejectConsent()
                }
            }
        }
    }
}
# UTIQ Loader Android WebView

A JS snippet of the code that can be added to call a code from Android
```
if (window.AndroidFunction) {
        AndroidFunction.onConsentChange(isConsentGranted);
       console.log('AndroidFunction function called');
} else {
       console.warn('No AndroidFunction available in the window object');
}
```
**AndroidFunction** is the string that the webview will call in Android, the webview should also register this word as it is the contract between Android and JS, It can be anything but **AndroidFunction** is just an example.

The JS will call the function using the contract keyword, in our case **AndroidFunction** followed by the function name in the **WebAppInterface**

The **WebAppInterface** is a plain class that contains the functions that will be called by their names from JS, these functions should be annotated with `@JavascriptInterface` and the class should be registered. added to the webview, also can be named anything.

```
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
```

This is how we register the JS interface with the contract keyword:
`webview.addJavascriptInterface(WebAppInterface(it), "AndroidFunction")`

And this is how it will be called from JS:

`AndroidFunction.onConsentChange(isConsentGranted);`

Code snippet to update a JS element:

```
val script = "document.getElementById('mobile-anchor').innerText = \"$isConsentGranted\""
webView.post {
      webView.evaluateJavascript(script) { value -> // 'value' contains the result of the JavaScript code
    print("Label is updated with message: $value")
     }
}
```
package com.example.apjakdojade

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebActivity : AppCompatActivity() {

    var webView: WebView? = null
    var stopUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        stopUrl = intent.extras?.getString("stopUrl")

        WebAction()
    }


    @SuppressLint("SetJavaScriptEnabled")
    fun WebAction() {
        webView = findViewById(R.id.webView)

        webView!!.settings.javaScriptEnabled = true
        webView!!.settings.setAppCacheEnabled(true)
        if (QrCode.result1 == null) {
            webView!!.loadUrl(stopUrl)
        } else {
            webView!!.loadUrl(QrCode.result1)
        }
        webView!!.webViewClient = object : WebViewClient() {
            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
                webView!!.loadUrl("file:///android_assets/error.html")
            }

            override fun onPageFinished(view: WebView, url: String) {
                // do your stuff here
            }
        }
    }

    override fun onBackPressed() {
        if (webView!!.canGoBack()) {
            webView!!.goBack()
        } else {
            finish()
        }
    }

}

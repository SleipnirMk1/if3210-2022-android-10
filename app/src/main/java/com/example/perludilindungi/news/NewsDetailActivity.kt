package com.example.perludilindungi.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.perludilindungi.R
import com.example.perludilindungi.models.NewsResultResponse

class NewsDetailActivity : AppCompatActivity() {
    private val NEWS_TAG = "NewsDetail"
    private lateinit var myWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        myWebView = findViewById(R.id.news_web_view)
        myWebView.webViewClient = WebViewClient()

        val link = intent.getStringExtra("EXTRA_LINK")
        myWebView.loadUrl(link!!)

        Log.d(NEWS_TAG, "onCreate: called")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
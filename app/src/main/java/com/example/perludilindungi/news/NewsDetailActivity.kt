package com.example.perludilindungi.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.perludilindungi.R
import com.example.perludilindungi.faskes.BookmarkFaskesActivity
import com.example.perludilindungi.faskes.CariFaskesActivity
import com.example.perludilindungi.qr_scanner.QrScanner
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewsDetailActivity : AppCompatActivity() {
    companion object {
        const val NEWS_TAG = "NewsDetail"
        const val EXTRA_LINK = "EXTRA_LINK"
        const val EXTRA_TITLE = "EXTRA_TITLE"
    }

    private lateinit var myWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        myWebView = findViewById(R.id.news_web_view)
        myWebView.webViewClient = WebViewClient()

        val link = intent.getStringExtra(EXTRA_LINK)
        myWebView.loadUrl(link!!)

        Log.d(NEWS_TAG, "onCreate: called")

        setQRButton()
        setNavigation()

        val title = intent.getStringExtra(EXTRA_TITLE)
        setActionBarTitle(title!!)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && myWebView.canGoBack()) {
            myWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun setQRButton() {
        val qrButton = findViewById<FloatingActionButton>(R.id.qrButton)
        qrButton.setOnClickListener {
            val intent = Intent(this, QrScanner::class.java)
            startActivity(intent)
        }
    }

    private fun setNavigation() {
        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.selectedItemId = R.id.navigationNews
        navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigationBookmark -> {
                    val intent = Intent(this@NewsDetailActivity, BookmarkFaskesActivity::class.java)
                    startActivity(intent)
                }
                R.id.navigationLocation -> {
                    val intent = Intent(this@NewsDetailActivity, CariFaskesActivity::class.java)
                    startActivity(intent)
                }
                else -> Log.d(NewsActivity.NEWS_TAG, "onCreate: navigation else")
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun setActionBarTitle(title: String) {
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = title
        }
    }
}
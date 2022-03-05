package com.example.perludilindungi.news

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.R
import com.example.perludilindungi.adapter.NewsAdapter
import com.example.perludilindungi.faskes.BookmarkFaskesActivity
import com.example.perludilindungi.faskes.CariFaskesActivity
import com.example.perludilindungi.models.NewsResultResponse
import com.example.perludilindungi.qr_scanner.QrScanner
import com.example.perludilindungi.repository.Repository
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NewsActivity : AppCompatActivity() {
    companion object {
        const val NEWS_TAG = "News"
        const val EXTRA_TITLE = "EXTRA_TITLE"
        const val EXTRA_DATE = "EXTRA_DATE"
        const val EXTRA_LINK = "EXTRA_LINK"
        const val EXTRA_IMAGE = "EXTRA_IMAGE"
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var rvNews: RecyclerView
    private var list: ArrayList<NewsResultResponse> = arrayListOf()
    private var itemNumber: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        Log.d(NEWS_TAG, "onCreate: called")

        rvNews = findViewById(R.id.rv_news)
        rvNews.setHasFixedSize(true)

        showRecyclerList()
        getNewsData()
        setNavigation()
        setQRButton()
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
                    val intent = Intent(this@NewsActivity, BookmarkFaskesActivity::class.java)
                    startActivity(intent)
                }
                R.id.navigationLocation -> {
                    val intent = Intent(this@NewsActivity, CariFaskesActivity::class.java)
                    startActivity(intent)
                }
                else -> Log.d(NEWS_TAG, "onCreate: navigation else")
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun showRecyclerList() {
        rvNews.layoutManager = LinearLayoutManager(this)

        newsAdapter = NewsAdapter(list, itemNumber)
        newsAdapter.setOnClickListener(object : NewsAdapter.OnClickCallback {
            override fun onClick(data: NewsResultResponse) {
                showSelectedNews(data)
                Log.d(NEWS_TAG, "showRecyclerList: setOnItemClickCallback: called")
            }
        })

        rvNews.adapter = newsAdapter

        Log.d(NEWS_TAG, "showRecyclerList: called")
    }

    private fun showSelectedNews(data: NewsResultResponse) {
        val intent = Intent(this@NewsActivity, NewsDetailActivity::class.java)
        intent.putExtra(EXTRA_TITLE, data.title)
        intent.putExtra(EXTRA_DATE, data.pubDate)
        intent.putExtra(EXTRA_LINK, data.link?.get(0) ?: data.guid)
        intent.putExtra(EXTRA_IMAGE, data.encl.imageUrl)

        startActivity(intent)
    }

    private fun getNewsData() {
        val repository = Repository()
        val viewModelFactory = NewsViewModelFactory(repository)

        Log.d(NEWS_TAG, "getNewsData: called")

        viewModel = ViewModelProvider(this, viewModelFactory)[NewsViewModel::class.java]
        viewModel.getNews()
        viewModel.myNewsResponse.observe(this) { response ->
            if (response.isSuccessful) {
                val results = response.body()?.results
                val countTotal = response.body()?.countTotal
                Log.d(NEWS_TAG, "====FETCH SUCCESS====")

                if (results != null && countTotal != null) {
                    list.addAll(results)
                    itemNumber = countTotal

                    newsAdapter.setResultsData(results)
                    newsAdapter.itemCount = countTotal
                }
            } else {
                Log.d(NEWS_TAG, "====FETCH FAILED====")
                Log.d(NEWS_TAG, response.code().toString())
                Log.d(NEWS_TAG, response.errorBody().toString())
                Log.d(NEWS_TAG, response.body()?.message.toString())
            }
        }
    }
}
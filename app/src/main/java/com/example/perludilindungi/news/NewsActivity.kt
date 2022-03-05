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
import com.example.perludilindungi.models.NewsResponse
import com.example.perludilindungi.models.NewsResultResponse
import com.example.perludilindungi.repository.Repository

class NewsActivity : AppCompatActivity() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var rvNews: RecyclerView
    private var list: ArrayList<NewsResultResponse> = arrayListOf()
    private var itemNumber: Int = 0
    private val NEWS_TAG = "News"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        Log.d(NEWS_TAG, "onCreate: called")

        rvNews = findViewById(R.id.rv_news)
        rvNews.setHasFixedSize(true)

        showRecyclerList()
        getNewsData()
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
        intent.putExtra("EXTRA_NEWS", data)

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

                if (results != null && countTotal != null) {
                    list.addAll(results)
                    itemNumber = countTotal

                    newsAdapter.setResultsData(results)
                    newsAdapter.itemCount = countTotal
                }
            } else {
                Log.d(NEWS_TAG, response.errorBody().toString())
            }
        }
    }
}
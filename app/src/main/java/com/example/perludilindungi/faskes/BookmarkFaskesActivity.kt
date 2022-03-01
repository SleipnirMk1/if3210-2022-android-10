package com.example.perludilindungi.faskes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.R
import com.example.perludilindungi.faskes.fragments.FaskesFragment
import com.example.perludilindungi.models.database.FaskesDataViewModel

class BookmarkFaskesActivity : AppCompatActivity() {
    private val TAG = "BookmarkFaskesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark_faskes)

        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = FaskesFragment()
        mFragmentTransaction.replace(R.id.frameLayoutFaskes, mFragment).commit()
    }
}
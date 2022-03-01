package com.example.perludilindungi.faskes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.R
import com.example.perludilindungi.faskes.fragments.FaskesFragment
import com.example.perludilindungi.models.database.FaskesDataViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class BookmarkFaskesActivity : AppCompatActivity() {
    private val TAG = "BookmarkFaskesActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark_faskes)

        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = FaskesFragment()
        mFragmentTransaction.replace(R.id.frameLayoutFaskes, mFragment).commit()

        val navigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        navigation.selectedItemId = R.id.navigationBookmark
        navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigationLocation -> {
                    startActivity(Intent(this, CariFaskesActivity::class.java))
                }
                R.id.navigationNews -> {
                    // TODO taroh activity news disini
                }
                else -> Log.d(TAG, "onCreate: masuk else")
            }
            return@setOnItemSelectedListener true
        }
    }
}
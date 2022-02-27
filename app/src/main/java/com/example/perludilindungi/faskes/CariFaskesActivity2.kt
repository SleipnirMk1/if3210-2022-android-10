package com.example.perludilindungi.faskes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.perludilindungi.R
import com.example.perludilindungi.faskes.fragments.FaskesFragment

class CariFaskesActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cari_faskes2)

        val faskesFragment = FaskesFragment()
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.add(R.id.frameLayout, faskesFragment).commit()

    }
}
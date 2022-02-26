package com.example.perludilindungi.faskes.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.perludilindungi.R

class FaskesFragment : Fragment() {

    companion object {
        fun newInstance() = FaskesFragment()
    }

    private lateinit var viewModel: FaskesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.faskes_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FaskesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
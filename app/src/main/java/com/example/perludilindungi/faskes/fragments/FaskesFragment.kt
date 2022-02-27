package com.example.perludilindungi.faskes.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.perludilindungi.R
import com.example.perludilindungi.adapter.FaskesAdapter
import com.example.perludilindungi.faskes.CariFaskesViewModel
import com.example.perludilindungi.faskes.CariFaskesViewModelFactory
import com.example.perludilindungi.repository.Repository

class FaskesFragment : Fragment() {

    private lateinit var viewModel: CariFaskesViewModel
    private lateinit var recyclerView: RecyclerView
    private val faskesAdapter by lazy { FaskesAdapter() }

//    companion object {
//        fun newInstance() = FaskesFragment()
//    }
//
//    private lateinit var viewModel: FaskesViewModel
//
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.faskes_fragment, container, false)

        // setup recycler view
        recyclerView = view.findViewById(R.id.recyclerView2)
        recyclerView.adapter = faskesAdapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = Repository()
        val viewModelFactory = CariFaskesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CariFaskesViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.getFaskes("DKI JAKARTA", "KOTA ADM. JAKARTA BARAT")
        activity?.let {
            viewModel.myFaskesResponse.observe(it) { response ->
                if (response.isSuccessful) {
                    response.body()?.let { faskesAdapter.setData(it.data) }
                    Log.d("FASKES", response.body().toString())
                } else {
                    Toast.makeText(activity, response.code(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
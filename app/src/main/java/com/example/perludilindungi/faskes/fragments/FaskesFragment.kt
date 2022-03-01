package com.example.perludilindungi.faskes.fragments

import android.content.Intent
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
import com.example.perludilindungi.faskes.DetailFaskesActivity
import com.example.perludilindungi.models.DataFaskesResponse
import com.example.perludilindungi.repository.Repository

class FaskesFragment : Fragment(), FaskesAdapter.OnItemClickListener {

    private lateinit var viewModel: CariFaskesViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var provinceInput: String
    private lateinit var cityInput: String
    private lateinit var arrData: ArrayList<DataFaskesResponse>
    private val faskesAdapter by lazy { FaskesAdapter(this) }
    private var lat: Double = 0.0
    private var lon: Double = 0.0

    override fun onItemClick(position: Int) {
        val faskes: DataFaskesResponse = arrData.get(position)
        Toast.makeText(activity, "Item $faskes clicked", Toast.LENGTH_SHORT).show()

        val intent = Intent(activity, DetailFaskesActivity::class.java)
        intent.putExtra("namaFaskes", faskes.nama)
        intent.putExtra("kodeFaskes", faskes.kode)
        intent.putExtra("alamatFaskes", faskes.alamat)
        intent.putExtra("telpFaskes", faskes.telp)
        intent.putExtra("jenisFaskes", faskes.jenis_faskes)
        intent.putExtra("statusFaskes", faskes.status)
        startActivity(intent)
    }

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

        val bundle = arguments
        provinceInput = bundle?.getString("provinceInput").toString()
        cityInput = bundle?.getString("cityInput").toString()
        lat = bundle!!.getDouble("lat")
        lon = bundle!!.getDouble("long")

        Log.d("FRAGMENT LAT", lat.toString())
        Log.d("FRAGMENT LONG", lon.toString())
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val repository = Repository()
        val viewModelFactory = CariFaskesViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CariFaskesViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.getFaskes(provinceInput, cityInput)
        activity?.let {
            viewModel.myFaskesResponse.observe(it) { response ->
                if (response.isSuccessful) {
                    response.body()?.let {
                        arrData = it.get5Nearest(lon, lat)
                        faskesAdapter.setData(arrData)
                    }
//                    Log.d("FASKES", response.body().toString())
                } else {
                    Toast.makeText(activity, response.code(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
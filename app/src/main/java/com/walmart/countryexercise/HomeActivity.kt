package com.walmart.countryexercise

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.walmart.core.service.CountryRetrofitFactory
import com.walmart.core.service.CountryServiceImpl
import com.walmart.countryexercise.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(CountryServiceImpl(CountryRetrofitFactory.buildService()))
    }
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val countryList = binding.countryList
        val adapter = CountryListAdapter()
        countryList.adapter = adapter
        countryList.layoutManager = LinearLayoutManager(this)
        viewModel.countryList.observe(this) {
            adapter.submitList(it)
            binding.errorView.visibility = View.GONE
            countryList.visibility = View.VISIBLE
        }

        viewModel.error.observe(this) {
            binding.errorView.text = it
            binding.errorView.visibility = View.VISIBLE
            countryList.visibility = View.GONE
        }
    }
}
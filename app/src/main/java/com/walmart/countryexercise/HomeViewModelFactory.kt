package com.walmart.countryexercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.walmart.core.service.CountryService

class HomeViewModelFactory(private val countryService: CountryService) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java))
            return HomeViewModel(countryService) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
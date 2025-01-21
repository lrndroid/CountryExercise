package com.walmart.countryexercise

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walmart.core.model.Country
import com.walmart.core.service.CountryService
import kotlinx.coroutines.launch

class HomeViewModel(private val countryService: CountryService) : ViewModel() {
    private val _countryList = MutableLiveData<List<Country>>()
    val countryList: LiveData<List<Country>> = _countryList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        fetchCountries()
    }

    internal fun fetchCountries() {
       viewModelScope.launch {
           try {
               _countryList.value = countryService.getCountries()
           } catch (e: Exception) {
               _error.value = "Error fetching countries ${e.message}"
           }
       }
    }
}
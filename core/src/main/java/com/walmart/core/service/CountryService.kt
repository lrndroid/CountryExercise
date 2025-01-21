package com.walmart.core.service

import android.util.Log
import com.walmart.core.model.Country

interface CountryService {
    suspend fun getCountries(): List<Country>
}

class CountryServiceImpl(private val countryAPI: CountryAPI) : CountryService {
    override suspend fun getCountries(): List<Country> {
        try {
            return countryAPI.getCountries()
        } catch (e: Exception) {
            Log.e("CountryServiceImpl", "Error fetching countries", e)
            throw e
        }
    }
}
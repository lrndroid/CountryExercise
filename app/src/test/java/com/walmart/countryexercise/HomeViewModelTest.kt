package com.walmart.countryexercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.walmart.core.model.Country
import com.walmart.core.model.Currency
import com.walmart.core.model.Language
import com.walmart.core.service.CountryService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get: Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private val countryService = mockk<CountryService>()

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testFetchCountries() = runTest {
        coEvery {
            countryService.getCountries()
        } returns listOf(
            Country("New Delhi", "IN", Currency(
                "Rupee", "INR",
                symbol = "₹"
            ), "https://restcountries.eu/data/ind.svg", Language("hi", "Hindi" ), "India", "AS"),
            Country("Washington, D.C.", "US", Currency(
                "Dollar", "USD",
                symbol = "$"
            ), "https://restcountries.eu/data/usa.svg", Language("en", "English" ), "United States of America", "NA")
        )
        homeViewModel = HomeViewModel(countryService)

        val observer = mockk<Observer<List<Country>>>(relaxed = true)
        homeViewModel.countryList.observeForever(observer)
        homeViewModel.fetchCountries()
        testDispatcher.scheduler.advanceUntilIdle()  // Ensures coroutines finish executing

        val countries = homeViewModel.countryList.value
        assertNotNull(countries)
        assertEquals(2, countries!!.size)
        assertEquals("United States of America", countries[1].name)

        homeViewModel.countryList.removeObserver(observer)
    }

    @Test
    fun testFetchCountriesFailure() = runTest{
        coEvery { countryService.getCountries() } throws Exception("Network error")
        homeViewModel = HomeViewModel(countryService)

        val observer = mockk<Observer<String>>(relaxed = true)
        homeViewModel.error.observeForever(observer)

        homeViewModel.fetchCountries()
        testDispatcher.scheduler.advanceUntilIdle()

        val error = homeViewModel.error.value
        assertNotNull(error)
        assertEquals("Error fetching countries Network error", error)
    }
}
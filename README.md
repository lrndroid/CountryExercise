### CountryExercise

# Development

 - The CountryExercise app fetches the countries using Retrofit libraries from the given URL.
 - The view model then collects the countries and updates the country list live data.
 - The activity that is observing the country list updates and submits the data to recyclerview.
 - In case of a network issue, an error message will be shown in the Activity that countries cannot be fetched at this time

# Testing

  - By mocking the country service the HomeViewModel is easily testable
  - Using Mockk and Coroutine testing framework both success and failure scenarios of fetch country are tested.


# Truck Monitor App

An Android app that shows the list of the truck info on the list and map.
![alt text](https://github.com/JunydDEV/android-app-truck-monitor/blob/main/screenshots/image_truck_monitor.png)


## Architecture

This app follows MVVM clean code architecture which is recommended by Google for Android applications.

- Data - Holds the repository and data sources (Remote, Local).
- Domain - Holds the use cases for different functionalities i.e. search, sort.
- Presentation - UI classes built with Jetpack ComposeUI.
- DI - Hilt dependency injection.

## Unit Testing - Mockito | JUnit4 | Turbine
This app has the code coverage for the repository, data source and ViewModel classes. All the classes are 100% covered.

## UI Testing - Espresso
UI tests include the following tests,
- Show Listing
- Show Listing on Map
- Sort Listing
- Search Truck Info

## Libraries
This app includes the following libraries,
- Jetpack Compose
- Hilt Dagger
- Turbine
- Mockito
- Room
- Retrofit
- Kotlin Coroutines
- Kotlin Datetime
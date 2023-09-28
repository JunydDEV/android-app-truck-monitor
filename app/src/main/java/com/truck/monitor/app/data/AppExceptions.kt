package com.truck.monitor.app.data

/**
 * Represents networking unavailability. When a device is not connected to the internet.
 * */
data class NoNetworkException(val title: String, val description: String) : Exception(title)

/**
 * Represents JSON Parsing error in case server returns invalid JSON or unexpected response i.e. HTML etc.
 * */
data class InvalidResponseException(val title: String, val description: String) : Exception(title)

/**
 * Represents search results not found in the local database.
 * */
data class SearchResultNotFoundException(val title: String, val description: String) : Exception(title)

/**
 * Represents Room integrity check failed.
 * */
data class IntegrityCheckFailureException(val title: String, val description: String) : Exception(title)


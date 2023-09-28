package com.truck.monitor.app.data.repository

import com.truck.monitor.app.data.IntegrityCheckFailureException
import com.truck.monitor.app.data.InvalidResponseException
import com.truck.monitor.app.data.NoNetworkException
import com.truck.monitor.app.data.SearchResultNotFoundException
import com.truck.monitor.app.data.model.DataFailureResponse
import javax.inject.Inject

class ExceptionHandler @Inject constructor() {

    fun handle(it: Throwable): DataFailureResponse {
        return when (it) {
            is NoNetworkException -> {
                DataFailureResponse(
                    message = it.title,
                    description = it.description
                )
            }

            is InvalidResponseException -> {
                DataFailureResponse(
                    message = it.title,
                    description = it.description
                )
            }

            is IntegrityCheckFailureException -> {
                DataFailureResponse(
                    message = it.title,
                    description = it.description
                )
            }

            is SearchResultNotFoundException -> {
                DataFailureResponse(
                    message = it.title,
                    description = it.description
                )
            }

            else -> {
                DataFailureResponse(
                    message = "Unknown Error",
                    description = "Request failed due to unknown reasons, please try later."
                )
            }
        }
    }
}
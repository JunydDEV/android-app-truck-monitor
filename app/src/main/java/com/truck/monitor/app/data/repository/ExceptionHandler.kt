package com.truck.monitor.app.data.repository

import com.google.gson.JsonParseException
import com.truck.monitor.app.data.model.DataFailureResponse
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject

class ExceptionHandler @Inject constructor() {

    fun handle(it: Throwable): DataFailureResponse {
        return when (it) {
            is IOException -> {
                DataFailureResponse(
                    message = "Network Error",
                    description = "Make sure your device is connected to a working internet."
                )
            }

            is JSONException -> {
                DataFailureResponse(
                    message = "Invalid Response Error",
                    description = "Something went wrong on server side, please try again."
                )
            }

            is JsonParseException -> {
                DataFailureResponse(
                    message = "Invalid Response Error",
                    description = "Something went wrong on server side, please try again."
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
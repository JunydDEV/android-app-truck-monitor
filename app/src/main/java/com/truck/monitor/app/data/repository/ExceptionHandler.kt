package com.truck.monitor.app.data.repository

import com.google.gson.JsonParseException
import com.truck.monitor.app.data.model.DataRequestError
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject

class ExceptionHandler @Inject constructor() {

    fun handle(it: Throwable): DataRequestError {
        return when (it) {
            is IOException -> {
                DataRequestError(
                    message = "Network Error",
                    description = "Make sure your device is connected to a working internet."
                )
            }

            is JSONException -> {
                DataRequestError(
                    message = "Invalid Response Error",
                    description = "Something went wrong on server side, please try again."
                )
            }

            is JsonParseException -> {
                DataRequestError(
                    message = "Invalid Response Error",
                    description = "Something went wrong on server side, please try again."
                )
            }

            else -> {
                DataRequestError(
                    message = "Unknown Error",
                    description = "Request failed due to unknown reasons, please try later."
                )
            }
        }
    }
}
package com.truck.monitor.app.data.model

sealed class DataState {
    class OnError(val response: DataRequestError) : DataState()
    class OnSuccess<T>(val response: NetworkResponse<T>) : DataState()
}
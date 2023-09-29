package com.truck.monitor.app.data.model.datastate

sealed class DataState {
    class OnError(val response: DataFailureResponse) : DataState()
    class OnSuccess<T>(val response: DataSuccessResponse<T>) : DataState()
}
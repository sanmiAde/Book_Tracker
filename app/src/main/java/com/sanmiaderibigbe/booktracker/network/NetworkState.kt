package com.sanmiaderibigbe.booktracker.network


sealed class NetWorkState {
    object Loading : NetWorkState()
    object Success : NetWorkState()
    object NotLoaded : NetWorkState()
    data class Error(val errorMessage: String?) : NetWorkState()
}

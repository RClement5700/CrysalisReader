package com.clementcorporation.crysalisreader.screens.login

data class LoadingState(val status: Status, val message: String? = null) {
    enum class Status{
        LOADING,
        SUCCESS,
        FAILED,
        IDLE
    }
    companion object{
        val IDLE = LoadingState(Status.IDLE)
        val LOADING = LoadingState(Status.LOADING)
        val FAILED = LoadingState(Status.FAILED)
        val SUCCESS = LoadingState(Status.SUCCESS)
    }
}
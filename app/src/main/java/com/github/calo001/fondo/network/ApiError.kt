package com.github.calo001.fondo.network

import retrofit2.HttpException

class ApiError (error: Throwable?) {
    var message = "An error occurred"
    var code = 0

    constructor(code: Int, message: String) : this(null) {
        this.message = message
        this.code = code
    }

    init {
        if (error is HttpException) {
            error.response()?.errorBody()?.let { message = it.string() }
            code = error.code()
        } else {
            message = error?.message ?: message
        }
    }
}
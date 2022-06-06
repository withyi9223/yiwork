package com.blackview.base.request

import com.blackview.base.http.AppException


open class BaseResponse<T> {
    var message: String? = null
    var code = 0
    var data: T? = null
}

class StartResponse<T> : BaseResponse<T>()

data class SuccessResponse<T>( var data1: T) : BaseResponse<T>()

class EmptyResponse<T> : BaseResponse<T>()

data class FailureResponse<T>(val exception: AppException) : BaseResponse<T>()
package com.android.tools.Activities.Multipart

class Rest_Response<T> {

    private var ResponseCode: String? = null

    private var ResponseData: T? = null

    private var ResponseMessage: String? = null

    fun getResponseCode(): String? {
        return ResponseCode
    }

    fun setResponseCode(ResponseCode: String?) {
        this.ResponseCode = ResponseCode
    }

    fun getResponseData(): T? {
        return ResponseData
    }

    fun setResponseData(ResponseData: T?) {
        this.ResponseData = ResponseData
    }

    fun getResponseMessage(): String? {
        return ResponseMessage
    }

    fun setResponseMessage(ResponseMessage: String?) {
        this.ResponseMessage = ResponseMessage
    }
}
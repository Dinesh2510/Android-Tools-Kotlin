package com.android.tools.Activities.Multipart

class ListResponse_multipart<T> {
    private var ResponseCode: String? = null

    private var ResponseData:ArrayList<T>?=null

    private var ResponseMessage: String? = null

    fun getResponseCode(): String? {
        return ResponseCode
    }

    fun setResponseCode(ResponseCode: String?) {
        this.ResponseCode = ResponseCode
    }

    fun getResponseData(): ArrayList<T> {
        return ResponseData!!
    }

    fun setResponseData(ResponseData: ArrayList<T>) {
        this.ResponseData = ResponseData
    }

    fun getResponseMessage(): String? {
        return ResponseMessage
    }

    fun setResponseMessage(ResponseMessage: String?) {
        this.ResponseMessage = ResponseMessage
    }
}
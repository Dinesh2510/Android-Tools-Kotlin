package com.android.tools.Activities.Mvvm_ex.Model

import com.android.tools.Activities.Mvvm_ex.Model.ResultData
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

class ProductModel {
    @SerializedName("ResponseCode")
    @Expose
    var responseCode: String? = null

    @SerializedName("Result")
    @Expose
    var result: String? = null

    @SerializedName("ResponseMsg")
    @Expose
    var responseMsg: String? = null

    @SerializedName("ResultData")
    @Expose
    var resultData: ResultData? = null
}
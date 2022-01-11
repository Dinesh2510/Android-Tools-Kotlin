package com.android.tools.Activities.Mvvm_ex.Model

import com.android.tools.Activities.Mvvm_ex.Model.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResultData {
    @SerializedName("Banner")
    @Expose
    var banner: List<Bannermv>? = null

    @SerializedName("Catlist")
    @Expose
    var catlist: List<Cat>? = null

    @SerializedName("f_stock")
    @Expose
     var fStock: List<FStock>? = null

    @SerializedName("Couponlist")
    @Expose
    var couponlist: List<Coupon>? = null

    @SerializedName("Main_Data")
    @Expose
    var mainData: MainData? = null

    @SerializedName("Collection")
    @Expose
    var collection : List<Any>? = null

}
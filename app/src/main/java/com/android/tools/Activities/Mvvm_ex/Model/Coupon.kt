package com.android.tools.Activities.Mvvm_ex.Model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class Coupon (

    @SerializedName("id")
    @Expose
    private var id: String? = null,

    @SerializedName("coupn_code")
    @Expose
    private var coupnCode: String? = null,

    @SerializedName("coupon_img")
    @Expose
    private var couponImg: String? = null,

    @SerializedName("coupon_expire_date")
    @Expose
    private var couponExpireDate: String? = null

)
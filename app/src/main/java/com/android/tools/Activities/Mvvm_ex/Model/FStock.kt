package com.android.tools.Activities.Mvvm_ex.Model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class FStock (
    @SerializedName("product_id")
    @Expose
    var productId: String? = null,

    @SerializedName("cityid")
    @Expose
    var cityid: String? = null,

    @SerializedName("catid")
    @Expose
    var catid: String? = null,

    @SerializedName("catname")
    @Expose
    var catname: String? = null,

    @SerializedName("subcatid")
    @Expose
    var subcatid: String? = null,

    @SerializedName("product_discount")
    @Expose
    var productDiscount: String? = null,

    @SerializedName("product_variation")
    @Expose
    var productVariation: String? = null,

    @SerializedName("product_regularprice")
    @Expose
    var productRegularprice: String? = null,

    @SerializedName("product_subscribeprice")
    @Expose
    var productSubscribeprice: String? = null,

    @SerializedName("product_title")
    @Expose
    var productTitle: String? = null,

    @SerializedName("product_img")
    @Expose
    var productImg: String? = null
)
package com.android.tools.Activities.Mvvm_ex.Model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


data class Cat(
    @SerializedName("id")
    @Expose
    var id: String? = null,

    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("cat_img")
    @Expose
    var catImg: String? = null
)
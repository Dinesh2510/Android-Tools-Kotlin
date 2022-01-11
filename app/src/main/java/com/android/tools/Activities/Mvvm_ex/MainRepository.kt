package com.android.tools.Activities.Mvvm_ex

import com.android.tools.api.ApiInterface

class MainRepository constructor(private val retrofitService: ApiInterface) {

    fun getProductsList() = retrofitService.getAllProduct()
}
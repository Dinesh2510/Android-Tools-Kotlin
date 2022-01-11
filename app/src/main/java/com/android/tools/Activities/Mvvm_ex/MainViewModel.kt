package com.android.tools.Activities.Mvvm_ex

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.tools.Activities.Mvvm_ex.Model.ProductModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: MainRepository) : ViewModel() {

    val movieList = MutableLiveData<ProductModel>()
    val errorMessage = MutableLiveData<String>()

    fun getAllProductModels() {

        val response = repository.getProductsList()
        response.enqueue(object : Callback<ProductModel> {
            override fun onResponse(call: Call<ProductModel>, response: Response<ProductModel>) {
                Log.e("MainViewModel_resp", "onResponse: "+response.body().toString() )
                movieList.postValue(response.body())
            }

            override fun onFailure(call: Call<ProductModel>, t: Throwable) {
                Log.e("MainViewModel_error", t.getLocalizedMessage().toString())
                errorMessage.value = t.message
            }

        })
    }
}
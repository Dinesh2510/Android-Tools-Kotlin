package com.android.tools.api

import com.android.tools.Activities.Multipart.ListResponse_multipart
import com.android.tools.Activities.Mvvm_ex.Model.ProductModel
import com.android.tools.Activities.Multipart.RestResponseModel
import com.android.tools.Activities.Multipart.Rest_Response
import com.android.tools.PaymentListResponce
import com.android.tools.model.*

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {


    @GET("category")
    fun getFoodCategory(): Call<ListResponse<FoodCategoryModel>>

    @POST("item")
    fun getFoodItem(
        @Body map: HashMap<String, String>,
        @Query("page") strPageNo: String
    ): Call<RestResponse<FoodItemResponseModel>>

    @GET("banner")
    fun getBanner(): Call<ListResponse<BannerModel>>

    @POST("paymenttype")
    fun getPaymentType(@Body map: HashMap<String, String>): Call<PaymentListResponce>

    @POST("paymenttype")
    fun setOrderPayment(@Body map: HashMap<String, String>): Call<SingleResponse>
    //Get Upload wallpaper Api 14

    @Multipart
    @POST("uploadwallpaper.php")
    fun getUploadwallpaper(@Part("user_id") userId: RequestBody,
                           @Part("category_id") category_id: RequestBody,
                           @Part("wallpaper_color") wallpaper_color: RequestBody,
                           @Part("wallpaper_height") height: RequestBody,
                           @Part("name") name: RequestBody,
                           @Part wallpaperImage: MultipartBody.Part?): Call<Rest_Response<RestResponseModel>>

    @GET("getcategories.php")
    fun getCategory(): Call<ListResponse_multipart<CategoryModel>>

    @GET("products.json")
    fun getAllProduct(): Call<ProductModel>

}
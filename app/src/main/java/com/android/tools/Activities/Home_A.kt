package com.android.tools.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.*
import com.android.tools.Activities.Banners.Banner_Viewpager
import com.android.tools.Activities.Banners.Banner_rv
import com.android.tools.Activities.Base64Img.DrawingImg.Drawing_Activity
import com.android.tools.Activities.Chips.ChipsList_A
import com.android.tools.Activities.FlexBox.FlexBoxActivity
import com.android.tools.Activities.Multipart.AddWallpaperActivity
import com.android.tools.Activities.MultipleSeclection.MultipleSelectionActivity
import com.android.tools.Activities.Mvvm_ex.List_Of_Product
import com.android.tools.Activities.RadioRecyclerview.RadioActivity
import com.android.tools.Activities.SingleSelection.PaymentPayActivity
import com.android.tools.Activities.VerticalViewPagerApp.Main_A
import com.android.tools.R
import com.bumptech.glide.Glide

import com.android.tools.api.ApiClient
import com.android.tools.api.ListResponse
import com.android.tools.api.RestResponse
import com.android.tools.base.BaseAdaptor
import com.android.tools.model.*
import com.android.tools.utils.Common
import com.android.tools.utils.Common.alertErrorOrValidationDialog
import com.android.tools.utils.Common.dismissLoadingProgress
import com.android.tools.utils.Common.isCheckNetwork
import com.android.tools.utils.Common.showLoadingProgress
import com.android.tools.utils.SharePreference
import com.android.tools.utils.SharePreference.Companion.isCurrancy
import com.android.tools.utils.SharePreference.Companion.isLinearLayoutManager
import com.android.tools.utils.SharePreference.Companion.isMaximum
import com.android.tools.utils.SharePreference.Companion.isMiniMum
import com.android.tools.utils.SharePreference.Companion.isMiniMumQty
import com.android.tools.utils.SharePreference.Companion.mapKey
import com.android.tools.utils.SharePreference.Companion.setStringPref
import com.android.tools.utils.SharePreference.Companion.userId
import com.android.tools.utils.SharePreference.Companion.userRefralAmount
import com.android.tools.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_persistent_bottom_sheet.*

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Home_A : AppCompatActivity() {
    var timer: Timer? = null
    var foodCategoryAdapter: BaseAdaptor<FoodCategoryModel>? = null
    private var foodCategoryList: ArrayList<FoodCategoryModel>? = null
    var bannerList: ArrayList<BannerModel>? = null
    var bannerAdapter: BaseAdaptor<BannerModel>? = null
    var foodAdapter: BaseAdaptor<FoodItemModel>? = null
    private var foodList: ArrayList<FoodItemModel>? = null
    var foodCategoryId = "";
    var manager1: GridLayoutManager? = null
    var CurrentPageNo: Int = 1
    var TOTAL_PAGES: Int = 0
    var isLoding: Boolean = true
    var scrollView: NestedScrollView? = null
    var pos = 0
    var bannerDatalist = ArrayList<BannerModel>()
    var mBehavior: BottomSheetBehavior<*>? = null
    var mBottomSheetDialog: BottomSheetDialog? = null
    lateinit var bottom_sheet: View
    var snackbar: Snackbar? = null

    //    var timer: Timer? = null
    val isAdded = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        //Set User id
        setStringPref(this@Home_A, userId, "571")
        foodList = ArrayList()
        scrollView = findViewById(R.id.scrollView)

        /* val view = layoutInflater.inflate(R.layout.layout_persistent_bottom_sheet, null)
         val dialog = BottomSheetDialog(this)
         dialog.setContentView(view)
         dialog.dismiss()*/


        if (SharePreference.getBooleanPref(this, isLinearLayoutManager)) {
            manager1 = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
            ic_grid?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_listitem,
                    null
                )
            )
        } else {
            manager1 = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
            ic_grid?.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_grid,
                    null
                )
            )
        }

        rvFoodSubcategory?.layoutManager = manager1

        if (isCheckNetwork(this)) {
            callApiBanner()
        } else {
            alertErrorOrValidationDialog(
                this,
                resources.getString(R.string.no_internet)
            )
        }
//broadcastReceiver Call here
        Utils.registerConnectivity(this) { response ->
            if (response.equals("disconnected", ignoreCase = true)) {
                snackbar = Snackbar.make(
                    findViewById(android.R.id.content),
                    R.string.no_internet,
                    Snackbar.LENGTH_INDEFINITE
                )
                val snackbarView: View = snackbar!!.getView()
                snackbarView.setBackgroundColor(resources.getColor(R.color.red))
                snackbar!!.show()
            } else {
                if (snackbar != null) snackbar!!.dismiss()
            }
        }


        ic_grid.setOnClickListener {
            if (SharePreference.getBooleanPref(applicationContext, isLinearLayoutManager)) {
                manager1 = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
                SharePreference.setBooleanPref(this, isLinearLayoutManager, false)
                ic_grid.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_grid,
                        null
                    )
                )
            } else {
                manager1 = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)
                SharePreference.setBooleanPref(this!!, isLinearLayoutManager, true)
                ic_grid.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_listitem,
                        null
                    )
                )
            }
            rvFoodSubcategory.layoutManager = manager1
        }

        if (isAdded) {
            scrollView!!.viewTreeObserver.addOnScrollChangedListener {
                val view1 = scrollView!!.getChildAt(scrollView!!.childCount - 1) as View
                val diff: Int =
                    view1.bottom - (scrollView!!.height + scrollView!!.scrollY)
                if (diff == 0 && CurrentPageNo < TOTAL_PAGES) {
                    isLoding = false
                    CurrentPageNo += 1
                    if (isCheckNetwork(this!!)) {
                        callApiFood(
                            foodCategoryId,
                            isFirstTime = false,
                            isSelect = false,
                            isFristTimeSelect = false
                        )
                    } else {
                        alertErrorOrValidationDialog(
                            this!!,
                            resources.getString(R.string.no_internet)
                        )
                    }
                }
            }
        }


        swiperefresh.setOnRefreshListener { // Your code to refresh the list here.
            if (isCheckNetwork(this!!)) {
                swiperefresh.isRefreshing = false
                foodList!!.clear()
                isLoding = true
                CurrentPageNo = 1
                pos = 0
                timer?.cancel()
                TOTAL_PAGES = 0

                if (isCheckNetwork(this!!)) {
                    bannerList?.clear()
                    callApiBanner()
                } else {
                    alertErrorOrValidationDialog(
                        this!!,
                        resources.getString(R.string.no_internet)
                    )
                }
            } else {
                alertErrorOrValidationDialog(
                    this!!,
                    resources.getString(R.string.no_internet)
                )
            }
        }

        /*Bottom Sheet*/
        bottom_sheet = findViewById(R.id.bottom_sheet)
        mBehavior = BottomSheetBehavior.from(bottom_sheet)
        ivMenu.setOnClickListener {
            showBottomSheetDialog()

        }


    }


    private fun callApiBanner() {
        showLoadingProgress(this!!)
        val call = ApiClient.getClient.getBanner()
        call.enqueue(object : Callback<ListResponse<BannerModel>> {
            override fun onResponse(
                call: Call<ListResponse<BannerModel>>,
                response: Response<ListResponse<BannerModel>>
            ) {
                if (response.code() == 200) {
                    val restResponce: ListResponse<BannerModel> = response.body()!!
                    if (restResponce.status == 1) {
                        if (restResponce.data!!.size > 0) {
                            bannerList = restResponce.data
                            bannerDatalist.addAll(restResponce.data!!)
                            callApiCategoryFood()
                        } else {
                            callApiCategoryFood()
                        }
                    } else if (restResponce.status == 0) {
                        callApiCategoryFood()
                    }
                } else {
                    callApiCategoryFood()
                }
            }

            override fun onFailure(call: Call<ListResponse<BannerModel>>, t: Throwable) {
                dismissLoadingProgress()
                alertErrorOrValidationDialog(
                    this@Home_A,
                    resources.getString(R.string.error_msg)
                )
            }
        })

    }


    private fun callApiCategoryFood() {
        val call = ApiClient.getClient.getFoodCategory()
        call.enqueue(object : Callback<ListResponse<FoodCategoryModel>> {
            override fun onResponse(
                call: Call<ListResponse<FoodCategoryModel>>,
                response: Response<ListResponse<FoodCategoryModel>>
            ) {
                if (response.code() == 200) {
                    val restResponce: ListResponse<FoodCategoryModel> = response.body()!!
                    if (restResponce.status == 1) {
                        if (restResponce.data!!.size > 0) {
                            foodCategoryList = restResponce.data!!
                            foodCategoryId = foodCategoryList!![0].getId()!!
                            foodCategoryList!![0].setSelect(true)
                            callApiFood(
                                foodCategoryId,
                                isFirstTime = true,
                                isSelect = false,
                                isFristTimeSelect = true
                            )
                        }
                    } else if (restResponce.status == 0) {
                        dismissLoadingProgress()
                        alertErrorOrValidationDialog(
                            this@Home_A,
                            restResponce.message
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ListResponse<FoodCategoryModel>>, t: Throwable) {
                dismissLoadingProgress()
                alertErrorOrValidationDialog(
                    this@Home_A,
                    resources.getString(R.string.error_msg)
                )
            }
        })
    }

    private fun callApiFood(
        id: String?,
        isFirstTime: Boolean,
        isSelect: Boolean,
        isFristTimeSelect: Boolean
    ) {
        if (!isFirstTime) {
            showLoadingProgress(this)
        }
        if (isSelect) {
            showLoadingProgress(this)
            foodList!!.clear()
        }
        val map = HashMap<String, String>()
        map["cat_id"] = id!!
        if (SharePreference.getBooleanPref(this, SharePreference.isLogin)) {
            map["user_id"] =
                SharePreference.getStringPref(this, SharePreference.userId)!!
        }
        val call = ApiClient.getClient.getFoodItem(map, CurrentPageNo.toString())
        call.enqueue(object : Callback<RestResponse<FoodItemResponseModel>> {
            override fun onResponse(
                call: Call<RestResponse<FoodItemResponseModel>>,
                response: Response<RestResponse<FoodItemResponseModel>>
            ) {
                if (response.code() == 200) {
                    val restResponce: RestResponse<FoodItemResponseModel> = response.body()!!
                    if (restResponce.getStatus().equals("1")) {
                        if (!isFristTimeSelect) {
                            dismissLoadingProgress()
                        }
                        val foodItemResponseModel: FoodItemResponseModel = restResponce.getData()!!
                        CurrentPageNo = foodItemResponseModel.currentPage!!.toInt()
                        TOTAL_PAGES = foodItemResponseModel.lastPage!!.toInt()
                        for (i in 0 until foodItemResponseModel.data!!.size) {
                            val foodItemModel = FoodItemModel()
                            foodItemModel.id = foodItemResponseModel.data!!.get(i).id
                            foodItemModel.itemName = foodItemResponseModel.data!![i].itemName

                            foodItemModel.variation = foodItemResponseModel.data!![i].variation

                            foodItemModel.itemimage = foodItemResponseModel.data!![i].itemimage

                            foodItemModel.isFavorite = foodItemResponseModel.data!![i].isFavorite

                            foodList!!.add(foodItemModel)
                        }

                        setStringPref(
                            applicationContext,
                            userRefralAmount,
                            restResponce.getReferral_amount()!!
                        )
                        setStringPref(
                            applicationContext,
                            isCurrancy,
                            restResponce.getCurrency()!!
                        )
                        setStringPref(
                            applicationContext,
                            isMiniMum,
                            restResponce.getMin_order_amount()!!
                        )
                        setStringPref(
                            applicationContext,
                            isMaximum,
                            restResponce.getMax_order_amount()!!
                        )
                        setStringPref(
                            applicationContext,
                            isMiniMumQty,
                            restResponce.getMax_order_qty()!!
                        )
                        setStringPref(
                            applicationContext,
                            mapKey,
                            restResponce.getMap().toString()
                        )

                        setFoodAdaptor(isFirstTime, isFristTimeSelect)
                        if (isFristTimeSelect) {
                            if (SharePreference.getBooleanPref(
                                    applicationContext,
                                    SharePreference.isLogin
                                )
                            ) {
                                if (isCheckNetwork(applicationContext)) {

                                } else {
                                    alertErrorOrValidationDialog(
                                        this@Home_A,
                                        resources.getString(R.string.no_internet)
                                    )
                                }
                            } else {
                                dismissLoadingProgress()
                            }
                        }
                    } else if (restResponce.getMessage().equals("0")) {
                        if (!isFirstTime) {
                            dismissLoadingProgress()
                            alertErrorOrValidationDialog(
                                this@Home_A,
                                restResponce.getMessage()
                            )
                        }

                    }
                } else {
                    val error = JSONObject(response.errorBody()!!.string())
                    if (error.getString("status").equals("2")) {
                        dismissLoadingProgress()
                        Common.setLogout(this@Home_A)
                    } else {
                        dismissLoadingProgress()
                        alertErrorOrValidationDialog(
                            this@Home_A,
                            error.getString("message")
                        )
                    }
                }

            }

            override fun onFailure(call: Call<RestResponse<FoodItemResponseModel>>, t: Throwable) {
                dismissLoadingProgress()
                alertErrorOrValidationDialog(
                    this@Home_A,
                    resources.getString(R.string.error_msg)
                )
            }
        })
    }

    private fun setFoodCategoryAdaptor() {
        foodCategoryAdapter =
            object : BaseAdaptor<FoodCategoryModel>(this, foodCategoryList!!) {
                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                @SuppressLint("ResourceType")
                override fun onBindData(
                    holder: RecyclerView.ViewHolder?,
                    `val`: FoodCategoryModel,
                    position: Int
                ) {
                    val tvFoodCategoryName: TextView =
                        holder!!.itemView.findViewById(R.id.tvFoodCategoryName)
                    val ivFoodCategory: ImageView =
                        holder.itemView.findViewById(R.id.ivFoodCategory)
                    val llBarger: LinearLayout = holder.itemView.findViewById(R.id.llBarger)
                    val ViewFrist: View = holder.itemView.findViewById(R.id.ViewFrist)
                    val ViewLast: View = holder.itemView.findViewById(R.id.ViewLast)

                    when (position) {
                        0 -> {
                            ViewFrist.visibility = View.VISIBLE
                            ViewLast.visibility = View.GONE
                        }
                        (foodCategoryList!!.size - 1) -> {
                            ViewFrist.visibility = View.GONE
                            ViewLast.visibility = View.VISIBLE
                        }
                        else -> {
                            ViewFrist.visibility = View.GONE
                            ViewLast.visibility = View.GONE
                        }
                    }

                    if (foodCategoryList!!.get(position).isSelect()!!) {
                        llBarger.background = ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.bg_strock_orange5,
                            null
                        )
                    } else {
                        llBarger.background = null
                    }
                    tvFoodCategoryName.text = foodCategoryList!!.get(position).getCategory_name()
                    Glide.with(this@Home_A).load(foodCategoryList!!.get(position).getImage())
                        .placeholder(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_placeholder,
                                null
                            )
                        )
                        .into(ivFoodCategory)
                    holder.itemView.setOnClickListener {
                        for (i in 0 until foodCategoryList!!.size) {
                            foodCategoryList!!.get(i).setSelect(false)
                        }
                        foodCategoryList!!.get(position).setSelect(true)
                        foodCategoryId = foodCategoryList!!.get(position).getId()!!
                        foodList!!.clear()
                        notifyDataSetChanged()
                        isLoding = true
                        CurrentPageNo = 1
                        TOTAL_PAGES = 0
                        if (SharePreference.getBooleanPref(this@Home_A, isLinearLayoutManager)) {
                            manager1 =
                                GridLayoutManager(
                                    applicationContext,
                                    1,
                                    GridLayoutManager.VERTICAL,
                                    false
                                )
                            ic_grid.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.ic_listitem,
                                    null
                                )
                            )
                        } else {
                            manager1 =
                                GridLayoutManager(
                                    applicationContext,
                                    2,
                                    GridLayoutManager.VERTICAL,
                                    false
                                )
                            ic_grid.setImageDrawable(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.ic_grid,
                                    null
                                )
                            )
                        }
                        rvFoodSubcategory.layoutManager = manager1
                        if (isCheckNetwork(applicationContext)) {
                            callApiFood(foodCategoryId, true, true, false)
                        } else {
                            alertErrorOrValidationDialog(
                                this@Home_A,
                                resources.getString(R.string.no_internet)
                            )
                        }
                    }
                }

                override fun setItemLayout(): Int {
                    return R.layout.row_foodcategory
                }

            }
        if (isAdded) {
            rvFoodCategory?.adapter = foodCategoryAdapter
            rvFoodCategory?.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            rvFoodCategory?.itemAnimator = DefaultItemAnimator()
            rvFoodCategory?.isNestedScrollingEnabled = true
        }
    }

    fun setFoodAdaptor(isFirstTime: Boolean, fristTimeSelect: Boolean) {
        if (isFirstTime) {
            if (fristTimeSelect) {
                setFoodCategoryAdaptor()
                if (bannerList != null) {
                    rlBenner?.visibility = View.VISIBLE
                    loadPagerImages(bannerList!!)
                } else {
                    rlBenner?.visibility = View.GONE
                }
            }
            foodAdapter = object : BaseAdaptor<FoodItemModel>(this, foodList!!) {
                @SuppressLint("NewApi", "ResourceType")
                override fun onBindData(
                    holder: RecyclerView.ViewHolder?,
                    `val`: FoodItemModel,
                    position: Int
                ) {
                    val tvFoodName: TextView = holder!!.itemView.findViewById(R.id.tvFoodName)
                    val tvFoodPriceGrid: TextView =
                        holder.itemView.findViewById(R.id.tvFoodPriceGrid)
                    val ivFood: ImageView = holder.itemView.findViewById(R.id.ivFood)
                    val icLike: ImageView = holder.itemView.findViewById(R.id.icLike)
                    val tvFoodOnSale: TextView = holder.itemView.findViewById(R.id.tvFoodOnSale)

                    tvFoodName.text = foodList!![position].itemName
                    Common.getPrice(
                        foodList!![position].variation?.get(0)?.productPrice!!.toDouble(),
                        tvFoodPriceGrid,
                        this@Home_A
                    )
                    if (SharePreference.getBooleanPref(applicationContext, isLinearLayoutManager)) {
                        Glide.with(applicationContext)
                            .load(foodList!![position].itemimage?.image).centerCrop()
                            .placeholder(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.ic_placeholder,
                                    null
                                )
                            )
                            .into(ivFood)
                    } else {
                        Glide.with(applicationContext)
                            .load(foodList!![position].itemimage!!.image).centerCrop()
                            .optionalFitCenter()
                            .placeholder(
                                ResourcesCompat.getDrawable(
                                    resources,
                                    R.drawable.ic_placeholder,
                                    null
                                )
                            )
                            .into(ivFood)
                    }

                    holder.itemView.setOnClickListener {
                        /* startActivity(
                             Intent(
                                 this,
                                 FoodDetailActivity::class.java
                             ).putExtra("foodItemId", foodList!![position].id)
                         )*/
                    }


                    if (foodList!![position].variation?.get(0)?.salePrice != 0) {
                        tvFoodOnSale.visibility = View.VISIBLE
                    } else {
                        tvFoodOnSale.visibility = View.GONE

                    }

                    tvFoodPriceGrid.visibility = View.VISIBLE

                    if (foodList!!.get(position).isFavorite.equals("0")) {
                        icLike.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_unlike,
                                null
                            )
                        )
                        icLike.imageTintList = ColorStateList.valueOf(Color.WHITE)
                    } else {
                        icLike.setImageDrawable(
                            ResourcesCompat.getDrawable(
                                resources,
                                R.drawable.ic_favourite_like,
                                null
                            )
                        )
                        icLike.imageTintList = ColorStateList.valueOf(Color.WHITE)
                    }


                    icLike.setOnClickListener {
                        if (SharePreference.getBooleanPref(this@Home_A, SharePreference.isLogin)) {
                            if (foodList!!.get(position).isFavorite.equals("0")) {
                                val map = HashMap<String, String>()
                                map["item_id"] = foodList!!.get(position).id!!
                                map["user_id"] = SharePreference.getStringPref(
                                    this@Home_A,
                                    SharePreference.userId
                                )!!
                                if (isCheckNetwork(this@Home_A)) {

                                } else {
                                    alertErrorOrValidationDialog(
                                        this@Home_A,
                                        resources.getString(R.string.no_internet)
                                    )
                                }
                            }
                        } else {
                            //// openActivity(LoginActivity::class.java)
                            // this.finish()
                        }

                    }
                }

                override fun setItemLayout(): Int {
                    return R.layout.row_foodsubcategory
                }

            }

            if (isAdded) {
                rvFoodSubcategory?.adapter = foodAdapter
                rvFoodSubcategory?.itemAnimator = DefaultItemAnimator()
            }
        } else {
            foodAdapter!!.notifyDataSetChanged()
        }
    }


    private fun loadPagerImages(imageHase: ArrayList<BannerModel>) {
        bannerAdapter = object : BaseAdaptor<BannerModel>(this@Home_A, imageHase) {
            @SuppressLint("NewApi", "ResourceType")
            override fun onBindData(
                holder: RecyclerView.ViewHolder?,
                `val`: BannerModel,
                position: Int
            ) {

                val ivFood: ImageView = holder!!.itemView.findViewById(R.id.ivBannereSlider)
                Glide.with(this@Home_A).load(imageHase.get(position).getImage()).placeholder(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.ic_placeholder,
                        null
                    )
                ).into(ivFood)

                /*   val view1: View = holder.itemView.findViewById(R.id.view1)
                   if (position == 0) {
                       view1.visibility = View.VISIBLE
                   } else {
                       view1.visibility = View.GONE
                   }
   */
                holder.itemView.setOnClickListener {
                    if (`val`.getType().toString() == "item") {
                        /* startActivity(
                             Intent(
                                 this@Home_A,
                                 FoodDetailActivity::class.java
                             ).putExtra("foodItemId", `val`.getItemId().toString())
                                 .putExtra("isItemActivity", resources.getString(R.string.home))
                         )*/
                    } else if (`val`.getType().toString() == "category") {
                        /*startActivity(
                            Intent(this@Home_A, CategoryByFoodActivity::class.java).putExtra(
                                "CategoryId",
                                `val`.getCatId().toString()
                            ).putExtra("CategoryName", `val`.getCategoryName().toString())
                        )*/
                    }
                }
            }

            override fun setItemLayout(): Int {
                return R.layout.row_bannerslider
            }

        }
        rvBanner?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvBanner?.adapter = bannerAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(null)
        rvBanner?.onFlingListener = null
        rvBanner?.let { snapHelper.attachToRecyclerView(it) }
        timer = Timer()
        rvBanner?.let { timer?.schedule(AutoScrollTask(pos, it, imageHase), 0, 5000L) }
    }


    private class AutoScrollTask(
        private var position: Int,
        private var rvBanner: RecyclerView,
        private var arrayList: ArrayList<BannerModel>
    ) : TimerTask() {
        override fun run() {
            if (arrayList.size > position) {

                if (position == arrayList.size - 1) {
                    position = 0
                } else {
                    position++
                }

            }
            rvBanner.smoothScrollToPosition(position)
        }
    }

    override fun onPause() {
        super.onPause()
        if (timer != null) {
            timer!!.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        if (timer != null) {
            if (bannerDatalist != null) {
                timer = Timer()
                rvBanner?.let { timer?.schedule(AutoScrollTask(pos, it, bannerDatalist), 0, 5000L) }
            }


        }


    }

    private fun showBottomSheetDialog() {
        if (mBehavior!!.state == BottomSheetBehavior.STATE_EXPANDED) {
            mBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        }
        val view: View = layoutInflater.inflate(R.layout.layout_persistent_bottom_sheet, null)

        view.findViewById<View>(R.id.bt_close).setOnClickListener { mBottomSheetDialog!!.hide() }
        mBottomSheetDialog = BottomSheetDialog(this)
        mBottomSheetDialog?.setContentView(view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBottomSheetDialog?.getWindow()!!
                .addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }
        onclickViews(view)
        // set background transparent
        (view.parent as View).setBackgroundColor(resources.getColor(android.R.color.transparent))
        mBottomSheetDialog?.show()
        mBottomSheetDialog?.setOnDismissListener(DialogInterface.OnDismissListener {
            mBottomSheetDialog = null
        })
    }

    private fun onclickViews(view: View) {
        view.findViewById<View>(R.id.card_home).setOnClickListener {
            startActivity(Intent(this@Home_A, Home_A::class.java))
        }

        view.findViewById<View>(R.id.card_flex).setOnClickListener {
            startActivity(Intent(this@Home_A, FlexBoxActivity::class.java))
        }
        view.findViewById<View>(R.id.card_multiple).setOnClickListener {
            startActivity(Intent(this@Home_A, MultipleSelectionActivity::class.java))
        }
        view.findViewById<View>(R.id.card_chips_rv).setOnClickListener {
            startActivity(Intent(this@Home_A, ChipsList_A::class.java))
        }
        view.findViewById<View>(R.id.card_PaymentPayActivity).setOnClickListener {
            startActivity(Intent(this@Home_A, PaymentPayActivity::class.java))
        }
        view.findViewById<View>(R.id.card_banner_rv).setOnClickListener {
            startActivity(Intent(this@Home_A, Banner_rv::class.java))
        }
        view.findViewById<View>(R.id.card_banner_viewpager).setOnClickListener {
            startActivity(Intent(this@Home_A, Banner_Viewpager::class.java))
        }
        view.findViewById<View>(R.id.card_multiprt).setOnClickListener {
            startActivity(Intent(this@Home_A, AddWallpaperActivity::class.java))
        }
        view.findViewById<View>(R.id.card_draw).setOnClickListener {
            startActivity(Intent(this@Home_A, Drawing_Activity::class.java))
        }
        view.findViewById<View>(R.id.card_base64).setOnClickListener {
            startActivity(Intent(this@Home_A, Main_A::class.java))
        }
        view.findViewById<View>(R.id.card_mvvm).setOnClickListener {
            startActivity(Intent(this@Home_A, List_Of_Product::class.java))
        }
        view.findViewById<View>(R.id.card_radio).setOnClickListener {
            startActivity(Intent(this@Home_A, RadioActivity::class.java))
        }

    }

     var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
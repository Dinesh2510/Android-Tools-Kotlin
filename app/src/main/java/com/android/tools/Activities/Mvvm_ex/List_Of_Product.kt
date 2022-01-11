package com.android.tools.Activities.Mvvm_ex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.tools.Activities.Multipart.ApiClient_Multipart
import com.android.tools.R
import com.android.tools.api.ApiInterface
import kotlinx.android.synthetic.main.activity_multiple_selection.*
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.android.tools.Activities.Home_A
import com.android.tools.Activities.Mvvm_ex.Model.Bannermv
import com.android.tools.Activities.Mvvm_ex.Model.Cat
import com.android.tools.Activities.Mvvm_ex.Model.FStock
import com.android.tools.Activities.Mvvm_ex.Model.ProductModel
import com.android.tools.model.BannerModel
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

class List_Of_Product : AppCompatActivity() {
    private val TAG = "List_Of_Product"
    lateinit var viewModel: MainViewModel
    var timer: Timer? = null
    var pos = 0

    private val retrofitService = ApiClient_Multipart.getClient

    var recyclerView: RecyclerView? = null
    var recyclerView_banner: RecyclerView? = null
    var recyclerview_Catlist: RecyclerView? = null

    private var mAdapter: MainAdapter? = null
    private var bannerAdapter: BannerAdapter_mvvm? = null
    private var category_adapter: Cat_Adapter? = null

    private lateinit var mProductModel: List<FStock>
    private lateinit var bannerModel: List<Bannermv>
    private lateinit var categoryModel: List<Cat>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_product)

        viewModel =
            ViewModelProvider(this, MyViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        recyclerView = findViewById<View>(R.id.recyclerview_product) as RecyclerView
        recyclerView_banner = findViewById<View>(R.id.recyclerView_banner) as RecyclerView
        recyclerview_Catlist = findViewById<View>(R.id.recyclerview_Catlist) as RecyclerView

        viewModel.movieList.observe(this, Observer {
            if (it != null) {
                mProductModel = it.resultData!!.fStock!!
                updateRecycler(mProductModel)
                Log.d(TAG, "onCreate: " + mProductModel)

                bannerModel = it.resultData!!.banner!!
                updateRecycler_banner(bannerModel)

                categoryModel = it.resultData!!.catlist!!
                updateRecycler_Category(categoryModel as ArrayList<Cat>)
            }
        })

        viewModel.errorMessage.observe(this, Observer {

        })
        viewModel.getAllProductModels()

    }

    private fun updateRecycler_Category(categoryModel: ArrayList<Cat>) {

        category_adapter = Cat_Adapter(this, categoryModel)
        recyclerview_Catlist?.apply {
            this.layoutManager =
                GridLayoutManager(this@List_Of_Product, 2, GridLayoutManager.HORIZONTAL, false)
            this.adapter = category_adapter
            category_adapter!!.notifyDataSetChanged()
        }

    }

    private fun updateRecycler_banner(bannerModel: List<Bannermv>) {
        bannerAdapter = BannerAdapter_mvvm(
            this@List_Of_Product,
            bannerModel,
            object : BannerAdapter_mvvm.RecyclerTouchListener {
                override fun onClickBannermv(item: Bannermv?, position: Int) {

                    Toast.makeText(this@List_Of_Product, "" + item!!.title, Toast.LENGTH_LONG)
                        .show()
                }
            })
        recyclerView_banner?.apply {
            this.layoutManager =
                LinearLayoutManager(this@List_Of_Product, LinearLayoutManager.HORIZONTAL, false)
            this.adapter = bannerAdapter
            bannerAdapter!!.notifyDataSetChanged()
        }
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(null)
        recyclerView_banner?.onFlingListener = null
        recyclerView_banner?.let { snapHelper.attachToRecyclerView(it) }
        timer = Timer()
        recyclerView_banner?.let { timer?.schedule(AutoScrollTask(pos, it, bannerModel), 0, 5000L) }

    }

    fun updateRecycler(mProductModel: List<FStock>) {
        mAdapter = MainAdapter()
        recyclerView?.apply {
            this.layoutManager =
                GridLayoutManager(this@List_Of_Product, 2, GridLayoutManager.HORIZONTAL, false)
            this.adapter = mAdapter
            mAdapter?.setFStockList(mProductModel)
        }
    }
    private class AutoScrollTask(
        private var position: Int,
        private var rvBanner: RecyclerView,
        private var arrayList: List<Bannermv>
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
            if (bannerModel != null) {
                timer = Timer()
                recyclerView_banner?.let { timer?.schedule(AutoScrollTask(pos, it, bannerModel), 0, 5000L) }
            }


        }


    }
}
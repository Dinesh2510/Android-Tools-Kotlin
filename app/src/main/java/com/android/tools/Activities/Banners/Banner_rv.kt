package com.android.tools.Activities.Banners

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.android.tools.R
import com.android.tools.api.ApiRequest
import com.android.tools.api.Callback
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class Banner_rv : AppCompatActivity(), BannerRvAdapter.RecyclerTouchListener {
    var myRecyclerBanner: RecyclerView? = null
    var position = 0
    var timer: Timer? = null
    var timerTask: TimerTask? = null
    var layoutManager: LinearLayoutManager? = null
    var bannerAdapter: BannerRvAdapter? = null
    var bannerList: MutableList<Banner> = ArrayList<Banner>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_rv)
        myRecyclerBanner = findViewById(R.id.myRecyclerBanner)
        layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        myRecyclerBanner?.setLayoutManager(layoutManager)
        setbanner()
        getHome()
    }

    private fun getHome() {
        ApiRequest.callApi(
            this@Banner_rv,
            "http://pixeldev.in/webservices/e_commerce/GetBannerList.php",
            JSONObject(),
            object : Callback {
                override fun response(resp: String) {
                    parseUserInfo(resp)
                }
            })
    }

    private fun parseUserInfo(resp: String) {
        try {
            val jsonObject = JSONObject(resp)
            val code = jsonObject.optString("status")
            if (code == "Success") {
//                prefs.edit().putString(Variables.uPassions, jsonObject.toString()).apply();
                bannerList.clear()
                val msgArray = jsonObject.optJSONArray("banner_info")
                for (i in 0 until msgArray.length()) {
                    val model = Banner()
                    model.img = msgArray.getJSONObject(i).optString("image")
                    model.id = msgArray.getJSONObject(i).optString("title")
                    bannerList.add(model)
                }
                bannerAdapter = BannerRvAdapter(this@Banner_rv, bannerList, this)
                myRecyclerBanner!!.adapter = bannerAdapter
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun setbanner() {
        position = 0
        myRecyclerBanner!!.scrollToPosition(position)
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(myRecyclerBanner)
        myRecyclerBanner!!.smoothScrollBy(5, 0)
        myRecyclerBanner!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == 1) {
                    stopAutoScrollBanner()
                } else if (newState == 0) {
                    position = layoutManager!!.findFirstCompletelyVisibleItemPosition()
                    runAutoScrollBanner()
                }
            }
        })
    }


    private fun stopAutoScrollBanner() {
        if (timer != null && timerTask != null) {
            timerTask!!.cancel()
            timer!!.cancel()
            timer = null
            timerTask = null
            position = layoutManager!!.findFirstCompletelyVisibleItemPosition()
        }
    }

    private fun runAutoScrollBanner() {
        if (timer == null && timerTask == null) {
            timer = Timer()
            timerTask = object : TimerTask() {
                override fun run() {
                    try {
                        if (position == myRecyclerBanner!!.adapter!!.itemCount - 1) {
                            position = 0
                            myRecyclerBanner!!.smoothScrollBy(5, 0)
                            myRecyclerBanner!!.smoothScrollToPosition(position)
                        } else {
                            position++
                            myRecyclerBanner!!.smoothScrollToPosition(position)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            timer!!.schedule(timerTask, 4000, 4000)
        }
    }

    override fun onClickBannerItem(titel: String?, position: Int) {
        Toast.makeText(applicationContext, "" + titel, Toast.LENGTH_SHORT).show()
    }
}
package com.android.tools.Activities.Banners

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.LinearLayout
import com.android.tools.R
import com.android.tools.utils.EnchantedViewPager
import com.android.tools.api.ApiRequest
import com.android.tools.api.Callback
import org.json.JSONException
import org.json.JSONObject
import com.android.tools.utils.OnClick
import android.os.Handler
import android.util.Log
import android.widget.Toast
import java.util.ArrayList


class Banner_Viewpager : AppCompatActivity() {
    private var viewPager: EnchantedViewPager? = null
    private var onClick: OnClick? = null
    var bannerList: MutableList<Banner> = ArrayList<Banner>()

    var sliderAdapter: SliderAdapter? = null

    private val handler: Handler = Handler()
    private var runnableCode: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_viewpager)

        viewPager = findViewById(R.id.viewPager_home);
        val columnWidth: Int = getScreenWidth()
        viewPager?.setLayoutParams(LinearLayout.LayoutParams(columnWidth, columnWidth / 2))

        viewPager!!.useScale()
        viewPager!!.removeAlpha()
        getHome()

        onClick = OnClick { position: Int, type: String, title: String?, id: String? ->
            Log.e("type_", "click: $type")

            if (type == "home_cat") {
               Toast.makeText(this,title+"=>Home",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,title+"=>Ext",Toast.LENGTH_LONG).show()

            }
        }

    }
    private fun getHome() {
        ApiRequest.callApi(
            this,
            "http://pixeldev.in/webservices/e_commerce/GetBannerList.php",
            JSONObject(),
            object : Callback {
                override fun response(resp: String) {
                    try {
                        val jsonObject = JSONObject(resp)
                        val code = jsonObject.optString("status")
                        if (code == "Success") {
                            bannerList.clear()
                            val msgArray = jsonObject.optJSONArray("banner_info")
                            for (i in 0 until msgArray.length()) {
                                val model = Banner()
                                model.img = msgArray.getJSONObject(i).optString("image")
                                model.id = msgArray.getJSONObject(i).optString("title")
                                bannerList.add(model)
                            }

                            sliderAdapter = SliderAdapter(this@Banner_Viewpager, "external",bannerList, onClick)
                            viewPager!!.adapter = sliderAdapter
                            startAutoSlider(bannerList.size);

                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }
            })
    }
    fun getScreenWidth(): Int {
        val columnWidth: Int
        val wm = this
            .getSystemService(WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val point = Point()
        point.x = display.width
        point.y = display.height
        columnWidth = point.x
        return columnWidth
    }
    private fun startAutoSlider(count: Int) {
        runnableCode = Runnable {
            var pos = viewPager!!.currentItem
            pos = pos + 1
            if (pos >= count) pos = 0
            viewPager!!.currentItem = pos
            runnableCode?.let { handler.postDelayed(it, 3000) }
        }
        handler.postDelayed(runnableCode!!, 3000)
    }
    override fun onDestroy() {
        if (runnableCode != null) handler.removeCallbacks(runnableCode!!)
        super.onDestroy()
    }
}
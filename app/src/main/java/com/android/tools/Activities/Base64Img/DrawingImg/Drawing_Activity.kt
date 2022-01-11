package com.android.tools.Activities.Base64Img.DrawingImg

import androidx.appcompat.app.AppCompatActivity
import com.android.tools.Activities.Base64Img.DrawingImg.DrawingViewUtils
import android.widget.TextView
import android.os.Bundle
import com.android.tools.R
import androidx.core.content.ContextCompat
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.View
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
import android.widget.Toast
import com.android.tools.utils.Constant
import com.android.volley.*
import kotlin.Throws
import com.android.volley.toolbox.Volley
import java.lang.Exception
import java.util.HashMap

class Drawing_Activity : AppCompatActivity(), View.OnClickListener {
    var mDrawingViewUtils: DrawingViewUtils? = null
    private var mCurrentBackgroundColor = 0
    private var mCurrentColor = 0
    private var mCurrentStroke = 0
    var tv_date: TextView? = null
    var order_id: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing)
        mDrawingViewUtils = findViewById(R.id.main_drawing_view)
        initDrawingView()
        findViewById<View>(R.id.iv_back).setOnClickListener { v: View -> onClick(v) }
        findViewById<View>(R.id.tv_reset).setOnClickListener { v: View -> onClick(v) }
        findViewById<View>(R.id.tv_submit).setOnClickListener { v: View -> onClick(v) }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_back -> onBackPressed()
            R.id.tv_reset -> mDrawingViewUtils!!.clearCanvas()
            R.id.tv_submit -> CallApi_addSignature(
                Constant.Convert_Bitmap_to_base64(
                    mDrawingViewUtils!!.bitmap
                )
            )
            else -> return
        }
    }

    private fun initDrawingView() {
        mCurrentBackgroundColor = ContextCompat.getColor(this, android.R.color.white)
        mCurrentColor = ContextCompat.getColor(this, android.R.color.black)
        mCurrentStroke = 10
        mDrawingViewUtils!!.setBackgroundColor(mCurrentBackgroundColor)
        mDrawingViewUtils!!.setPaintColor(mCurrentColor)
        mDrawingViewUtils!!.setPaintStrokeWidth(mCurrentStroke)
    }
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null
    }

    fun CallApi_addSignature(image: String) {
        val progressDialog = ProgressDialog(this@Drawing_Activity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Loading..")
        progressDialog.show()
        if (isNetworkAvailable(this@Drawing_Activity)) {
            val stringRequest: StringRequest = object :
                StringRequest(Method.POST, Constant.ImageUpload, Response.Listener { response ->
                    try {
                        progressDialog.dismiss()
                        val jsonObject = JSONObject(response)
                        val message = jsonObject.getString("message")
                        val code = jsonObject.getString("status")
                        val image1 = ""
                        Log.d("TAG_1", "onResponse: $jsonObject")
                        if (code == "success") {
                            //  http://pixeldev.in/webservices/e_commerce/admin/user_pic/1.61468388976E+12.jpg
                            val data = jsonObject.getJSONObject("data")
                            val image = data.getString("image")
                            Log.d("image_server", "onResponse: $image")
                            Toast.makeText(this@Drawing_Activity, "" + message, Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(this@Drawing_Activity, "" + message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { error ->
                    progressDialog.dismiss()
                    error.printStackTrace()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val params = HashMap<String, String>()
                    params["image"] = image
                    params["user_id"] = "21"
                    params["eventName"] = "uploadbase64image"
                    Log.d("params", "getParams: $params")
                    return params
                }
            }
            val requestQueue = Volley.newRequestQueue(this)
            stringRequest.retryPolicy = DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            requestQueue.add(stringRequest)
        } else {
            // progressDialog.dismiss();
            Toast.makeText(this@Drawing_Activity, "Checked Internet Connection", Toast.LENGTH_LONG)
                .show()
        }
    }
}
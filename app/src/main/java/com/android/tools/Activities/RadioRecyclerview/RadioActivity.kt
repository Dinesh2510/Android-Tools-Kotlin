package com.android.tools.Activities.RadioRecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.R
import com.android.tools.api.ApiRequest
import com.android.tools.api.Callback
import com.android.tools.model.PassionsModel_Parse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class RadioActivity : AppCompatActivity() {
    var list: ArrayList<PassionsModel_Parse> = ArrayList<PassionsModel_Parse>()
    var recycler_view_interested: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_radio)
        recycler_view_interested = findViewById(R.id.recycler_view_interested)
        recycler_view_interested?.setLayoutManager(GridLayoutManager(this, 3))
        callApiShowPassions()


    }

    private fun callApiShowPassions() {
        ApiRequest.callApi(
            this@RadioActivity,
            "http://pixeldev.in/webservices/movie_app/GetAllHomeList.php",
            JSONObject(),
            object : Callback {
                override fun response(resp: String?) {
                    parseUserInfo(resp)
                }
            })
    }

    fun parseUserInfo(data: String?) {
        list = ArrayList<PassionsModel_Parse>()
        try {
            val jsonObject = JSONObject(data)
            val code = jsonObject.optString("code")
            if (code == "200") {
//                prefs.edit().putString(Variables.uPassions, jsonObject.toString()).apply();
                list.clear()
                val msgArray = jsonObject.optJSONArray("genre").toString()
                val listType_enquiry_status_data =
                    object : TypeToken<ArrayList<PassionsModel_Parse?>?>() {}.type
                val statusModels: ArrayList<PassionsModel_Parse> =
                    Gson().fromJson(
                        msgArray,
                        listType_enquiry_status_data
                    )
                if (statusModels.size > 0) {
                    for (statusModel in statusModels) {
                        list.add(statusModel)
                    }
                    val adapterFormList = AdapterInterested(list, this@RadioActivity)
                    recycler_view_interested!!.adapter = adapterFormList
                    adapterFormList.notifyDataSetChanged()
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun onClickCalled_status(designModel: PassionsModel_Parse, position: Int, row_index: Int) {
        var statusName: String = designModel.genreName
        //Below code select and deselect the radio button
        statusName = if (row_index == -1) {
            ""
        } else {
            designModel.genreName
        }
        Toast.makeText(applicationContext, statusName, Toast.LENGTH_SHORT).show()
        Log.e("sd", "onClickCalled_status: $statusName")
        Log.e("sdf", "onClickCalled_status_row_index: $row_index")
    }
}
package com.android.tools.Activities.FlexBox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.R
import com.android.tools.adaptor.DivLikeAdapter
import com.android.tools.api.ApiRequest
import com.android.tools.model.PassionsModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class FlexBoxActivity : AppCompatActivity() {
    var divLikeContent: ArrayList<PassionsModel?> = ArrayList()
    var adapter: DivLikeAdapter? = null
    var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flex_box)

        recyclerView = findViewById(R.id.recycler_view)
        val layoutManager = FlexboxLayoutManager(this@FlexBoxActivity)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.FLEX_START
        recyclerView?.setLayoutManager(layoutManager)
        callApiShowPassions()

    }

    private fun callApiShowPassions() {
        ApiRequest.callApi(
            this@FlexBoxActivity,
            "http://pixeldev.in/webservices/movie_app/GetAllHomeList.php",
            JSONObject()
        ) { resp -> parseUserInfo(resp) }
    }

    fun parseUserInfo(data: String?) {
        divLikeContent = ArrayList<PassionsModel?>()
        try {
            val jsonObject = JSONObject(data)
            val code = jsonObject.optString("code")
            if (code == "200") {
//                prefs.edit().putString(Variables.uPassions, jsonObject.toString()).apply();
                divLikeContent.clear()
                val msgArray = jsonObject.optJSONArray("genre")
                for (i in 0 until msgArray.length()) {
                    val model = PassionsModel()
                    model.genre_id = (msgArray.getJSONObject(i).optString("genre_id"))
                    model.genre_name = (msgArray.getJSONObject(i).optString("genre_name"))
                    divLikeContent.add(model)
                }
                adapter = DivLikeAdapter(this@FlexBoxActivity, divLikeContent)
                recyclerView!!.adapter = adapter
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}
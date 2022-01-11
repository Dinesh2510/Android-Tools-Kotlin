package com.android.tools.Activities.MultipleSeclection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tools.R
import com.android.tools.adaptor.MultiAdapter
import com.android.tools.api.ApiRequest
import com.android.tools.api.Callback
import com.android.tools.model.PassionsModel
import org.json.JSONException
import org.json.JSONObject
import java.lang.StringBuilder
import java.util.ArrayList

class MultipleSelectionActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var employeeArrayList: ArrayList<PassionsModel> = ArrayList<PassionsModel>()
    private var adapter: MultiAdapter? = null
    private var btnGetSelected: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_selection)
        btnGetSelected = findViewById<View>(R.id.btnGetSelected) as AppCompatButton
        recyclerView = findViewById<View>(R.id.recyclerView) as RecyclerView

        recyclerView!!.layoutManager = LinearLayoutManager(this)
        recyclerView?.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter = MultiAdapter(this@MultipleSelectionActivity, employeeArrayList)
        recyclerView?.setAdapter(adapter)

        callApiShowPassions()

        btnGetSelected!!.setOnClickListener {
            if (adapter!!.getselected.size > 0) {
                val stringBuilder = StringBuilder()
                for (i in 0 until adapter!!.getselected.size) {
                    stringBuilder.append(adapter!!.getselected.get(i).genre_name)
                    stringBuilder.append("\n")
                }
                showToast(stringBuilder.toString().trim { it <= ' ' })
            } else {
                showToast("No Selection")
            }
        }
    }


    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun callApiShowPassions() {
        ApiRequest.callApi(
            this@MultipleSelectionActivity,
            "http://pixeldev.in/webservices/movie_app/GetAllHomeList.php",
            JSONObject(),
            object : Callback {
                override fun response(resp: String?) {
                    parseUserInfo(resp)
                }
            })
    }

    fun parseUserInfo(data: String?) {
        employeeArrayList = ArrayList()
        try {
            val jsonObject = JSONObject(data)
            val code = jsonObject.optString("code")
            if (code == "200") {
//                prefs.edit().putString(Variables.uPassions, jsonObject.toString()).apply();
                employeeArrayList.clear()
                val msgArray = jsonObject.optJSONArray("genre")
                for (i in 0 until msgArray.length()) {
                    val model = PassionsModel()
                    model.genre_id =(msgArray.getJSONObject(i).optString("genre_id"))
                    model.genre_name =(msgArray.getJSONObject(i).optString("genre_name"))
                    employeeArrayList.add(model)
                }
                adapter!!.setEmployees(employeeArrayList)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}
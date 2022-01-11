package com.android.tools.Activities.Chips

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.tools.R
import com.android.tools.api.ApiRequest
import com.android.tools.api.Callback
import com.android.tools.model.PassionsModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList

class ChipsList_A : AppCompatActivity() {
    var list: MutableList<PassionsModel> = ArrayList<PassionsModel>()
    val tempList: MutableList<String> = ArrayList()

    var chipGroup: ChipGroup? = null

    var continueButton: RelativeLayout? = null
    var continueTv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chips_list)


        continueButton = findViewById(R.id.continueButton)
        continueTv = findViewById(R.id.continue_tv)
        continueButton?.setOnClickListener(View.OnClickListener {
            if (tempList.size > 2) {
                // Toast.makeText(getApplicationContext(), "cll", Toast.LENGTH_SHORT).show();
            }
        })

        chipGroup = findViewById(R.id.chipGroup)

        callApiShowPassions()
    }

    private fun callApiShowPassions() {
        ApiRequest.callApi(
            this@ChipsList_A,
            "http://pixeldev.in/webservices/movie_app/GetAllHomeList.php",
            JSONObject(),
            object : Callback {
                override fun response(resp: String?) {
                    parseUserInfo(resp)
                }
            })
    }

    fun parseUserInfo(data: String?) {
        try {
            val jsonObject = JSONObject(data)
            val code = jsonObject.optString("code")
            if (code == "200") {
//                prefs.edit().putString(Variables.uPassions, jsonObject.toString()).apply();
                list.clear()
                val msgArray = jsonObject.optJSONArray("genre")
                for (i in 0 until msgArray.length()) {
                    val model = PassionsModel()
                    model.genre_id=(msgArray.getJSONObject(i).optString("genre_id"))
                    model.genre_name=(msgArray.getJSONObject(i).optString("genre_name"))
                    list.add(model)
                    val chip1 = LayoutInflater.from(this@ChipsList_A)
                        .inflate(R.layout.item_passion, null) as Chip
                    chip1.text = msgArray.getJSONObject(i).optString("genre_name")
                    chip1.setOnClickListener {
                        if (tempList.size == 0) {
                            tempList.add(chip1.text.toString())
                            chip1.setTextColor(
                                ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        this@ChipsList_A,
                                        R.color.pink_color
                                    )
                                )
                            )
                            chip1.chipStrokeColor =
                                ColorStateList.valueOf(
                                    ContextCompat.getColor(
                                        this@ChipsList_A,
                                        R.color.pink_color
                                    )
                                )
                            continueTv?.setText(
                                this@ChipsList_A.getString(R.string.continue_capital)
                                    .toString() + " (" + tempList.size + "/5)"
                            )
                        } else if (tempList.size > 0) {
                            for (i in tempList.indices) {
                                if (tempList[i] == chip1.text.toString()) {
                                    chip1.setTextColor(
                                        ColorStateList.valueOf(
                                            ContextCompat.getColor(
                                                this@ChipsList_A,
                                                R.color.newGrayTextColor
                                            )
                                        )
                                    )
                                    chip1.chipStrokeColor = ColorStateList.valueOf(
                                        ContextCompat.getColor(
                                            this@ChipsList_A,
                                            R.color.newGrayTextColor
                                        )
                                    )
                                    tempList.removeAt(i)
                                    continueTv?.setText(
                                        this@ChipsList_A.getString(R.string.continue_capital)
                                            .toString() + " (" + tempList.size + "/5)"
                                    )
                                    break
                                } else if (i + 1 == tempList.size && tempList[i] != chip1.text.toString()) {
                                    if (tempList.size < 5) {
                                        tempList.add(chip1.text.toString())
                                        chip1.setTextColor(
                                            ColorStateList.valueOf(
                                                ContextCompat.getColor(
                                                    this@ChipsList_A,
                                                    R.color.pink_color
                                                )
                                            )
                                        )
                                        chip1.chipStrokeColor = ColorStateList.valueOf(
                                            ContextCompat.getColor(
                                                this@ChipsList_A,
                                                R.color.pink_color
                                            )
                                        )
                                        continueTv?.setText(
                                            this@ChipsList_A.getString(R.string.continue_capital)
                                                .toString() + " (" + tempList.size + "/5)"
                                        )
                                        break
                                    }
                                }
                            }
                        }
                        if (tempList.size > 2) {
                            continueButton!!.background =
                                ContextCompat.getDrawable(
                                    this@ChipsList_A,
                                    R.drawable.ic_pink_background
                                )
                            continueTv!!.setTextColor(
                                ContextCompat.getColor(
                                    this@ChipsList_A,
                                    R.color.white
                                )
                            )
                        } else {
                            continueButton!!.background =
                                ContextCompat.getDrawable(
                                    this@ChipsList_A,
                                    R.drawable.ic_google_background
                                )
                            continueTv!!.setTextColor(
                                ContextCompat.getColor(
                                    this@ChipsList_A,
                                    R.color.gray0
                                )
                            )
                        }
                    }
                    chipGroup!!.addView(chip1)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}
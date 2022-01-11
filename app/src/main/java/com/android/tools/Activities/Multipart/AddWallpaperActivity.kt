package com.android.tools.Activities.Multipart

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.android.tools.R
import com.android.tools.model.CategoryModel
import com.android.tools.utils.Common
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_add_wallpaper.*
import kotlinx.android.synthetic.main.dlg_externalstorage.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

class AddWallpaperActivity : AppCompatActivity() {
    private val SELECT_FILE = 201
    private val REQUEST_CAMERA = 202
    private var mSelectedFileImg: File? = null
    var getId: Int = 0
    var getHeigt: Int = 0
    var getColor = ""
    var categoryList: ArrayList<CategoryModel>? = null
    var categorySpList: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_wallpaper)

        categoryList = ArrayList()
        categorySpList = ArrayList()
        if (Common.isCheckNetwork(this@AddWallpaperActivity)) {
            callApiCategory()
        } else {
            Common.alertErrorOrValidationDialog(
                this@AddWallpaperActivity,
                resources.getString(R.string.no_internet)
            )
        }

        rlSelectCategory.setOnClickListener {
            spCategory.performClick()
            spCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position != 0) {
                        Common.getLog("getPostion", position.toString())
                        getId = categoryList!!.get(position).getId()!!.toInt()
                        tvSelectCategory.text = categorySpList!!.get(position)
                        tvSelectCategory.setTextColor(resources.getColor(R.color.black))
                    }

                }
            }
        }


    }


    fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.ivAddWallpaper -> {
                // getExternalStoragePermission()

                imageSelectDialog(this@AddWallpaperActivity)

            }
            R.id.tvUpload -> {
                if (Common.isCheckNetwork(this)) {
                    mCallApiAddWallpaper()
                } else {
                    Common.alertErrorOrValidationDialog(
                        this,
                        resources.getString(R.string.no_internet)
                    )
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }


    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data)
            } else if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data!!)
            }
        }
    }


    @SuppressLint("InlinedApi")
    fun imageSelectDialog(act: Activity) {
        var dialog: Dialog? = null
        try {
            if (dialog != null) {
                dialog.dismiss()
                dialog = null
            }
            dialog = Dialog(act, R.style.AppCompatAlertDialogStyleBig)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            );
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(true)
            val m_inflater = LayoutInflater.from(act)
            val m_view = m_inflater.inflate(R.layout.dlg_externalstorage, null, false)

            val finalDialog: Dialog = dialog
            m_view.tvSetImageCamera.setOnClickListener {
                finalDialog.dismiss()
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(
                    intent,
                    REQUEST_CAMERA
                )
            }
            m_view.tvSetImageGallery.setOnClickListener {
                finalDialog.dismiss()
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_PICK
                //   intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE)
            }
            dialog.setContentView(m_view)
            if (!act.isFinishing) dialog.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun onSelectFromGalleryResult(data: Intent?) {
        var bm: Bitmap? = null
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(
                    this.getContentResolver(),
                    data.data
                )
                val bytes = ByteArrayOutputStream()
                bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
                getColor = getDominantColor(bm)
                mSelectedFileImg = File(
                    Environment.getExternalStorageDirectory(),
                    System.currentTimeMillis().toString() + ".jpeg"
                )
                Common.getLog("ImgPath", "ImagePath>>$mSelectedFileImg")
                val fo: FileOutputStream
                try {
                    mSelectedFileImg!!.createNewFile()
                    fo = FileOutputStream(mSelectedFileImg)
                    fo.write(bytes.toByteArray())
                    fo.close()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        getHeigt = getHeightIMGSize(Uri.fromFile(mSelectedFileImg))
        Glide.with(this)
            .load(Uri.parse("file://" + mSelectedFileImg!!.getPath()))
            .into(ivWallpaper)


    }

    private fun onCaptureImageResult(data: Intent) {
        val thumbnail = data.extras!!["data"] as Bitmap?
        val bytes = ByteArrayOutputStream()
        thumbnail!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        getColor = getDominantColor(thumbnail)
        mSelectedFileImg = File(
            Environment.getExternalStorageDirectory(),
            System.currentTimeMillis().toString() + ".jpeg"
        )
        val fo: FileOutputStream
        try {
            mSelectedFileImg!!.createNewFile()
            fo = FileOutputStream(mSelectedFileImg)
            fo.write(bytes.toByteArray())
            fo.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        getHeigt = getHeightIMGSize(Uri.fromFile(mSelectedFileImg))
        Glide.with(this)
            .load(Uri.parse("file://" + mSelectedFileImg!!.getPath()))
            .into(ivWallpaper)
    }

    private fun getHeightIMGSize(uri: Uri): Int {
        val fd: ParcelFileDescriptor? = contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor? = fd!!.fileDescriptor
        val bitmap: Bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        val height: Int = bitmap.height
        val width: Int = bitmap.width
        var setHeight: Int = 0
        if (width > height) {
            setHeight = 140
        } else {
            setHeight = 310
        }
        fd.close();
        return setHeight
    }

    fun getDominantColor(bitmap: Bitmap?): String {
        val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return String.format("#%06X", 0xFFFFFF and color)
    }

    // https://pixeldev.in/webservices/wallpaper-admin/api/uploadwallpaper.php
    private fun mCallApiAddWallpaper() {
        Common.showLoadingProgress(this@AddWallpaperActivity)
        var call: Call<Rest_Response<RestResponseModel>>? = null
        if (mSelectedFileImg != null) {
            call = ApiClient_Multipart.getClient.getUploadwallpaper(
                Common.setRequestBody("1"),
                Common.setRequestBody(getId.toString()),
                Common.setRequestBody(getColor + "ff"),
                Common.setRequestBody(getHeigt.toString()),
                Common.setRequestBody(edWallPaperName.text.toString()),
                Common.setImageUpload("wallpaper_image", mSelectedFileImg!!)
            )
        } else {
            call = ApiClient_Multipart.getClient.getUploadwallpaper(
                Common.setRequestBody("1"),
                Common.setRequestBody(getId.toString()),
                Common.setRequestBody(getColor + "ff"),
                Common.setRequestBody(getHeigt.toString()),
                Common.setRequestBody(edWallPaperName.text.toString()),
                null
            )
        }
        call.enqueue(object : Callback<Rest_Response<RestResponseModel>> {
            override fun onResponse(
                call: Call<Rest_Response<RestResponseModel>>,
                response: Response<Rest_Response<RestResponseModel>>
            ) {
                val loginResponce: Rest_Response<RestResponseModel> = response.body()!!
                if (loginResponce.getResponseCode().equals("1")) {
                    Common.dismissLoadingProgress()
                    Common.alertErrorOrValidationDialog(
                        this@AddWallpaperActivity,
                        loginResponce.getResponseMessage()
                    )

                } else if (loginResponce.getResponseCode().equals("0")) {
                    Common.dismissLoadingProgress()
                    Common.alertErrorOrValidationDialog(
                        this@AddWallpaperActivity,
                        loginResponce.getResponseMessage()
                    )
                }
            }

            override fun onFailure(call: Call<Rest_Response<RestResponseModel>>, t: Throwable) {
                Common.dismissLoadingProgress()
                Common.alertErrorOrValidationDialog(
                    this@AddWallpaperActivity,
                    resources.getString(R.string.error_msg)
                )
            }
        })

    }
    private fun callApiCategory() {
        Common.showLoadingProgress(this@AddWallpaperActivity)
        val call = ApiClient_Multipart.getClient.getCategory()
        call.enqueue(object : Callback<ListResponse_multipart<CategoryModel>> {
            override fun onResponse(call: Call<ListResponse_multipart<CategoryModel>>, response: Response<ListResponse_multipart<CategoryModel>>) {
                if (response.code() == 200) {
                    val restResponce: ListResponse_multipart<CategoryModel> = response.body()!!
                    if (restResponce.getResponseCode().equals("1")) {
                        Common.dismissLoadingProgress()
                        val categoryModel = CategoryModel()
                        categoryModel.setId("0")
                        categoryModel.setCategory_name("Select category")
                        categoryModel.setCategory_image("img")
                        categoryList!!.add(0,categoryModel)
                        categorySpList!!.add(0,"Select category")
                        for (i in 0 until restResponce.getResponseData().size){
                            val categoryModelin=restResponce.getResponseData().get(i)
                            categoryList!!.add(categoryModelin)
                            categorySpList!!.add(categoryModelin.getCategory_name()!!)
                        }
                        val adapter = ArrayAdapter(
                            this@AddWallpaperActivity,
                            R.layout.textview_spinner,
                            categorySpList!!
                        ).also {
                            it.setDropDownViewResource(R.layout.textview_spinner)
                        }
                        spCategory.adapter = adapter
                    } else if (restResponce.getResponseCode().equals("0")) {
                        Common.dismissLoadingProgress()
                        Common.alertErrorOrValidationDialog(
                            this@AddWallpaperActivity,
                            restResponce.getResponseMessage()
                        )
                    }
                }
            }

            override fun onFailure(call: Call<ListResponse_multipart<CategoryModel>>, t: Throwable) {
                Common.dismissLoadingProgress()
                Common.alertErrorOrValidationDialog(
                    this@AddWallpaperActivity,
                    resources.getString(R.string.error_msg)
                )
            }
        })
    }


}
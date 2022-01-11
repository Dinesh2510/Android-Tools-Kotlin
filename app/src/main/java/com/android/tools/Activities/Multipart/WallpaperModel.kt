package com.android.tools.Activities.Multipart

import android.os.Parcel
import android.os.Parcelable

class WallpaperModel() : Parcelable {

    private var wallpaper_likes: String? = null

    private var category_name: String? = null

    private var wallpaper_color: String? = null

    private var user_image: String? = null

    private var user_id: String? = null

    private var user_name: String? = null

    private var wallpaper_image: String? = null

    private var id: String? = null

    private var wallpaper_height: String? = null

    private var isFavourite: String? = null

    private var wallpaper_views: String? = null

    constructor(parcel: Parcel) : this() {
        wallpaper_likes = parcel.readString()
        category_name = parcel.readString()
        wallpaper_color = parcel.readString()
        user_image = parcel.readString()
        user_id = parcel.readString()
        user_name = parcel.readString()
        wallpaper_image = parcel.readString()
        id = parcel.readString()
        wallpaper_height = parcel.readString()
        isFavourite = parcel.readString()
        wallpaper_views = parcel.readString()
    }

    fun getWallpaper_likes(): String? {
        return wallpaper_likes
    }

    fun setWallpaper_likes(wallpaper_likes: String?) {
        this.wallpaper_likes = wallpaper_likes
    }

    fun getCategory_name(): String? {
        return category_name
    }

    fun setCategory_name(category_name: String?) {
        this.category_name = category_name
    }

    fun getWallpaper_color(): String? {
        return wallpaper_color
    }

    fun setWallpaper_color(wallpaper_color: String?) {
        this.wallpaper_color = wallpaper_color
    }

    fun getUser_image(): String? {
        return user_image
    }

    fun setUser_image(user_image: String?) {
        this.user_image = user_image
    }

    fun getUser_id(): String? {
        return user_id
    }

    fun setUser_id(user_id: String?) {
        this.user_id = user_id
    }

    fun getUser_name(): String? {
        return user_name
    }

    fun setUser_name(user_name: String?) {
        this.user_name = user_name
    }

    fun getWallpaper_image(): String? {
        return wallpaper_image
    }

    fun setWallpaper_image(wallpaper_image: String?) {
        this.wallpaper_image = wallpaper_image
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getWallpaper_height(): String? {
        return wallpaper_height
    }

    fun setWallpaper_height(wallpaper_height: String?) {
        this.wallpaper_height = wallpaper_height
    }

    fun getIsFavourite(): String? {
        return isFavourite
    }

    fun setIsFavourite(isFavourite: String?) {
        this.isFavourite = isFavourite
    }

    fun getWallpaper_views(): String? {
        return wallpaper_views
    }

    fun setWallpaper_views(wallpaper_views: String?) {
        this.wallpaper_views = wallpaper_views
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(wallpaper_likes)
        parcel.writeString(category_name)
        parcel.writeString(wallpaper_color)
        parcel.writeString(user_image)
        parcel.writeString(user_id)
        parcel.writeString(user_name)
        parcel.writeString(wallpaper_image)
        parcel.writeString(id)
        parcel.writeString(wallpaper_height)
        parcel.writeString(isFavourite)
        parcel.writeString(wallpaper_views)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WallpaperModel> {
        override fun createFromParcel(parcel: Parcel): WallpaperModel {
            return WallpaperModel(parcel)
        }

        override fun newArray(size: Int): Array<WallpaperModel?> {
            return arrayOfNulls(size)
        }
    }
}
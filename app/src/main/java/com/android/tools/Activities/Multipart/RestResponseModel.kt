package com.android.tools.Activities.Multipart

import com.android.tools.Activities.Multipart.WallpaperModel

class RestResponseModel {

    private var notification: String? = null

    private var profile_image: String? = null

    private var id: String? = null

    private var email: String? = null

    private var username: String? = null

    private var Wallpapers: ArrayList<WallpaperModel>?=null

    fun getNotification(): String? {
        return notification
    }

    fun setNotification(notification: String?) {
        this.notification = notification
    }

    fun getProfile_image(): String? {
        return profile_image
    }

    fun setProfile_image(profile_image: String?) {
        this.profile_image = profile_image
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    fun getWallpapers(): ArrayList<WallpaperModel>? {
        return Wallpapers
    }

    fun setWallpapers(Wallpapers: ArrayList<WallpaperModel>?) {
        this.Wallpapers = Wallpapers
    }
}
package com.android.tools.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import android.os.Parcelable.Creator
import com.android.tools.model.PassionsModel_Parse


class PassionsModel_Parse protected constructor(`in`: Parcel) : Parcelable {
    @SerializedName("genre_id")
    @Expose
    var genreId: String = `in`.readString().toString()

    @SerializedName("genre_name")
    @Expose
    var genreName: String = `in`.readString().toString()

    @SerializedName("genre_img")
    @Expose
    var genreImg: String = `in`.readString().toString()

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(genreId)
        dest.writeString(genreName)
        dest.writeString(genreImg)
    }

    override fun toString(): String {
        return "PassionsModel_Parse(genreId=$genreId, genreName=$genreName, genreImg=$genreImg)"
    }


    companion object CREATOR : Creator<PassionsModel_Parse> {
        override fun createFromParcel(parcel: Parcel): PassionsModel_Parse {
            return PassionsModel_Parse(parcel)
        }

        override fun newArray(size: Int): Array<PassionsModel_Parse?> {
            return arrayOfNulls(size)
        }
    }

}

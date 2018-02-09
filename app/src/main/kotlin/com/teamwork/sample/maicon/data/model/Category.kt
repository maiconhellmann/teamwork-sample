package com.teamwork.sample.maicon.data.model

import android.os.Parcel
import android.os.Parcelable

class Category : Parcelable {
    var name: String? = null

    var id: String? = null

    constructor(source: Parcel) : this(
    )

    constructor()

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Category> = object : Parcelable.Creator<Category> {
            override fun createFromParcel(source: Parcel): Category = Category(source)
            override fun newArray(size: Int): Array<Category?> = arrayOfNulls(size)
        }
    }
}
package com.teamwork.sample.maicon.data.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created on 07/02/2018.
 */
class Tag(val name: String, val id: String, val color: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(id)
        writeString(color)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Tag> = object : Parcelable.Creator<Tag> {
            override fun createFromParcel(source: Parcel): Tag = Tag(source)
            override fun newArray(size: Int): Array<Tag?> = arrayOfNulls(size)
        }
    }
}
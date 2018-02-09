package com.teamwork.sample.maicon.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

class Project(
        var company: Company? = null,

        var starred: Boolean? = null,

        var name: String? = null,

        @SerializedName("show-announcement")
        var showAnnouncement: Boolean? = null,

        var announcement: String? = null,

        var description: String? = null,

        var status: String? = null,

        var isProjectAdmin: Boolean? = null,

        @SerializedName("created-on")
        var createdOn: String? = null,

        var category: Category? = null,

        @SerializedName("start-page")
        var startPage: String? = null,

        var startDate: Date? = null,

        var logo: String? = null,

        var notifyeveryone: Boolean? = null,

        var id: String? = null,

        @SerializedName("last-changed-on")
        var lastChangedOn: String? = null,

        var endDate: Date? = null,

        @SerializedName("harvest-timers-enabled")
        var harvestTimersEnabled: String? = null,

        var tags: List<Tag>? = null
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readParcelable<Company>(Company::class.java.classLoader),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readString(),
            source.readParcelable<Category>(Category::class.java.classLoader),
            source.readString(),
            source.readSerializable() as Date?,
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?,
            source.readString(),
            source.readString(),
            source.readSerializable() as Date?,
            source.readString(),
            source.createTypedArrayList(Tag.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeParcelable(company, 0)
        writeValue(starred)
        writeString(name)
        writeValue(showAnnouncement)
        writeString(announcement)
        writeString(description)
        writeString(status)
        writeValue(isProjectAdmin)
        writeString(createdOn)
        writeParcelable(category, 0)
        writeString(startPage)
        writeSerializable(startDate)
        writeString(logo)
        writeValue(notifyeveryone)
        writeString(id)
        writeString(lastChangedOn)
        writeSerializable(endDate)
        writeString(harvestTimersEnabled)
        writeTypedList(tags)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Project> = object : Parcelable.Creator<Project> {
            override fun createFromParcel(source: Parcel): Project = Project(source)
            override fun newArray(size: Int): Array<Project?> = arrayOfNulls(size)
        }
    }
}
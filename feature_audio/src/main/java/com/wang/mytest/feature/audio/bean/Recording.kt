package com.wang.mytest.feature.audio.bean

import android.os.Parcel
import android.os.Parcelable
import com.wang.mytest.feature.audio.calcDuration

data class Recording(val filename: String, val filepath: String) : Parcelable {
    val duration: Long = calcDuration()

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(filename)
        writeString(filepath)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Recording> = object : Parcelable.Creator<Recording> {
            override fun createFromParcel(source: Parcel): Recording = Recording(source)
            override fun newArray(size: Int): Array<Recording?> = arrayOfNulls(size)
        }
    }
}
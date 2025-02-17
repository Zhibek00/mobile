package com.example.restaurantapp.models

import android.os.Parcel
import android.os.Parcelable

data class DataXXXXX(
    val __v: Int,
    val _id: String?,
    val tableCount: Int,
    val tables: List<Table>,
    val tid: String?,
    val type: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.createTypedArrayList(Table)!!,
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(__v)
        parcel.writeString(_id)
        parcel.writeInt(tableCount)
        parcel.writeString(tid)
        parcel.writeInt(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataXXXXX> {
        override fun createFromParcel(parcel: Parcel): DataXXXXX {
            return DataXXXXX(parcel)
        }

        override fun newArray(size: Int): Array<DataXXXXX?> {
            return arrayOfNulls(size)
        }
    }
}
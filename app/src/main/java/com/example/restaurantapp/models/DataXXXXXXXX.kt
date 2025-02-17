package com.example.restaurantapp.models

import android.os.Parcel
import android.os.Parcelable

data class DataXXXXXXXX(
    val __v: Int,
    val _id: String?,
    val contacts: String?,
    val message: String?,
    val name: String?,
    val occasion: String?,
    val resStatus: Int,
    val reservationCode: String?,
    val reservationEndTime: Int,
    val reservationId: String?,
    val reservationStartTime: Int,
    val rid: String?,
    val tableId: String?,
    val tableType: Int,
    val tid: String?,
    val uid: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(__v)
        parcel.writeString(_id)
        parcel.writeString(contacts)
        parcel.writeString(message)
        parcel.writeString(name)
        parcel.writeString(occasion)
        parcel.writeInt(resStatus)
        parcel.writeString(reservationCode)
        parcel.writeInt(reservationEndTime)
        parcel.writeString(reservationId)
        parcel.writeInt(reservationStartTime)
        parcel.writeString(rid)
        parcel.writeString(tableId)
        parcel.writeInt(tableType)
        parcel.writeString(tid)
        parcel.writeString(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataXXXXXXXX> {
        override fun createFromParcel(parcel: Parcel): DataXXXXXXXX {
            return DataXXXXXXXX(parcel)
        }

        override fun newArray(size: Int): Array<DataXXXXXXXX?> {
            return arrayOfNulls(size)
        }
    }
}
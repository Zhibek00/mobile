package com.example.restaurantapp.models

import android.os.Parcel
import android.os.Parcelable

data class Row(
    val __v: Int,
    val _id: String?,
    val address: String?,
    val countTable: Int,
    val description: String?,
    val category: String?,
    var isFavorite: Int,
    val name: String?,
    val parking: Int,
    val path: String?,
    val prayingRoom: Int,
    val rid: String?,
    val phone: String?,
    val avgCheque: Int,
    val workendtime: Int,
    val workstarttime: Int,
    val rating:  Int

    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
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
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()

    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(__v)
        parcel.writeString(_id)
        parcel.writeString(address)
        parcel.writeInt(countTable)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeInt(isFavorite)
        parcel.writeString(name)
        parcel.writeInt(parking)
        parcel.writeString(path)
        parcel.writeInt(prayingRoom)
        parcel.writeString(rid)
        parcel.writeString(phone)
        parcel.writeInt(avgCheque)
        parcel.writeInt(workendtime)
        parcel.writeInt(workstarttime)
        parcel.writeInt(rating)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Row> {
        override fun createFromParcel(parcel: Parcel): Row {
            return Row(parcel)
        }

        override fun newArray(size: Int): Array<Row?> {
            return arrayOfNulls(size)
        }
    }

}
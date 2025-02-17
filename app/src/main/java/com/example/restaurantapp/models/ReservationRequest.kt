package com.example.restaurantapp.models

import android.os.Parcel
import android.os.Parcelable

data class ReservationRequest(
    var contacts: String?,
    var message: String?,
    var name: String?,
    var occasion: String?,
    var reservationStartTime: Int,
    var rid: String?,
    var tableType: Int,
    var tid: String?,
    var uid: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(contacts)
        parcel.writeString(message)
        parcel.writeString(name)
        parcel.writeString(occasion)
        parcel.writeInt(reservationStartTime)
        parcel.writeString(rid)
        parcel.writeInt(tableType)
        parcel.writeString(tid)
        parcel.writeString(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReservationRequest> {
        override fun createFromParcel(parcel: Parcel): ReservationRequest {
            return ReservationRequest(parcel)
        }

        override fun newArray(size: Int): Array<ReservationRequest?> {
            return arrayOfNulls(size)
        }
    }

}

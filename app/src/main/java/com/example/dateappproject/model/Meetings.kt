package com.example.dateappproject.model

import android.location.Location
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meetings(

    var id : String,
    val name :String?,
    val email : String?,
    val cellphone : String?,
    val location : Location ?,
    val age : Int?,
    val descrip : String?


):Parcelable  {

    constructor():
            this("" , "" , "" , "" , null, 0  , "")
}
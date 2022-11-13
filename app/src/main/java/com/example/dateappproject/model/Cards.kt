package com.example.dateappproject.model

import android.location.Location
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cards(

    var id : String,
    val userId :String?,
    val name : String?,
    val distance : Location ?,
    val age : Int?,
    val biography : String?,
    val gender : String?,
    val state: State?,
    val interests : String?


):Parcelable  {

    constructor():
            this(""  , "" , "" , null, 0  , "" , "" ,null,"")
}
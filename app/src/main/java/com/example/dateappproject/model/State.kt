package com.example.dateappproject.model

import android.location.Location
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class State(

    var id : String,
    val state :String?,
    val visibility : Boolean?,

):Parcelable  {

    constructor():
            this("" , "" , false)
}
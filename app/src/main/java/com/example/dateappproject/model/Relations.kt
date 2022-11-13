package com.example.dateappproject.model

import android.location.Location
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Relations(

    var id : String,
    val user1 :Users?,
    val user2: Users?,
    val state: State?,
    val date : String ?

):Parcelable  {

    constructor():
            this("" , null , null , null , "")
}
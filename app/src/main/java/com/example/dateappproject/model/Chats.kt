package com.example.dateappproject.model

import android.location.Location
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


//Cada usuario tendra chats --> meterlo en una coleccion de user como coleccion chats

@Parcelize
data class Chats(

    var id : String,
    val senderId :String,
    val messages : Messages?



):Parcelable  {

    constructor():
            this("" , "" , null )
}
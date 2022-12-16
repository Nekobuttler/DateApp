package com.example.dateappproject.model

import android.location.Location
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

//Cada mensaje tendra una coleccion chat adjuntado a esta por lo
//que la jerarquia seria de user->chats->mensajes

@Parcelize
data class Messages(

    var id : String,
    val text :String?,
    val type : String?,
    val imageUrl : String?,
    val time : Long?,
    var senderId : String?


):Parcelable  {

    constructor():
            this("" , "" , "" , ""  ,0,"")
}
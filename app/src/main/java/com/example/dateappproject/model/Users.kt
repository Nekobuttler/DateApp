package com.example.dateappproject.model

import android.location.Location
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Users(

    var id : String?,
    val name :String?,
    val email : String?,
    val cellphone : String?,
    val location : Location ?,
    val age : Int?,
    val biography : String?,
    var gender : String?,
    val interests : String?,
    var birthdate : String?,
    val profileMainPicture : String?,




    ):Parcelable  {


    constructor():
            this("" , "" , "" , "" , null, 0  , "" , "" ,"","","")


}


package com.example.dateappproject.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dateappproject.model.Users
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.auth.User

import com.google.firebase.ktx.Firebase


class UsersDao {

    private var UserCode: String

    private var firestore: FirebaseFirestore


    init {
        val user = Firebase.auth.currentUser?.email
        //val user = Firebase.auth.currentUser?.uid
        UserCode = "$user"
        firestore = FirebaseFirestore.getInstance()

        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()


    }


    //Una funcion debe siempre guardar y mantener inodoros los parametros de entrada y de salida
    //Por lo tanto siempre es bueno crear una variable intermedia que funcione como temporal que obtenga sus valores


    fun getAllData(): MutableLiveData<List<Users>> {
        val UsersList =
            MutableLiveData<List<Users>>()     //    : -> Tipo de dato      = -> definicion de dato
        firestore.collection("DateApp")
            .document(UserCode)
            .collection("MyLikes")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = ArrayList<Users>()
                    val users =
                        snapshot.documents  //Obtine los datos del snapshot en otras palabras json de usuarios

                    users.forEach {
                        val User =
                            it.toObject(Users::class.java) // --> aqui define la clase y transforma los objetos desconocidos en la clase
                        if (User != null) {
                            list.add(User)   //Los agrega a la lista
                        }
                    }
                    UsersList.value =
                        list  //Traslada los datos obtenidos de la lista temp a la lista de respuesta


                }

            }
        return UsersList;
    }

    fun SaveUser(User: Users) { //Primero objeto luego clase que pertenece

        val document: DocumentReference

        if (User.id!!.isEmpty()) { //Nuevo regitro

            document = firestore
                .collection("DateApp")
                .document(UserCode)
                .collection("MyLikes")
                .document()

            User.id = document.id
        } else {
            document = firestore
                .collection("DateApp")
                .document(UserCode)
                .collection("MyLikes")
                .document(UserCode)
        }

        val set = document.set(User)
        set.addOnSuccessListener {
            Log.d(
                "Add User",
                "User Added"
            )
        }
            .addOnCanceledListener {
                Log.e(
                    "Add User",
                    "User Not Added"
                )
            }

    }

    fun DeleteUser(User: Users) {
        if (User.id!!.isNotEmpty()) { //Nuevo regitro

            firestore
                .collection("DateApp")
                .document(UserCode)
                .collection("MyLikes")
                .document(User.id!!)
                .delete()
                .addOnSuccessListener {
                    Log.d(
                        "Delete User",
                        "Deleted User"
                    )
                }
                .addOnCanceledListener {
                    Log.e(
                        "Delete User",
                        "User Not Deleted "
                    )
                }


        }


    }
}



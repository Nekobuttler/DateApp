package com.example.dateappproject.data

import androidx.lifecycle.MutableLiveData
import com.example.dateappproject.Adapters.MessageAdapter
import com.example.dateappproject.model.Messages
import com.example.dateappproject.model.Users
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase

class MessagesDao {


    private var UserCode: String

    private var firestore: FirebaseFirestore




    init {
        val user = Firebase.auth.currentUser
        //val user = Firebase.auth.currentUser?.uid
        UserCode = "$user"
        firestore = FirebaseFirestore.getInstance()

        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()


    }

    fun GetMessagesData(): MutableLiveData<List<Messages>> {
        val MessageList =
            MutableLiveData<List<Messages>>()     //    : -> Tipo de dato      = -> definicion de dato
        firestore.collection("DateApp")
            .document(UserCode)
            .collection("Chats")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val list = ArrayList<Messages>()

                        snapshot.documents.forEach {
                        val message =
                            it.toObject(Messages::class.java) // --> aqui define la clase y transforma los objetos desconocidos en la clase
                        if (message != null) {
                            list.add(message)   //Los agrega a la lista
                        }
                    }
                    MessageList.value =
                        list  //Traslada los datos obtenidos de la lista temp a la lista de respuesta


                }

            }
        return MessageList;
    }






}
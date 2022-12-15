package com.example.dateappproject

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dateappproject.Adapters.MessageAdapter
import com.example.dateappproject.data.MessagesDao
import com.example.dateappproject.databinding.ActivityChatBinding
import com.example.dateappproject.model.Messages
import com.example.dateappproject.model.State
import com.example.dateappproject.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import org.w3c.dom.Document
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import com.example.dateappproject.ChatActivity as ChatActivity1

class  ChatActivity : AppCompatActivity() {

    var binding : ActivityChatBinding? = null

    var adapter : MessageAdapter? = null

    var messages : ArrayList<Messages>? = null

    var senderRoom : String? =null

    var receiverRoom : String? = null

    var storage : FirebaseStorage? = null

    var dialog : ProgressDialog? = null

    var senderUid : String? = null

    var receiverUid : String? = null

    var messagesDao : MessagesDao? = null

    private lateinit var firestore: FirebaseFirestore

    private val args by navArgs<ChatActivityArgs>()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityChatBinding.inflate(layoutInflater )

        setContentView(binding!!.root)

        //setSupportActionBar(binding!!.toolbar)

        firestore = FirebaseFirestore.getInstance()

        dialog = ProgressDialog(this@ChatActivity)
        dialog!!.setMessage("Upload image")
        dialog!!.setCancelable(false)

        messages = ArrayList()
        val name = args.user.name
        val  profile =  args.user.profileMainPicture
        binding!!.usernameChat.text = name

        Glide.with(this@ChatActivity).load(profile)
            .placeholder(R.drawable.music_video_asmr_man)
            .into(binding!!.profileImageChat)
        binding!!.backOption.setOnClickListener{
            finish()
        }



        receiverUid = args.user.id
        senderUid = FirebaseAuth.getInstance().uid

        firestore.collection("Users").document(receiverUid.toString())
            .addSnapshotListener{ snapshot , e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if(snapshot != null) {

                    val status = snapshot.getData().toString()
                    if (status.equals("offline")){
                        binding!!.state.visibility = View.GONE

                }else{
                        //binding!!.state.setText(status)
                       // binding!!.state.visibility = View.VISIBLE
                    }
            /*  if (snapshot != null) {
                  val user = snapshot.toObject<Users>()
                  if(user!!.status.equals("conectado")){

                  }*/

                }

            }

        senderRoom = senderUid
        receiverRoom = receiverUid
        adapter = MessageAdapter(this@ChatActivity , messages!!, senderRoom!! , receiverRoom!!)

        binding!!.messagesRecycler.layoutManager = LinearLayoutManager(this@ChatActivity)
        binding!!.messagesRecycler.adapter = adapter

        firestore.collection("Users").document(senderUid.toString()).collection("chats")
            .document(receiverUid.toString()).collection("Messages")
            .addSnapshotListener{
                    snapshot , e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if(snapshot != null) {
                    snapshot.documents.forEach{msg ->
                        val message =  msg.toObject(Messages :: class.java)
                        if(message != null){
                            messages!!.add(message)
                        }
                    }

                }else{

                }

            }


        binding!!.sendMessage.setOnClickListener {
            val text = binding!!.etMessageText.text.toString()
            val time = Date()
            var message = Messages("", text, "", "", time, senderUid)
            binding!!.etMessageText.setText("")

            val lastMsgObj = HashMap<String, Any>()
            lastMsgObj["lastMsg"] = message.text!!
            lastMsgObj["lastMsgTime"] = message.time!!
            var document: DocumentReference =
                firestore.collection("Users").document(receiverUid.toString()).collection("chats")
                    .document(senderUid.toString()).collection("Messages")
                    .document()
            document.set(message).addOnSuccessListener {
                Log.d("aviso", "se paso el mensaje ")
                document = firestore.collection("Users").document(receiverUid.toString())
                    .collection("chats").document(senderUid.toString()).collection("Messages")
                    .document()
                document.set(message).addOnSuccessListener {
                    Log.d("aviso", "aqui tambien se logro")
                }
            }
        }

        val handler = Handler()
        binding!!.etMessageText.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val currentId = FirebaseAuth.getInstance().uid
                val document : DocumentReference =
                    firestore.collection("Users").document(currentId.toString())
                        .collection("Status").document()
                document.set("Typing...")
            }

            override fun afterTextChanged(s: Editable?) {
                val currentId = FirebaseAuth.getInstance().uid
                val document : DocumentReference =
                    firestore.collection("Users").document(currentId.toString())
                .collection("Status").document()
                document.set("offline")
            }

        })



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 25){
            if (data != null){
                if (data.data != null){
                    val selectedImage = data.data
                    val calendar = Calendar.getInstance()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val state : String = "offline"
        val currentId = FirebaseAuth.getInstance().uid
        val document : DocumentReference =
            firestore.collection("Users").document(currentId.toString())
        document.set(state)
    }

    override fun onResume() {
        super.onResume()
        val currentId = FirebaseAuth.getInstance().uid
        val state : String = "online"
        val document : DocumentReference =
            firestore.collection("Users").document(currentId.toString())
        document.set(state)
    }
}
package com.example.dateappproject

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
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
        val name = intent.getStringExtra("name")
        val  profile =  intent.getStringExtra("image")
        binding!!.usernameChat.text = name

        Glide.with(this@ChatActivity).load(profile)
            .placeholder(R.drawable.music_video_asmr_man)
            .into(binding!!.profileImageChat)
        binding!!.backOption.setOnClickListener{
            finish()
        }




        receiverUid = intent.getStringExtra("uid")
        senderUid = FirebaseAuth.getInstance().uid

        firestore.collection("Status").document(receiverUid.toString())
            .addSnapshotListener{ snapshot , e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if(snapshot != null) {

                    val status = snapshot.getData().toString()
                    if (status.equals("offline")){
                        binding!!.state.visibility = View.GONE

                }else{
                        binding!!.state.setText(status)
                        binding!!.state.visibility = View.VISIBLE
                    }
            /*  if (snapshot != null) {
                  val user = snapshot.toObject<Users>()
                  if(user!!.status.equals("conectado")){

                  }*/

                }

            }

        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid
 //       adapter = MessageAdapter(this@ChatActivity , messages , senderRoom!! , receiverRoom!!)

        binding!!.messagesRecycler.layoutManager = LinearLayoutManager(this@ChatActivity)
        binding!!.messagesRecycler.adapter = adapter
        firestore.collection("Users").document(receiverUid.toString()).collection("chats").document(senderUid.toString())


    }
}
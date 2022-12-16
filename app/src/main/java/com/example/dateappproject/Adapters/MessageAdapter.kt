package com.example.dateappproject.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dateappproject.*
import com.example.dateappproject.databinding.MessageReceivedBinding
import com.example.dateappproject.databinding.MessageSendBinding
import com.example.dateappproject.model.Messages
import com.google.firebase.auth.FirebaseAuth

//class MessageAdapter : RecyclerView.Adapter<MessageAdapter.MessageViewHolder >(){

class MessageAdapter( var context : Context,
    messagesList: ArrayList<Messages>?,
    sender : String ,
    receiver : String ) : RecyclerView.Adapter<RecyclerView.ViewHolder >(){

    lateinit var messagesList : ArrayList<Messages>


    val ITEM_SENT = 1
    val ITEM_RECIEVE = 2

    val sender : String
    val receiver : String

    inner class SendMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding : MessageSendBinding = MessageSendBinding.bind(itemView)


    }

    inner class ReceivedMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var binding : MessageReceivedBinding = MessageReceivedBinding.bind(itemView)

    }
    init{
        Log.d("aaaaa", "inicializando datos")
        if(messagesList != null ){
            this.messagesList = messagesList
        }
        this.sender = sender
        this.receiver = receiver
    }


    override fun getItemViewType(position: Int): Int {
        Log.d("analizando" , "esperando")
        val messages = messagesList[position]
        return if(FirebaseAuth.getInstance().uid != messages.senderId){
            ITEM_SENT
            Log.d("analizando" , "itemsent")
        }else{
            ITEM_RECIEVE
            Log.d("analizando" , "iten recieve")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("aaa" , "esta analizando")
        return  if (viewType == ITEM_SENT){
           val view : View = LayoutInflater.from(context).inflate(R.layout.message_send,
                                                                    parent,
                                                                        false)
             SendMessageViewHolder(view)
       }else{
             val view : View = LayoutInflater.from(context).inflate(R.layout.message_received,
                 parent,
                 false)
             ReceivedMessageViewHolder(view)
         }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message  = messagesList[position]
        if(holder.javaClass == SendMessageViewHolder :: class.java){
            val viewHolder = holder as SendMessageViewHolder
            if(message.type.equals("photo")){
                viewHolder.binding.imgReceive.visibility = View.VISIBLE
                viewHolder.binding.sendMessage.visibility = View.GONE
                viewHolder.binding.sendMessageHolder.visibility = View.GONE
                Glide.with(context)
                    .load(message.imageUrl)
                    .placeholder(R.drawable.example)
                    .into(viewHolder.binding.imgReceive)
            }
            viewHolder.binding.sendMessage.text = message.text
            viewHolder.itemView.setOnClickListener{


                Toast.makeText(context, "You're touching me.... kya!!!",Toast.LENGTH_LONG).show()
            }
        }else{
            val viewHolder = holder as ReceivedMessageViewHolder
            if(message.type.equals("photo")){
                viewHolder.binding.imgReceive.visibility = View.VISIBLE
                viewHolder.binding.sendReceive.visibility = View.GONE
                viewHolder.binding.receiveMessageHolder.visibility = View.GONE
                Glide.with(context)
                    .load(message.imageUrl)
                    .placeholder(R.drawable.example)
                    .into(viewHolder.binding.imgReceive)
            }
        }
    }

    override fun getItemCount(): Int = messagesList.size


/*
inner class ReceivedMessageViewHolder(private val itemBinding: MessageReceivedBinding)
    : RecyclerView.ViewHolder(itemBinding.root) {
    fun GetData(messages: Messages){
        itemBinding.sendReceive.text = messages.text
        itemBinding.timeSend.text = messages.time.toString()
        Glide.with(itemBinding.root.context)
            .load(messages.imageUrl)
            .circleCrop()
            .into( itemBinding.imgReceive)
        itemBinding.receiveMessageHolder.setOnClickListener { //Aca iria el de OnLongClick but i need to program that

        }

    }



}inner class SendMessageViewHolder(private val itemBinding: MessageSendBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun GetData(messages: Messages){
            itemBinding.sendMessage.text = messages.text
            itemBinding.timeSend.text = messages.time.toString()
            Glide.with(itemBinding.root.context)
                .load(messages.imageUrl)
                .circleCrop()
                .into( itemBinding.imgReceive)
            itemBinding.sendMessageHolder.setOnClickListener { //Aca iria el de OnLongClick but i need to program that

            }

        }

    }

*/


/*
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
     if ( viewType == ITEM_SENT){
         val view : View = LayoutInflater.from(parent.context).inflate(R.layout.message_send,
         parent,false)
     val itemBinding = MessageSendBinding
         .inflate(
             LayoutInflater.from(parent.context), parent, false
         )
     SendMessageViewHolder(itemBinding)
 }else {
         val view : View = LayoutInflater.from(parent.context).inflate(R.layout.message_send,
             parent,false)
         val itemBinding = MessageReceivedBinding
             .inflate(
                 LayoutInflater.from(parent.context), parent, false
             )
         ReceivedMessageViewHolder(itemBinding)
     }


    return MessageViewHolder(itemBinding)
}
 */


}

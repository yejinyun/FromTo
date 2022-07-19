package com.fromto.mailbox.more
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fromto.R
import com.fromto.config.Application.Companion.prefs
import com.fromto.databinding.ItemChattitleBinding
import com.fromto.retrofit.Chatbox

class ChatboxRvAdapter(private val chatBoxList:ArrayList<Chatbox>)
    : RecyclerView.Adapter<ChatboxRvAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(chatbox: Chatbox)
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListner: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListner = itemClickListener
    }

    override fun getItemCount(): Int = chatBoxList.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding : ItemChattitleBinding = ItemChattitleBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup,
            false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatboxRvAdapter.ViewHolder, position: Int) {
        holder.bind(chatBoxList[position])

        holder.itemView.setOnClickListener {
            mItemClickListner.onItemClick(chatBoxList[position])
        }
    }

    //뷰홀더 > 아이템뷰 객체들을 담는 그릇
    inner class ViewHolder(val binding: ItemChattitleBinding):RecyclerView.ViewHolder(binding.root){

        val date = itemView?.findViewById<TextView>(R.id.item_mailbox_more_date_tv)
        val recipient = itemView?.findViewById<TextView>(R.id.item_mailbox_more_reciever_tv)
        val sender = itemView?.findViewById<TextView>(R.id.item_mailbox_more_sender_tv)

        fun bind(itemChatbox: Chatbox){
            val chatRecipient = itemChatbox.recipient.toString()
            val chatSender = itemChatbox.sender.toString()
            val contents = itemChatbox.contents.toString()

            date.text = itemChatbox.date
            recipient.text = chatRecipient
            sender.text = chatSender

            prefs.edit().putString("chatRecipient",chatRecipient).apply()
            prefs.edit().putString("chatSender",chatSender).apply()
            prefs.edit().putString("MailContents",contents).apply()
        }
    }
}
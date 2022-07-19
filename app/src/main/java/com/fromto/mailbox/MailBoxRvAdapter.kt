package com.fromto.mailbox
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fromto.MainActivity
import com.fromto.R
import com.fromto.config.Application.Companion.prefs
import com.fromto.databinding.ItemMailtitleBinding
import com.fromto.retrofit.Mailbox

class MailBoxRvAdapter(private val mailBoxList:ArrayList<List<Mailbox>>, val context: Context)
    : RecyclerView.Adapter<MailBoxRvAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(mailbox: List<Mailbox>)
        fun onRemoveAlbum(position: Int)
    }

    //리스트 객체를 전달받는 함수랑 리스너 객체를 저장할 변수
    private lateinit var mItemClickListner: MyItemClickListener

    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListner = itemClickListener
    }

    override fun getItemCount(): Int = mailBoxList.size

    override fun onCreateViewHolder(
        viewGroup: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding:ItemMailtitleBinding = ItemMailtitleBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup,
            false
        )

        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MailBoxRvAdapter.ViewHolder, position: Int) {
        holder.bind(listOf(mailBoxList[position][0]))

        holder.itemView.setOnClickListener {
            mItemClickListner.onItemClick(listOf(mailBoxList[position][0]))
        }
    }

    //뷰홀더 > 아이템뷰 객체들을 담는 그릇
    inner class ViewHolder(val binding: ItemMailtitleBinding):RecyclerView.ViewHolder(binding.root){
        val poster = itemView?.findViewById<ImageView>(R.id.item_mailbox_poster_iv)
        val LetterTitle = itemView?.findViewById<TextView>(R.id.item_mailbox_mailtitle_tv)
        val fromName = itemView?.findViewById<TextView>(R.id.item_mailbox_from_tv)
        val MovieTitle = itemView?.findViewById<TextView>(R.id.item_mailbox_movietitle_tv)

        fun bind(itemMail: List<Mailbox>){
            val urlString = itemMail[0].imgUrl
            if(!urlString!!.isEmpty()){
                poster?.setImageResource(R.drawable.img_empty_movie)
                //포스터 없을경우 기본 이미지로
                } else {
                    poster?.visibility = View.GONE
                }

            Glide.with(context).load(itemMail[0].imgUrl).into(binding.itemMailboxPosterIv)
            LetterTitle?.text = itemMail[0].letterTitle
            fromName?.text = itemMail[0].nickname
            MovieTitle?.text = itemMail[0].movieTitle
        }
    }
}
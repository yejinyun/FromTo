package com.fromto.mypage.wirttenmovie
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fromto.R
import com.fromto.databinding.ItemPosterBinding
import com.fromto.mypage.wirttenmovie.WrittenmovieRvAdapter.ViewHolder
import com.fromto.retrofit.WrittenPoster
import kotlinx.android.synthetic.main.item_poster.view.*

class WrittenmovieRvAdapter(private val posterList:ArrayList<List<WrittenPoster>>, val context: Context)
    : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int = posterList.size

    override fun onCreateViewHolder(
        viewGroup: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding:ItemPosterBinding = ItemPosterBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup,
            false
        )

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOf(posterList[position][0]))
    }

    //뷰홀더 > 아이템뷰 객체들을 담는 그릇
    inner class ViewHolder(val binding: ItemPosterBinding):RecyclerView.ViewHolder(binding.root){
        val poster = itemView?.findViewById<ImageView>(R.id.item_written_poster_iv)
        val LetterTitle = itemView?.findViewById<TextView>(R.id.item_written_poster_tv)

        fun bind(itemPoster: List<WrittenPoster>){
            val urlString = itemPoster[0].imgUrl
            if(!urlString!!.isEmpty()){
                poster?.setImageResource(R.mipmap.ic_fromto_foreground)
                //포스터 없을경우 기본 이미지로
                } else {
                    poster?.visibility = View.GONE }
            Glide.with(context).load(itemPoster[0].imgUrl).into(binding.itemWrittenPosterIv)
            LetterTitle?.text = itemPoster[0].letterTitle
        }

//            Glide.with(requireContext()).load(response.result!![0][0].imgUrl).into(binding.testIv)
//            binding.testTv.setText(response.result[0][0].letterTitle)



//            if(!urlString.isEmpty()){
//                poster?.setImageResource(R.mipmap.ic_fromto_foreground)
//                //포스터 없을경우 기본 이미지로
//            } else {
//                poster?.visibility = View.GONE }
////            Glide.with(requireContext()).load(response.result?.가장먼저쓴포스터3개?.get(1)!!).into(binding.mypageWrittenPosterIv02)
//            letterTitle?.text = itemPoster?.letterTitle }
        }

//    interface MyItemClickListener{
//        fun onItemClick(poster: WrittenPoster)
//        fun onRemoveAlbum(position: Int)
//    }
//
//    private lateinit var mItemClickListner: MyItemClickListener
//    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
//        mItemClickListner = itemClickListener
//    }
// 데이터 변경시 리스트 다시 할당
}
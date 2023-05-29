package com.example.linusapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.linusapp.vo.ContentVO

class ImageAdapter(private val imageList: MutableList<ContentVO>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(contentVO: ContentVO) {
            itemView.findViewById<ImageView>(R.id.imageView).setImageResource(contentVO.image)
            itemView.findViewById<TextView>(R.id.title_content).text = contentVO.contentTitle
            itemView.findViewById<TextView>(R.id.content_card).text = contentVO.content
            itemView.setOnClickListener{
                val contentTextActivity = Intent(itemView!!.context, TelaConteudoTexto::class.java)
                contentTextActivity.putExtra("idContent", contentVO.idContent)
                contentTextActivity.putExtra("fkLevel", contentVO.fkLevel)
                itemView.context.startActivity(contentTextActivity)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_container, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position])
        if (position == imageList.size - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}
package com.example.linusapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.linusapp.vo.DistroVO

class ImageAdapterDistribuicao(
    private val distroList: MutableList<DistroVO>,
    private val viewPager2: ViewPager2
) :
    RecyclerView.Adapter<ImageAdapterDistribuicao.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageDistro)
        fun bind(distroVO: DistroVO) {
            itemView.findViewById<ImageView>(R.id.imageDistro).setImageResource(distroVO.image)
            itemView.findViewById<TextView>(R.id.title_distro).text = distroVO.distroName
            itemView.findViewById<TextView>(R.id.distro_content_card).text = distroVO.distroVersion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.image_container_distro, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(distroList[position])
        if (position == distroList.size - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return distroList.size
    }

    private val runnable = Runnable {
        distroList.addAll(distroList)
        notifyDataSetChanged()
    }
}
package com.example.linusapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.linusapp.vo.DistroVO
import kotlin.coroutines.coroutineContext


class ImageAdapterDistribuicao(
    private val distroList: MutableList<DistroVO>,
    private val viewPager2: ViewPager2
) : RecyclerView.Adapter<ImageAdapterDistribuicao.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(distroVO: DistroVO) {
            itemView.findViewById<ImageView>(R.id.imageDistro).setImageResource(distroVO.image)
            itemView.findViewById<TextView>(R.id.title_distro).text = distroVO.distroName
            itemView.findViewById<TextView>(R.id.distro_content_card).text = "Versão: " + distroVO.distroVersion
            itemView.findViewById<TextView>(R.id.level_text).text = "Nivel recomendado: " + getLevelName(distroVO.fkLevel)
            itemView.setOnClickListener {
                val intent = Intent("RestApiData")
                intent.putExtra("test", "bla")
                LocalBroadcastManager.getInstance(itemView.findNavController().context).sendBroadcast(intent)
                itemView.findNavController().navigate(R.id.nav_content_by_level)
            }
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

    fun getLevelName(idLevel: Int) = if (idLevel == 1) "Iniciante" else if (idLevel == 2) "Intermediario" else "Avançado"

    private val runnable = Runnable {
        distroList.addAll(distroList)
        notifyDataSetChanged()
    }
}
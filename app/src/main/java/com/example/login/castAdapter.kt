package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class castAdapter(val context:show_Movies, val castlist: List<cast>) : RecyclerView.Adapter<castAdapter.newViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.castandcrew,parent,false)
        return newViewHolder(view)
    }

    override fun getItemCount(): Int {
        return castlist.size
    }

    override fun onBindViewHolder(holder: newViewHolder, position: Int) {
        val realcast = castlist[position]
       // val real= realcast.cast[position]
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+realcast.profile_path).error(R.drawable.baseline_account_circle_24)
            .into(holder.profile_pic)
        holder.profile_name.text=realcast.original_name

    }
    class newViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val profile_pic= itemView.findViewById<CircleImageView>(R.id.profile_pic)
        val profile_name = itemView.findViewById<TextView>(R.id.profile_name)

    }

}
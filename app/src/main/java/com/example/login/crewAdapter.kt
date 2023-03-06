package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class crewAdapter(val context: show_Movies,val CrewList:List<crew>):RecyclerView.Adapter<crewAdapter.nayaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): nayaViewHolder {
        val inflate =LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.castandcrew,parent,false)
        return nayaViewHolder(view)
    }

    override fun onBindViewHolder(holder: nayaViewHolder, position: Int) {
        val realcrew=CrewList[position]
       Glide.with(context).load("https://image.tmdb.org/t/p/w500"+realcrew.profile_path).error(R.drawable.baseline_account_circle_24).into(holder.profile_pic)
        holder.profile_name.text=realcrew.original_name
    }

    override fun getItemCount(): Int {
      return CrewList.size
    }

    class nayaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profile_pic= itemView.findViewById<CircleImageView>(R.id.profile_pic)
        val profile_name = itemView.findViewById<TextView>(R.id.profile_name)
    }
}
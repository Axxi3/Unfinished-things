package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class castDetailsAdapter(val context:castShower, val casty:castdetailsdata)
    :RecyclerView.Adapter<castDetailsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate= LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.castrecyclerlayout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
          val data=casty
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+ data.profile_path).into(holder.pfp)
        holder.depart.text=data.known_for_department
        holder.bio.text=data.biography
        holder.name.text=data.name
    }

    override fun getItemCount(): Int {
       return 1
    }

    class ViewHolder(item: View):RecyclerView.ViewHolder(item) {
        val pfp=item.findViewById<ImageView>(R.id.pfp)
        val depart=item.findViewById<TextView>(R.id.depart)
        val bio=item.findViewById<TextView>(R.id.bio)
        val name=item.findViewById<TextView>(R.id.castname)
    }
}
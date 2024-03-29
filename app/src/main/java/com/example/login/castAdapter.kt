package com.example.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView

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
        holder.itemView.setOnClickListener {
            castdetailscalled(realcast,it)
        }
    }

    private fun castdetailscalled(realcast: cast, it: View?) {
           val intent=Intent(it!!.context,castShower::class.java).apply {
               putExtra("cast_id",realcast.id)
           }
        it.context.startActivity(intent)
    }


    class newViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val profile_pic= itemView.findViewById<CircularImageView>(R.id.profile_pic)
        val profile_name = itemView.findViewById<TextView>(R.id.profile_name)

    }

}
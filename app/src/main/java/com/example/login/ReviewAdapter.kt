package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class ReviewAdapter(val context:show_Movies, val reviewer: List<reviewdata.Results>):RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.reviews,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reviews=reviewer[position]
        holder.name.text=reviews.author
        Glide.with(context).load("https://image.tmdb.org/t/p/w500"+ reviews.url).into(holder.avatar)
        holder.review.text=reviews.content
    }

    override fun getItemCount(): Int {
        return reviewer.size
    }

    class ViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
var avatar=itemView.findViewById<CircleImageView>(R.id.avatar)
        var name= itemView.findViewById<TextView>(R.id.reviewname)
        var review= itemView.findViewById<TextView>(R.id.review)
        var rate= itemView.findViewById<RatingBar>(R.id.rate)
    }
}
package com.example.login.repo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.login.Movie
import com.example.login.R
import com.example.login.Real_home

class PagingAdapter(val context:Real_home):PagingDataAdapter<Movie.Result, PagingAdapter.MyViewHolder>(COMPARATOR) {

    private var pass: Int=0
    private  var rater:Float = 0.0f
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val discy = getItem(position)
        if(discy!=null) {
            holder.cardy.setBackgroundColor(pass)
            Glide.with(context).load("https://image.tmdb.org/t/p/w500" + (discy.poster_path  ))
                .into(holder.img)

            holder.name.text = discy.title
            rater = discy.vote_average.toFloat()
            holder.rate.rating = rater
            holder.relese.text = discy.release_date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.discover,parent,false)
        return MyViewHolder(view)
    }

    class MyViewHolder (itemview:View):RecyclerView.ViewHolder(itemview){
        val img= itemView.findViewById<ImageView>(R.id.DiscoverMovie)!!
        val name= itemView.findViewById<TextView>(R.id.Moviename)!!
        val rate = itemView.findViewById<RatingBar>(R.id.movierate)!!
        val relese=itemView.findViewById<TextView>(R.id.RElease)!!
        val cardy=itemView.findViewById<CardView>(R.id.cardy)!!
    }



    companion object {
        private val COMPARATOR =object :DiffUtil.ItemCallback<Movie.Result>() {
            override fun areItemsTheSame(oldItem: Movie.Result, newItem: Movie.Result): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie.Result, newItem: Movie.Result): Boolean {
                return oldItem==newItem
            }
        }
    }
}
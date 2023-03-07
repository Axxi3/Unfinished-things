package com.example.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class DiscoverAdapter2(val context: Real_home?,val context2:MainActivity2?,val MovieList: List<Movie.Result>): RecyclerView.Adapter<DiscoverAdapter2.ViewHolder2>() {
    private val colors= context?.resources?.getIntArray(R.array.mycolor)
    private val colors2= context2?.resources?.getIntArray(R.array.mycolor)
    private var pass: Int=0
    private  var rater:Float = 0.0f
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.discover,parent,false)
        return DiscoverAdapter2.ViewHolder2(view)
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        val discy= MovieList[position]
        holder.setIsRecyclable(false)
        if (context2 != null && discy!=null) {
            pass= colors2?.get((colors2?.indices)!!.random())!!
            holder.cardy.setBackgroundColor(pass!!)
            Glide.with(context2).load("https://image.tmdb.org/t/p/w500" + (discy!!.poster_path  ))
                .into(holder.img)

            holder.name.text = discy!!.title
            rater = discy.vote_average.toFloat()
            holder.rate.rating = rater
            holder.relese.text = discy.release_date
        }
        else{
            pass= colors?.get((colors?.indices)!!.random())!!
            holder.cardy.setBackgroundColor(pass!!)
            // holder.cardy.setBackgroundColor(colors?.get((colors.indices).random())!!)
            context?.let {
                Glide.with(context).load("https://image.tmdb.org/t/p/w500" + discy!!.poster_path).error(R.drawable.samurai)
                    .into(holder.img)
                holder.name.text = discy.title
                holder.relese.text = discy.release_date
                if (discy.vote_average!=null)
                    rater = discy.vote_average.toFloat()

                holder.rate.rating = rater
            }
        }
        holder.itemView.setOnClickListener{
            discover(discy,it, pass!!.toString())
        }
    }

    private fun discover(discy: Movie.Result, it: View?, toString: String) {
            val intent = Intent(it!!.context,show_Movies::class.java).apply {
                putExtra("what_type", "movie")
                putExtra("extra_movie_backdrop", discy!!.backdrop_path)
                putExtra("extra_movie_poster", discy.poster_path)
                putExtra("extra_movie_title", discy.title)
                putExtra("extra_movie_release_date", discy.release_date)
                putExtra("movie_id", discy.id)
                putExtra("extra_movie_overview", discy.overview)
                if (discy.vote_average != null)
                    putExtra("extra_movie_rating", discy.vote_average.toFloat())

            }
            it.context.startActivity(intent)

    }

    override fun getItemCount(): Int {
       return MovieList.size
    }

    class ViewHolder2(itemView: View):RecyclerView.ViewHolder(itemView) {
        val img= itemView.findViewById<ImageView>(R.id.DiscoverMovie)
        val name=itemView.findViewById<TextView>(R.id.Moviename)
        val rate = itemView.findViewById<RatingBar>(R.id.movierate)
        val relese=itemView.findViewById<TextView>(R.id.RElease)
        val cardy=itemView.findViewById<CardView>(R.id.cardy)
    }
}
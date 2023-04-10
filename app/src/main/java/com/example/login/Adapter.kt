package com.example.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter(
    val context: Real_home?, val context2: show_Movies?,val context3:Real_profile?, val MovieList: List<Movie.Result>,
    val what:String) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    var onItemClick:((show_Movies)->Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate= LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.card,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
               return MovieList.size
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
val realMovies= MovieList[position]
      //  holder.MovieName.text=realMovies.original_title
       // holder.des.text=realMovies.overview
        if (context!=null) {
            context?.let { Glide.with(it).load("https://image.tmdb.org/t/p/w500"+ realMovies.poster_path).into(holder.Pics) }
        }
        else if(context3!=null) {
                    Glide.with(context3).load("https://image.tmdb.org/t/p/w500"+ realMovies.poster_path).into(holder.Pics)
        }
        else {
            context2?.let { Glide.with(it).load("https://image.tmdb.org/t/p/w500"+ realMovies.poster_path).into(holder.Pics) }
        }

           // holder.pop.text=realMovies.popularity
        holder.itemView.setOnClickListener {
             moviedetails(realMovies,it)
        }


    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      //var MovieName=  itemView.findViewById<TextView>(R.id.movieName)
        var Pics= itemView.findViewById<ImageView>(R.id.pics)
      //  var des = itemView.findViewById<TextView>(R.id.des)
       // var pop = itemView.findViewById<TextView>(R.id.pop)

    }


    private fun moviedetails(realMovies: Movie.Result, it: View?) {
    val intent =Intent(it!!.context,show_Movies::class.java).apply {
        putExtra("what_type",what)
        putExtra("extra_movie_backdrop", realMovies.backdrop_path)
        putExtra("extra_movie_poster",realMovies.poster_path)
        putExtra("extra_movie_title",realMovies.title)
        putExtra("extra_movie_overview",realMovies.overview)
       putExtra("extra_movie_rating",realMovies.vote_average.toFloat())
        putExtra("extra_movie_release_date",realMovies.release_date)
        putExtra("movie_id",realMovies.id)
        putExtra("genere",realMovies.genre_ids)
    }
        it.context.startActivity(intent)
    }
}


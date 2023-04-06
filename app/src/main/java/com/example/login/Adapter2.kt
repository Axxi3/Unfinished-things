package com.example.login


import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter2(
    val context2: show_Movies?, val MovieList: List<Result>,
    val what:String) : RecyclerView.Adapter<Adapter2.ViewHolder>() {
    var onItemClick:((show_Movies)->Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate= LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.card,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val realMovies= MovieList[position]

if(context2!=null) {
    Glide.with(context2).load("https://image.tmdb.org/t/p/w500" + realMovies.poster_path)
        .into(holder.Pics)
}

        // holder.pop.text=realMovies.popularity
        holder.itemView.setOnClickListener {
            moviedetails(realMovies,it)
        }
    }

    override fun getItemCount(): Int {
        return MovieList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //var MovieName=  itemView.findViewById<TextView>(R.id.movieName)
        var Pics= itemView.findViewById<ImageView>(R.id.pics)
        //  var des = itemView.findViewById<TextView>(R.id.des)
        // var pop = itemView.findViewById<TextView>(R.id.pop)

    }


    private fun moviedetails(realMovies: Result, it: View?) {
        val intent =Intent(it!!.context,show_Movies::class.java).apply {
            putExtra("what_type",what)
            putExtra("extra_movie_backdrop", realMovies.backdrop_path)
            putExtra("extra_movie_poster",realMovies.poster_path)
            putExtra("extra_movie_title",realMovies.title)
            putExtra("extra_movie_overview",realMovies.overview)
            putExtra("extra_movie_rating",realMovies.vote_average.toFloat())
            putExtra("extra_movie_release_date",realMovies.release_date)
            putExtra("movie_id",realMovies.id)
        }
        it.context.startActivity(intent)
    }
}


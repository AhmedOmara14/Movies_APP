package com.task.presentation.list_movies.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.databinding.ItemMovieBinding
import com.task.domain.model.all_movies.Movie
import com.squareup.picasso.Picasso
import com.task.common.Constants.BASE_URL_IMAGE

class MoviesAdapter(private var listener: MovieClickListener) :
    RecyclerView.Adapter<MoviesAdapter.viewHolder>() {
    private var allMovies: List<Movie>? = null

    fun setAllMovies(allMovies: List<Movie>?) {
        this.allMovies = allMovies
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        Log.d("TAG", "onBindViewHolder: "+ allMovies?.get(position))
        holder.bind(allMovies!![position])

        holder.itemView.setOnClickListener {
            listener.getDetails(allMovies!![position].id.toString())
        }
    }

    override fun getItemCount(): Int {
        return if (allMovies != null) allMovies!!.size else 0
    }
    class viewHolder(var binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie?) {
            Picasso.get().load(BASE_URL_IMAGE+movie?.poster_path).into(binding.ivMovie)
            binding.movie=movie
        }
        init {
            this.binding = binding
        }
    }

}

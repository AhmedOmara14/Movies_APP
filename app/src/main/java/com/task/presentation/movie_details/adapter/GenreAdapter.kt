package com.task.presentation.movie_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.databinding.ItemGenresBinding
import com.task.domain.model.movie_details.Genre

class GenreAdapter() :
    RecyclerView.Adapter<GenreAdapter.viewHolder>() {
    private var allGenres: List<Genre>? = null

    fun setAllGenres(allGenres: List<Genre>?) {
        this.allGenres = allGenres
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            ItemGenresBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.bind(allGenres!![position])

    }

    override fun getItemCount(): Int {
        return if (allGenres != null) allGenres!!.size else 0
    }
    class viewHolder(var binding: ItemGenresBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre?) {
            binding.genre=genre
        }
        init {
            this.binding = binding
        }
    }

}

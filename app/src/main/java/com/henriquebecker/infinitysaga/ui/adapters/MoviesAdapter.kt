package com.henriquebecker.infinitysaga.ui.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.henriquebecker.infinitysaga.R
import com.henriquebecker.infinitysaga.data.entity.Movie
import com.henriquebecker.infinitysaga.ui.fragments.MoviesFragmentDirections
import kotlinx.android.synthetic.main.movie_item.view.*

class MoviesAdapter(private val updateMovie: (m: Movie) -> Unit):
    PagedListAdapter<Movie, MoviesAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(old: Movie, new: Movie): Boolean =
                old.title == new.title
            override fun areContentsTheSame(old: Movie, new: Movie): Boolean {
                return old == new
            }
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val poster: AppCompatImageView = itemView.movie_item_image
        private val title: MaterialTextView = itemView.movie_item_title
        private val year: MaterialTextView = itemView.movie_item_year
        private val genres: MaterialTextView = itemView.movie_item_genres
        private val favoriteBtn: AppCompatImageView = itemView.movie_item_btn_favorite


        fun bindTo(movie: Movie, updateMovie: (m: Movie) -> Unit) {
            favoriteBtn.setImageResource(if(movie.favorite == true) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border_white_24dp)

            title.text = movie.title
            genres.text = movie.genre
            year.text = movie.year.toString()

            Glide.with(itemView)
                .load(Uri.parse(movie.poster))
                .placeholder(R.drawable.movie_place_holder)
                .error(R.drawable.movie_place_holder)
                .into(poster)

            favoriteBtn.setOnClickListener {
                movie.favorite = movie.favorite != true
                favoriteBtn.setImageResource(if(movie.favorite == true) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border_white_24dp)
                updateMovie(movie)
            }
            itemView.setOnClickListener {
                it.findNavController().navigate(MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(movie.title))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MovieViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let{
            holder.bindTo(it, updateMovie)
        }
    }
}
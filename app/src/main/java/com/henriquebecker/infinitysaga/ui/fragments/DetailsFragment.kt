package com.henriquebecker.infinitysaga.ui.fragments


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.henriquebecker.infinitysaga.R
import com.henriquebecker.infinitysaga.data.entity.Movie
import com.henriquebecker.infinitysaga.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat


class DetailsFragment : Fragment() {
    private val viewModel by viewModel<MovieViewModel>()
    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMovie()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    private fun initMovie(){
        viewModel.getMovie(args.movieTitle).observe(viewLifecycleOwner, Observer {
            updateView(it)
        })
    }

    private fun updateView(movie: Movie) {
        Glide.with(this)
            .load(Uri.parse(movie.poster))
            .placeholder(R.drawable.movie_place_holder)
            .error(R.drawable.movie_place_holder)
            .into(movie_poster)

        movie_director.text = movie.director

        movie_genre.text = movie.genre

        movie_rated.text = getString(R.string.rated_holder, movie.rated)

        val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)
        movie_released.text = getString(R.string.released_holder, dateFormat.format(movie.released.time))

        movie_writer.text = movie.writer.replace(", ", "\n")

        movie_actors.text = movie.actors.replace(", ", "\n")

        movie_plot.text = movie.plot

        movie_btn_favorite.setImageResource(if(movie.favorite == true) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border_white_24dp)

        movie_btn_favorite.setOnClickListener {
            movie.favorite = movie.favorite != true
            movie_btn_favorite.setImageResource(if(movie.favorite == true) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border_white_24dp)
            viewModel.updateMovie(movie)
        }

    }
}

package com.henriquebecker.infinitysaga.viewmodel

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import com.henriquebecker.infinitysaga.data.datasource.MovieDataSource
import com.henriquebecker.infinitysaga.data.entity.Movie
import com.henriquebecker.infinitysaga.data.repository.MovieRepository
import com.henriquebecker.infinitysaga.util.LoadingState
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository, private val dataSource: MovieDataSource) : ViewModel() {
    private val _loadingState = MutableLiveData<LoadingState>()

    val loadingState: LiveData<LoadingState>
        get() = _loadingState

    val movies = LivePagedListBuilder(dataSource,30).build()

    fun refreshDataSource(){
        movies.value?.dataSource?.invalidate()
    }

    fun search(query: String){
        dataSource.query = query
        refreshDataSource()
    }


    fun getQuery() = dataSource.query

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                repository.refresh()
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }

    fun updateMovie(movie: Movie) {
        viewModelScope.launch {
            repository.update(movie)
        }
    }

    fun getMovie(movieTitle: String) = repository.getMovie(movieTitle)
}
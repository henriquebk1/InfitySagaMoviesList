package com.henriquebecker.infinitysaga.data.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.henriquebecker.infinitysaga.data.api.MovieApi
import com.henriquebecker.infinitysaga.data.dao.MovieDao
import com.henriquebecker.infinitysaga.data.entity.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val movieApi: MovieApi, private val movieDao: MovieDao) {
    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val movies = movieApi.getAllAsync()
            movieDao.upsertAll(movies)
        }
    }

    suspend fun update(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieDao.update(movie)
        }
    }

    fun getAll(sql: SupportSQLiteQuery) = movieDao.getAllPagingWhere(sql)
    fun getMovie(movieTitle: String) = movieDao.getMovie(movieTitle)
}
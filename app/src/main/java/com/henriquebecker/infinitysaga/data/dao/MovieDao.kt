package com.henriquebecker.infinitysaga.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.henriquebecker.infinitysaga.data.entity.Movie

@Dao
interface MovieDao {
    @RawQuery(observedEntities = [Movie::class])
    fun getAllPagingWhere(query: SupportSQLiteQuery): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM movie WHERE title = :title")
    fun findSync(title: String): Movie

    @Query("SELECT * FROM movie WHERE title = :title")
    fun getMovie(title: String): LiveData<Movie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(movie: Movie): Long

    @Update
    fun update(movie: Movie)

    fun upsertAll(movies: List<Movie>){
        movies.forEach { movie ->
            val id = insert(movie)
            if (id == -1L) {
                val old = findSync(movie.title)
                movie.favorite = old.favorite
                update(movie)
            }
         }
    }


}
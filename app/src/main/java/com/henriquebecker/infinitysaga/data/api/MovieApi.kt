package com.henriquebecker.infinitysaga.data.api

import com.henriquebecker.infinitysaga.data.entity.Movie
import retrofit2.http.GET

interface MovieApi {
    @GET("/saga")
    suspend fun getAllAsync(): List<Movie>
}
package com.henriquebecker.infinitysaga.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "movie")
@Parcelize
data class Movie(
        @SerializedName("title")
        @PrimaryKey val title: String,
        @SerializedName("year")
        val year: Int,
        @SerializedName("rated")
        val rated: String,
        @SerializedName("released")
        val released: Calendar,
        @SerializedName("runtime")
        val runtime: String,
        @SerializedName("genre")
        val genre: String,
        @SerializedName("director")
        val director: String,
        @SerializedName("writer")
        val writer: String,
        @SerializedName("actors")
        val actors: String,
        @SerializedName("plot")
        val plot: String,
        @SerializedName("poster")
        val poster: String,
        var favorite: Boolean? = false): Parcelable
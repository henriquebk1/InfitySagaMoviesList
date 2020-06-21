package com.henriquebecker.infinitysaga.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.henriquebecker.infinitysaga.data.dao.MovieDao
import com.henriquebecker.infinitysaga.data.entity.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
}
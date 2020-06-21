package com.henriquebecker.infinitysaga.data.datasource

import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.henriquebecker.infinitysaga.data.entity.Movie
import com.henriquebecker.infinitysaga.data.repository.MovieRepository
import com.henriquebecker.infinitysaga.prefs
import com.henriquebecker.infinitysaga.util.OrderType

class MovieDataSource(private val repository: MovieRepository) : DataSource.Factory<Int, Movie>() {
    var query = ""

    override fun create(): DataSource<Int, Movie> {
        val re = Regex("[^A-Za-z0-9 ]")
        query = re.replace(query, "")
        var where = ""
        if (query.isNotBlank()) {
            query.split(" ").forEach {
                where += if (where.isBlank()) "WHERE " else " AND "
                where += "(title LIKE '%$it%' OR rated LIKE '%$it%' OR year = '$it' OR runtime LIKE '%$it%' OR genre LIKE '%$it%' OR director LIKE '%$it%' OR writer LIKE '%$it%' OR actors LIKE '%$it%')"
            }
        }
        val orderBy = when (prefs.order) {
            OrderType.TITLE.type -> "ORDER BY title ASC"
            OrderType.FAVORITE.type -> "ORDER BY favorite DESC"
            OrderType.YEAR.type -> "ORDER BY YEAR DESC"
            else -> ""
        }
        val sql = SimpleSQLiteQuery("SELECT * FROM movie $where $orderBy")
        return repository.getAll(sql).create()
    }
}
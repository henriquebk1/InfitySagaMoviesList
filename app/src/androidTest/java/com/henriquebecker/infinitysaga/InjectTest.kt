package com.henriquebecker.infinitysaga

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.henriquebecker.infinitysaga.data.repository.MovieRepository
import com.henriquebecker.infinitysaga.viewmodel.MovieViewModel
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.get
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class InjectTest : KoinTest {
    private val repository: MovieRepository by inject()
    private val viewModel: MovieViewModel by inject()

    @Test
    fun testInjection() {
        val r = get<MovieRepository>()
        val v = get<MovieViewModel>()

        Assert.assertNotNull(r)
        assertEquals(r, repository)


        Assert.assertNotNull(viewModel)
        Assert.assertNotNull(v)
    }

}
package com.henriquebecker.infinitysaga.ui.fragments


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.henriquebecker.infinitysaga.R
import com.henriquebecker.infinitysaga.prefs
import com.henriquebecker.infinitysaga.ui.adapters.MoviesAdapter
import com.henriquebecker.infinitysaga.util.LoadingState
import com.henriquebecker.infinitysaga.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movies.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MoviesFragment : Fragment() {
    private val viewModel by viewModel<MovieViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRefresh()
        initState()
        initList()
        setHasOptionsMenu(true)
    }


    private fun initState() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                LoadingState.Status.FAILED -> {
                    refreshList.isRefreshing = false
                    Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                }
                LoadingState.Status.RUNNING -> {
                    refreshList.isRefreshing = true
                }
                LoadingState.Status.SUCCESS -> {
                    refreshList.isRefreshing = false
                }
            }
        })
    }

    private fun initRefresh() {
        refreshList.setOnRefreshListener {
            viewModel.fetchData()
        }
    }

    private fun initList() {
        val adapter = MoviesAdapter{ movie ->
            viewModel.updateMovie(movie)
        }
        moviesList.adapter = adapter
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        val searchManager = requireContext().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.app_bar_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.search(newText?:"")
                    return true
                }
            })
            setQuery(viewModel.getQuery(), false)
            isIconified = viewModel.getQuery().isBlank()
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.app_bar_sort){
            val mySortAlertDialog = AlertDialog.Builder(requireContext())
            mySortAlertDialog.setTitle(R.string.sort)
            val r = arrayOf(getString(R.string.by_title), getString(R.string.by_favorite), getString(R.string.by_year))
            mySortAlertDialog.setSingleChoiceItems(r, prefs.order) { dialog, which ->
                prefs.order = which
                viewModel.refreshDataSource()
                moviesList.scrollToPosition(0)
                dialog.dismiss()
            }
            mySortAlertDialog.setNegativeButton(R.string.cancel
            ) { dialog, _ ->
                dialog.dismiss()
                            }
            mySortAlertDialog.create().show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}


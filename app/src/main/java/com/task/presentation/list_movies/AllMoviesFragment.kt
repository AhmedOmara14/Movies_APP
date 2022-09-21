package com.task.presentation.list_movies

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.illa.task.common.NetworkConnection
import com.task.R
import com.task.common.Constants.API_TOKEN
import com.task.common.Constants.TAG_LOADING
import com.task.common.HelperClass
import com.task.common.LoadingDialog
import com.task.common.Status
import com.task.databinding.FragmentAllMoviesBinding
import com.task.domain.model.all_movies.Movie
import com.task.presentation.list_movies.adapter.MovieClickListener
import com.task.presentation.list_movies.adapter.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllMoviesFragment : Fragment(), MovieClickListener {
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var binding: FragmentAllMoviesBinding
    private val viewModel: AllMoviesViewModel by viewModels()
    private lateinit var networkConnection: NetworkConnection
    private lateinit var loadingDialog: LoadingDialog
    private var page = 1
    private var allMoviesList: ArrayList<Movie> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_all_movies,
                container,
                false
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getData()
        initMovieAdapter()
        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView, _: Int, scrollY: Int, _: Int, _: Int ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                getData()
            }
        }

    }


    private fun initMovieAdapter() {
        moviesAdapter = MoviesAdapter(
            this
        )
        val linearLayoutManager = LinearLayoutManager(activity)
        binding.rvAllMovies.layoutManager = linearLayoutManager
        binding.rvAllMovies.adapter = moviesAdapter

    }

    override fun onResume() {
        super.onResume()
        allMoviesList = ArrayList()
        init()
    }

    private fun init() {
        networkConnection = NetworkConnection(requireContext())
        loadingDialog = LoadingDialog()
        loadingDialog.isCancelable = false

    }

    private fun getData() {
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                viewModel.getMovies(API_TOKEN, page)
                    .observe(viewLifecycleOwner) { response ->
                        when (response.status) {
                            Status.LOADING -> {
                                activity?.supportFragmentManager?.let {
                                    loadingDialog?.show(
                                        it,
                                        TAG_LOADING
                                    )
                                }
                            }
                            Status.SUCCESS -> {
                                loadingDialog?.dismiss()
                                val result = response.data!!
                                result.movies.let { it1 -> allMoviesList.addAll(it1) }
                                setDataInList(allMoviesList)
                                result.movies.clear()
                            }
                            Status.ERROR -> {
                                loadingDialog?.dismiss()
                                HelperClass.showToast(context, response.message)
                            }
                        }
                    }
            } else {
                HelperClass.showToast(context, getString(R.string.no_internet_connection))
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setDataInList(allMoviesList: java.util.ArrayList<Movie>) {
        if (allMoviesList.isNotEmpty()) {
            binding.rvAllMovies.visibility = View.VISIBLE
            moviesAdapter.setAllMovies(allMoviesList)
            moviesAdapter.notifyDataSetChanged()
        }
    }


    override fun getDetails(movieId: String) {
        val navDirections: NavDirections =
            AllMoviesFragmentDirections.actionAllMoviesFragmentToMovieDetailsFragment(movieId)
        findNavController(requireView()).navigate(navDirections)

    }


}
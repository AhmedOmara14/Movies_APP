package com.task.presentation.movie_details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.illa.task.common.NetworkConnection
import com.squareup.picasso.Picasso
import com.task.R
import com.task.common.Constants
import com.task.common.Constants.BASE_URL_IMAGE
import com.task.common.HelperClass
import com.task.common.LoadingDialog
import com.task.common.Status
import com.task.databinding.FragmentMovieDetailsBinding
import com.task.domain.model.movie_details.Genre
import com.task.presentation.movie_details.adapter.GenreAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private lateinit var movieId: String
    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()
    private lateinit var networkConnection: NetworkConnection
    private var loadingDialog: LoadingDialog? = null
    private lateinit var genreAdapter: GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initMovieAdapter()
        getData()
    }

    private fun init() {
        val args: MovieDetailsFragmentArgs by navArgs()
        movieId = args.movieId
        networkConnection = NetworkConnection(requireContext())
        loadingDialog = LoadingDialog()
        loadingDialog!!.isCancelable = false
    }

    private fun getData() {
        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                viewModel.getMovieDetails(movieId, Constants.API_TOKEN)
                    .observe(viewLifecycleOwner) { response ->
                        when (response.status) {
                            Status.LOADING -> {
                                loadingDialog!!.show(
                                    requireActivity().supportFragmentManager,
                                    Constants.TAG_LOADING
                                )
                            }
                            Status.SUCCESS -> {
                                loadingDialog?.dismiss()
                                val result = response.data!!
                                binding.movie = result
                                Picasso.get().load(BASE_URL_IMAGE + result.poster_path)
                                    .into(binding.ivMovie)
                                setDataInList(result.genres as ArrayList<Genre>)

                            }
                            Status.ERROR -> {
                                loadingDialog?.dismiss()
                                HelperClass.showToast(context, response.message)
                            }
                        }
                    }
            } else
                HelperClass.showToast(context, getString(R.string.no_internet_connection))
        }
    }

    private fun initMovieAdapter() {
        genreAdapter = GenreAdapter()
        val linearLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
        binding.rvAllGenres.layoutManager = linearLayoutManager
        binding.rvAllGenres.adapter = genreAdapter

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun setDataInList(allGenresList: ArrayList<Genre>) {
        if (allGenresList.isNotEmpty()) {
            binding.rvAllGenres.visibility = View.VISIBLE
            genreAdapter.setAllGenres(allGenresList)
            genreAdapter.notifyDataSetChanged()
        }
    }
}
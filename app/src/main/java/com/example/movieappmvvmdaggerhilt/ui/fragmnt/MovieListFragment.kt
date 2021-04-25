package com.example.movieappmvvmdaggerhilt.ui.fragmnt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieappmvvmdaggerhilt.R
import com.example.movieappmvvmdaggerhilt.adapter.TopMovieAdapter
import com.example.movieappmvvmdaggerhilt.app_interface.AdapterOnClick
import com.example.movieappmvvmdaggerhilt.app_interface.SetupView
import com.example.movieappmvvmdaggerhilt.commons.NetworkState
import com.example.movieappmvvmdaggerhilt.data.remote.models.ResultClass
import com.example.movieappmvvmdaggerhilt.databinding.FragmentMovieListBinding
import com.example.movieappmvvmdaggerhilt.ui.view_models.MainViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*

class MovieListFragment(override val TAG: String = "MovieListFragment") : Fragment(), AdapterOnClick<ResultClass>,
    SetupView, View.OnClickListener {

    private lateinit var binding: FragmentMovieListBinding

    lateinit var topMovieAdapter: TopMovieAdapter

    private val vm by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieListBinding.bind(view)
        setupView()
    }

    private fun setGridLayoutManager():GridLayoutManager{
        val gridLayoutManager = GridLayoutManager(requireContext(),3)
        gridLayoutManager.spanSizeLookup = object: GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = topMovieAdapter.getItemViewType(position)
                if (viewType == topMovieAdapter.MOVIE_TYPE) return 1
                else
                    return 3
            }
        }
        return gridLayoutManager
    }

    override fun setupBindings() {
        topMovieAdapter = TopMovieAdapter(requireContext(), this)
        binding.apply {
            recyclerTop.layoutManager = setGridLayoutManager()
            recyclerTop.adapter = topMovieAdapter
            fbtnSearch.setOnClickListener(this@MovieListFragment)
        }


    }

    override fun setupObservers() {
        vm.topMovies.observe(this, Observer {
            topMovieAdapter.submitList(it)
        })

        vm.networkState.observe(this, Observer {
            progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            tvMessage.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
            if (vm.listIsEmpty())
                topMovieAdapter.setNetworkState(it)
        })
    }

    override fun adapterOnClick(item: ResultClass, pos: Int) {
        val action = MovieListFragmentDirections.actionFragmentMoviesListToFragmentMovieDetail(item.id)
        view?.findNavController()?.navigate(action)
    }

    override fun onClick(p0: View?) {
        binding.apply {
            when (p0!!.id){
                fbtnSearch.id -> {
                    val action = MovieListFragmentDirections.actionFragmentMoviesListToFindMovieListFragment()
                    view?.findNavController()?.navigate(action)
                }
            }
        }
    }
}
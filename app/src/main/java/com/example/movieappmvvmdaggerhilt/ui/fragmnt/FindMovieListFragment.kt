package com.example.movieappmvvmdaggerhilt.ui.fragmnt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.movieappmvvmdaggerhilt.R
import com.example.movieappmvvmdaggerhilt.adapter.SearchMovieAdapter
import com.example.movieappmvvmdaggerhilt.app_interface.AdapterOnClick
import com.example.movieappmvvmdaggerhilt.app_interface.SetupView
import com.example.movieappmvvmdaggerhilt.commons.afterTextChanged
import com.example.movieappmvvmdaggerhilt.commons.linear
import com.example.movieappmvvmdaggerhilt.data.remote.models.ResultClass
import com.example.movieappmvvmdaggerhilt.databinding.FragmentSearchMovieBinding
import com.example.movieappmvvmdaggerhilt.ui.view_models.MainViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*

class FindMovieListFragment(override val TAG: String = "FindMovieListFragment") : Fragment(), AdapterOnClick<ResultClass>,
    SetupView, View.OnClickListener {

    private lateinit var binding: FragmentSearchMovieBinding

    lateinit var searchMovieAdapter: SearchMovieAdapter

    private val vm by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchMovieBinding.bind(view)
        setupView()
    }


    override fun setupBindings() {
        binding.apply {
            rvSearchmovie.layoutManager = linear(requireContext())
            itSearchmovie.afterTextChanged {
                vm.searchMovie(it).observe(this@FindMovieListFragment, Observer { itResult ->
                    searchMovieAdapter = SearchMovieAdapter(itResult, this@FindMovieListFragment)
                    rvSearchmovie.adapter = searchMovieAdapter
                })
            }
        }
        binding.btnBack.setOnClickListener(this)

    }

    override fun setupObservers() {


    }

    override fun adapterOnClick(item: ResultClass, pos: Int) {
        val action = FindMovieListFragmentDirections.actionFindMovieListFragmentToFragmentMovieDetail(item.id)
        view?.findNavController()?.navigate(action)
    }

    override fun onClick(p0: View?) {
        binding.apply {
            when (p0!!.id){
                btnBack.id -> {
                    view?.findNavController()?.popBackStack()
                }
            }
        }
    }
}
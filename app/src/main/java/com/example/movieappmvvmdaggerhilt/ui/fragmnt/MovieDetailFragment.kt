package com.example.movieappmvvmdaggerhilt.ui.fragmnt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.movieappmvvmdaggerhilt.R
import com.example.movieappmvvmdaggerhilt.app_interface.SetupView
import com.example.movieappmvvmdaggerhilt.commons.Const
import com.example.movieappmvvmdaggerhilt.commons.NetworkState
import com.example.movieappmvvmdaggerhilt.data.remote.models.SingleMovieClass
import com.example.movieappmvvmdaggerhilt.databinding.FragmentMovieDetailsBinding
import com.example.movieappmvvmdaggerhilt.ui.view_models.MainViewModel
import kotlinx.android.synthetic.main.fragment_movie_list.*
import kotlinx.android.synthetic.main.item_topmovies.view.*

class MovieDetailFragment(override val TAG: String = "") : Fragment(), View.OnClickListener, SetupView {

    private lateinit var binding: FragmentMovieDetailsBinding
    lateinit var navController: NavController
    private val args: MovieDetailFragmentArgs by navArgs()

    private val vm by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailsBinding.bind(view)
        navController = Navigation.findNavController(view)
        setupView()
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

    override fun setupBindings() {
        binding.apply {
            btnBack.setOnClickListener(this@MovieDetailFragment)
        }
    }

    override fun setupObservers() {
        vm.movieDetail(args.movieId).observe(this,  Observer {
            binding.apply {
                val movieposter = Const.BASE_API_URL + it?.posterPath
                Glide.with(this@MovieDetailFragment)
                    .load(movieposter)
                    .into(ivMovie)
                tvMovieTitle.text = it.title
                tvInfo.text = it.overview
                tvTagLine.text = it.tagline
            }
        })
    }
}
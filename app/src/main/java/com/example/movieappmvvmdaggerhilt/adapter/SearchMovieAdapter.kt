package com.example.movieappmvvmdaggerhilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieappmvvmdaggerhilt.app_interface.AdapterOnClick
import com.example.movieappmvvmdaggerhilt.commons.Const
import com.example.movieappmvvmdaggerhilt.data.remote.models.ResultClass
import com.example.movieappmvvmdaggerhilt.databinding.ItemSearchmovieBinding
import kotlinx.android.synthetic.main.item_topmovies.view.*

class SearchMovieAdapter (private val items: List<ResultClass>, private val adapterOnClick: AdapterOnClick<ResultClass>? = null) : RecyclerView.Adapter<SearchMovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder  = ViewHolder(
        ItemSearchmovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position],position, adapterOnClick)
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private val itemBinding: ItemSearchmovieBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: ResultClass, position: Int, adapterOnClick: AdapterOnClick<ResultClass>? = null) {
            itemBinding.apply {
                tvTitle.text = item.title
                val movieposter = Const.BASE_API_URL + item?.posterPath
                Glide.with(itemBinding.root)
                    .load(movieposter)
                    .into(imagePost)
                if (adapterOnClick!=null)
                    cardTop.setOnClickListener { adapterOnClick.adapterOnClick(item, position) }
            }
        }
    }
}

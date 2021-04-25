package com.example.movieappmvvmdaggerhilt.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieappmvvmdaggerhilt.R
import com.example.movieappmvvmdaggerhilt.app_interface.AdapterOnClick
import com.example.movieappmvvmdaggerhilt.commons.Const.Companion.BASE_API_URL
import com.example.movieappmvvmdaggerhilt.commons.NetworkState
import com.example.movieappmvvmdaggerhilt.data.remote.models.ResultClass
import kotlinx.android.synthetic.main.item_networkstate.view.*
import kotlinx.android.synthetic.main.item_topmovies.view.*

class TopMovieAdapter(private val context: Context, private val adapter: AdapterOnClick<ResultClass>): PagedListAdapter<ResultClass, RecyclerView.ViewHolder>(TopMovieDiffCallBack()) {


    val MOVIE_TYPE = 1
    val NETWORK_TYPE = 2

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType==MOVIE_TYPE)
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_topmovies, parent, false))
        else
            return NetworkStateViewHolder(LayoutInflater.from(context).inflate(R.layout.item_networkstate, parent, false))
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1 ){
            NETWORK_TYPE
        }else
            MOVIE_TYPE
    }


    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position)==MOVIE_TYPE)
            (holder as ViewHolder).bind(getItem(position), adapter)
        else
            (holder as NetworkStateViewHolder).bin(networkState)
    }

    private fun hasExtraRow():Boolean{
        return networkState!=null && networkState!= NetworkState.LOADED
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: ResultClass?, adapter: AdapterOnClick<ResultClass>) = with(itemView){
            tvTitle.text   = item?.title
            tvReview.text  = item?.overview
            tvReting.text = item?.voteCount.toString()

            val movieposter = BASE_API_URL + item?.posterPath
            Glide.with(this)
                .load(movieposter)
                .into(imagePost)

            cardTop.setOnClickListener{adapter.adapterOnClick(item!!)}
        }
    }

    class NetworkStateViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bin(item:NetworkState?) = with(itemView){
            if (item == NetworkState.LOADING)
                itemView.progressBar2.visibility = View.VISIBLE
            else
                itemView.progressBar2.visibility = View.GONE

            if (item == NetworkState.ERROR || item == NetworkState.END_OF_LIST){
                itemView.tvError.visibility = View.VISIBLE
                itemView.tvError.text = item.msg
            }else
                itemView.tvError.visibility = View.GONE
        }
    }

    fun setNetworkState(networkState: NetworkState){
        val previousState: NetworkState? = this.networkState
        val hadExtraRow:Boolean = hasExtraRow()
        this.networkState = networkState
        val hasExtraRow:Boolean = hasExtraRow()

        if (hadExtraRow != hasExtraRow){
            if (hadExtraRow){
                notifyItemRemoved(super.getItemCount())
            }else{
                notifyItemInserted(super.getItemCount())
            }
        }
    }

    class TopMovieDiffCallBack: DiffUtil.ItemCallback<ResultClass>(){
        override fun areItemsTheSame(oldItem: ResultClass, newItem: ResultClass): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ResultClass, newItem: ResultClass): Boolean  = oldItem == newItem

    }

}


package com.hadirahimi.movieapppowerfull.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hadirahimi.movieapppowerfull.databinding.ItemHomeMoviesLastBinding
import com.hadirahimi.movieapppowerfull.db.MovieEntity
import javax.inject.Inject

class AdapterFavorite @Inject constructor() : RecyclerView.Adapter<AdapterFavorite.ViewHolder>()
{
    private lateinit var binding : ItemHomeMoviesLastBinding
    private var moviesList = emptyList<MovieEntity>()
    
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)
    {
        @SuppressLint("SetTextI18n")
        fun bindItems(movie : MovieEntity)
        {
            binding.apply {
                tvMovieName.text = movie.title
                tvMovieCountry.text = movie.country
                tvMovieRate.text = movie.rate
                tvMovieYear.text = movie.year
                
                ivMoviePoster.load(movie.poster) {
                    crossfade(true)
                    crossfade(1000)
                }
                //click
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(movie)
                    }
                }
            }
        }
    }
    
    private var onItemClickListener : ((MovieEntity) -> Unit?)? = null
    
    fun setOnItemClickListener(listener : (MovieEntity) -> Unit)
    {
        onItemClickListener = listener
    }
    
    override fun onCreateViewHolder(
        parent : ViewGroup , viewType : Int
    ) : AdapterFavorite.ViewHolder
    {
        binding =
            ItemHomeMoviesLastBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder()
    }
    
    override fun onBindViewHolder(holder : AdapterFavorite.ViewHolder , position : Int)
    {
        holder.bindItems(moviesList[position])
        holder.setIsRecyclable(false)
    }
    
    override fun getItemCount() : Int
    {
        return moviesList.size
    }
    
    fun setData(data : List<MovieEntity>)
    {
        val movieDiffUtil = MoviesDiffUtils(moviesList , data)
        val diffUtils = DiffUtil.calculateDiff(movieDiffUtil)
        moviesList = data
        
        diffUtils.dispatchUpdatesTo(this)
    }
    
    class MoviesDiffUtils(
        private val oldItem : List<MovieEntity> , private val newItem : List<MovieEntity>
    ) : DiffUtil.Callback()
    {
        override fun getOldListSize() : Int
        {
            return oldItem.size
        }
        
        override fun getNewListSize() : Int
        {
            return newItem.size
        }
        
        private var onItemClickListener : ((MovieEntity) -> Unit?)? = null
        
        fun setOnItemClickListener(listener : (MovieEntity) -> Unit)
        {
            onItemClickListener = listener
        }
        
        override fun areItemsTheSame(oldItemPosition : Int , newItemPosition : Int) : Boolean
        {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
        
        //method paiin zamani call mishe ke method bala call beshe va true bashe
        override fun areContentsTheSame(oldItemPosition : Int , newItemPosition : Int) : Boolean
        {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
        
    }
    
    
}
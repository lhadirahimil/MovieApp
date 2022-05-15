package com.hadirahimi.movieapppowerfull.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hadirahimi.movieapppowerfull.databinding.ItemHomeTopMoviesBinding
import com.hadirahimi.movieapppowerfull.models.home.ResponseMoviesList
import javax.inject.Inject

class AdapterTopMovie @Inject constructor() : RecyclerView.Adapter<AdapterTopMovie.ViewHolder>()
{
    private lateinit var binding : ItemHomeTopMoviesBinding
    
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)
    {
        @SuppressLint("SetTextI18n")
        fun setData(movie : ResponseMoviesList.Data)
        {
            binding.apply {
                tvMovieTitle.text = movie.title
                tvMovieInfo.text = "${movie.imdbRating} | ${movie.country} | ${movie.year}"
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
    
    
    private var onItemClickListener : ((ResponseMoviesList.Data) -> Unit?)? = null
    
    fun setOnItemClickListener(listener : (ResponseMoviesList.Data) -> Unit)
    {
        onItemClickListener = listener
    }
    
    override fun onCreateViewHolder(
        parent : ViewGroup , viewType : Int
    ) : AdapterTopMovie.ViewHolder
    {
        binding =
            ItemHomeTopMoviesBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder()
    }
    
    override fun onBindViewHolder(holder : AdapterTopMovie.ViewHolder , position : Int)
    {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
        
       
    }
    
    override fun getItemCount() : Int
    {
        return if (differ.currentList.size > 5) 5
        else differ.currentList.size
    }
    
    private val differCallback = object : DiffUtil.ItemCallback<ResponseMoviesList.Data>()
    {
        override fun areItemsTheSame(
            oldItem : ResponseMoviesList.Data , newItem : ResponseMoviesList.Data
        ) : Boolean
        {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(
            oldItem : ResponseMoviesList.Data , newItem : ResponseMoviesList.Data
        ) : Boolean
        {
            return oldItem == newItem
        }
    }
    
     val differ = AsyncListDiffer(this , differCallback)
    
}
package com.hadirahimi.movieapppowerfull.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hadirahimi.movieapppowerfull.databinding.ItemHomeGenreListBinding
import com.hadirahimi.movieapppowerfull.databinding.ItemHomeTopMoviesBinding
import com.hadirahimi.movieapppowerfull.models.home.ResponseGenreList
import com.hadirahimi.movieapppowerfull.models.home.ResponseMoviesList
import javax.inject.Inject

class AdapterGenre @Inject constructor() : RecyclerView.Adapter<AdapterGenre.ViewHolder>()
{
    private lateinit var binding : ItemHomeGenreListBinding
    
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)
    {
        @SuppressLint("SetTextI18n")
        fun setData(genre : ResponseGenreList.ResponseGenreListItem)
        {
            binding.apply {
                tvName.text = genre.name
            }
        }
    }
    
    override fun onCreateViewHolder(
        parent : ViewGroup , viewType : Int
    ) : AdapterGenre.ViewHolder
    {
        binding =
            ItemHomeGenreListBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder()
    }
    
    override fun onBindViewHolder(holder : AdapterGenre.ViewHolder , position : Int)
    {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }
    
    override fun getItemCount() : Int = differ.currentList.size
    
    private val differCallback = object : DiffUtil.ItemCallback<ResponseGenreList.ResponseGenreListItem>()
    {
        override fun areItemsTheSame(
            oldItem : ResponseGenreList.ResponseGenreListItem , newItem : ResponseGenreList.ResponseGenreListItem
        ) : Boolean
        {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(
            oldItem : ResponseGenreList.ResponseGenreListItem , newItem : ResponseGenreList.ResponseGenreListItem
        ) : Boolean
        {
            return oldItem == newItem
        }
    }
    
     val differ = AsyncListDiffer(this , differCallback)
    
}
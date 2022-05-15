package com.hadirahimi.movieapppowerfull.ui.datail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.hadirahimi.movieapppowerfull.databinding.ItemDetailImageBinding
import com.hadirahimi.movieapppowerfull.models.detail.ResponseMovieDetail
import javax.inject.Inject

class AdapterMovieImage @Inject constructor() : RecyclerView.Adapter<AdapterMovieImage.ViewHolder>()
{
    private lateinit var binding : ItemDetailImageBinding
    
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root)
    {
        @SuppressLint("SetTextI18n")
        fun setData(image : String)
        {
            binding.apply {
                itemImage.load(image){
                    crossfade(true)
                    crossfade(1000)
                }
            }
        }
    }
    
    override fun onCreateViewHolder(
        parent : ViewGroup , viewType : Int
    ) : AdapterMovieImage.ViewHolder
    {
        binding =
            ItemDetailImageBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ViewHolder()
    }
    
    override fun onBindViewHolder(holder : AdapterMovieImage.ViewHolder , position : Int)
    {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }
    
    override fun getItemCount() : Int = differ.currentList.size
    
    private val differCallback = object : DiffUtil.ItemCallback<String>()
    {
        override fun areItemsTheSame(
            oldItem : String , newItem : String
        ) : Boolean
        {
            return oldItem == newItem
        }
        
        override fun areContentsTheSame(
            oldItem : String , newItem : String
        ) : Boolean
        {
            return oldItem == newItem
        }
    }
    
    val differ = AsyncListDiffer(this , differCallback)
    
}
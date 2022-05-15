package com.hadirahimi.movieapppowerfull.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadirahimi.movieapppowerfull.R
import com.hadirahimi.movieapppowerfull.databinding.FragmentFavoriteBinding
import com.hadirahimi.movieapppowerfull.ui.home.HomeFragmentDirections
import com.hadirahimi.movieapppowerfull.utils.initRecycler
import com.hadirahimi.movieapppowerfull.utils.showInvisible
import com.hadirahimi.movieapppowerfull.viewModel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment()
{
    //binding
    private lateinit var binding:FragmentFavoriteBinding
    
    //other
     val viewModel : FavoriteViewModel by viewModels()
    
    @Inject
     lateinit var adapterFavorite:AdapterFavorite
    
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?
    ) : View
    {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    
    
    
    override fun onViewCreated(view : View , savedInstanceState : Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)
        //init views
        binding.apply {
            //load
            viewModel.loadFavoriteMovie()
            //empty checker
            viewModel.empty.observe(viewLifecycleOwner){
                if (it)
                {
                    emptyItemLayout.showInvisible(true)
                    recyclerMovies.showInvisible(false)
                }
                else
                {
                    emptyItemLayout.showInvisible(false)
                    recyclerMovies.showInvisible(true)
                }
            }
            //list Favorite
            viewModel.favoriteList.observe(viewLifecycleOwner){
                adapterFavorite.setData(it)
                recyclerMovies.initRecycler(LinearLayoutManager(requireContext()),adapterFavorite)
            }
            //click item
            adapterFavorite.setOnItemClickListener {
                val direction = FavoriteFragmentDirections.actionToDetail(it.id)
                findNavController().navigate(direction)
            }
            
        }
    }
    
}
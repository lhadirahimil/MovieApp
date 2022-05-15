package com.hadirahimi.movieapppowerfull.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.hadirahimi.movieapppowerfull.databinding.FragmentHomeBinding
import com.hadirahimi.movieapppowerfull.ui.home.adapters.AdapterGenre
import com.hadirahimi.movieapppowerfull.ui.home.adapters.AdapterLastMovie
import com.hadirahimi.movieapppowerfull.ui.home.adapters.AdapterTopMovie
import com.hadirahimi.movieapppowerfull.utils.initRecycler
import com.hadirahimi.movieapppowerfull.utils.showInvisible
import com.hadirahimi.movieapppowerfull.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment()
{
    
    //binding 
    private lateinit var binding : FragmentHomeBinding
    
    
    //adapter
    @Inject
    lateinit var adapterTopMovie : AdapterTopMovie
    
    @Inject
    lateinit var adapterGenre : AdapterGenre
    
    @Inject
    lateinit var adapterLastMovies : AdapterLastMovie
    
    //other
    private val viewModel : HomeViewModel by viewModels()
    private val pagerHelper : PagerSnapHelper by lazy { PagerSnapHelper() }
    
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        //call Api
        viewModel.getTopMovies(3)
        viewModel.genreList()
        viewModel.lastMovies()
    }
    
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?
    ) : View
    {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater , container , false)
        return binding.root
    }
    
    override fun onViewCreated(view : View , savedInstanceState : Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)
        //init views
        binding.apply {
            //get Top Movies
            viewModel.topMovies.observe(viewLifecycleOwner) { topMovies ->
                adapterTopMovie.differ.submitList(topMovies.data)
                //recyclerView
                recyclerTopMovies.initRecycler(
                    LinearLayoutManager(
                        requireContext() ,
                        LinearLayoutManager.HORIZONTAL ,
                        false
                    ) , adapterTopMovie
                )
            }
            //indicator
            pagerHelper.attachToRecyclerView(recyclerTopMovies)
            topMoviesIndicator.attachToRecyclerView(recyclerTopMovies , pagerHelper)
            
            //get genres
            viewModel.genresList.observe(viewLifecycleOwner) { genres ->
                adapterGenre.differ.submitList(genres)
                recyclerGenre.initRecycler(
                    LinearLayoutManager(
                        requireContext() ,
                        LinearLayoutManager.HORIZONTAL ,
                        false
                    ) , adapterGenre
                )
            }
            
            //get last movies
            viewModel.lastMovies.observe(viewLifecycleOwner) { lastMovies ->
                adapterLastMovies.setData(lastMovies.data)
                recyclerLastMovies.initRecycler(
                    LinearLayoutManager(requireContext()) ,
                    adapterLastMovies
                )
            }
            //click
            adapterLastMovies.setOnItemClickListener {
                val direction = HomeFragmentDirections.actionToDetail(it.id !!.toInt())
                findNavController().navigate(direction)
            }
            adapterTopMovie.setOnItemClickListener {
                val direction = HomeFragmentDirections.actionToDetail(it.id !!.toInt())
                findNavController().navigate(direction)
            }
            //loading progress
            viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading)
                {
                    moviesScrollLayout.showInvisible(false)
                    loadingProgress.showInvisible(true)
                }
                else
                {
                    moviesScrollLayout.showInvisible(true)
                    loadingProgress.showInvisible(false)
                }
            }
        }
        
    }
}
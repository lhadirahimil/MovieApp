package com.hadirahimi.movieapppowerfull.ui.datail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.hadirahimi.movieapppowerfull.R
import com.hadirahimi.movieapppowerfull.databinding.FragmentDatailBinding
import com.hadirahimi.movieapppowerfull.db.MovieEntity
import com.hadirahimi.movieapppowerfull.models.detail.ResponseMovieDetail
import com.hadirahimi.movieapppowerfull.utils.initRecycler
import com.hadirahimi.movieapppowerfull.utils.showInvisible
import com.hadirahimi.movieapppowerfull.viewModel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DatailFragment : Fragment()
{
    //binding
    private lateinit var binding : FragmentDatailBinding
    
    //other
    private val viewModel : DetailViewModel by viewModels()
    
    private var movieId = 0
    private val args : DatailFragmentArgs by navArgs()
    
    
    @Inject
    lateinit var adapterMovieImage : AdapterMovieImage
    
    @Inject
    lateinit var entity : MovieEntity
    
    
    override fun onCreate(savedInstanceState : Bundle?)
    {
        super.onCreate(savedInstanceState)
        //get data
        movieId = args.movieID
    }
    
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?
    ) : View
    {
        // Inflate the layout for this fragment
        binding = FragmentDatailBinding.inflate(layoutInflater , container , false)
        return binding.root
    }
    
    override fun onViewCreated(view : View , savedInstanceState : Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)
        //call info
        if (movieId > 0) viewModel.movieInfo(movieId)
        //init views
        binding.apply {
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it)
                {
                    
                    loadingProgress.showInvisible(true)
                    detailScrollView.showInvisible(false)
                }
                else
                {
                    loadingProgress.showInvisible(false)
                    detailScrollView.showInvisible(true)
                }
            }
            viewModel.movieInfo.observe(viewLifecycleOwner) { response ->
                pushToView(response)
                //init recyclerview images
                adapterMovieImage.differ.submitList(response.images)
                recyclerImages.initRecycler(
                    LinearLayoutManager(
                        requireContext() , LinearLayoutManager.HORIZONTAL , false
                    ) , adapterMovieImage
                )
                
                //fav Click
                ivFavorite.setOnClickListener {
                    entity.id = movieId
                    entity.poster = response.poster.toString()
                    entity.title = response.title.toString()
                    entity.rate = response.imdbRating.toString()
                    entity.country = response.country.toString()
                    entity.year = response.year.toString()
                    
                    viewModel.favoriteMovie(movieId , entity)
                }
                
            }
            //default fav icon Color
            lifecycleScope.launchWhenCreated {
                if (viewModel.exists(movieId))
                {
                    ivFavorite.setColorFilter(
                        ContextCompat.getColor(
                            requireContext() ,
                            R.color.scarlet
                        )
                    )
                }
                else
                {
                    ivFavorite.setColorFilter(
                        ContextCompat.getColor(
                            requireContext() ,
                            R.color.philippineSilver
                        )
                    )
                }
            }
            //change fav state with click
            viewModel.isFavorite.observe(viewLifecycleOwner) {
                if (it)
                {
                    ivFavorite.setColorFilter(
                        ContextCompat.getColor(
                            requireContext() ,
                            R.color.scarlet
                        )
                    )
                }
                else
                {
                    ivFavorite.setColorFilter(
                        ContextCompat.getColor(
                            requireContext() ,
                            R.color.philippineSilver
                        )
                    )
                }
            }
            
            //Back
            ivBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
    
    private fun pushToView(it : ResponseMovieDetail)
    {
        binding.apply {
            tvMovieName.text = it.title
            tvMovieActorsInfo.text = it.actors
            tvMovieRate.text = it.imdbRating
            tvMovieTime.text = it.runtime
            tvMovieDate.text = it.released
            tvMovieSummeryInfo.text = it.plot
            ivMainPoster.load(it.poster) {
                crossfade(true)
                crossfade(800)
            }
            ivPosterBackImage.load(it.poster)
        }
    }
}
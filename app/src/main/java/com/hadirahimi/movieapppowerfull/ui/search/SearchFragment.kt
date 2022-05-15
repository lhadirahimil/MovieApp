package com.hadirahimi.movieapppowerfull.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hadirahimi.movieapppowerfull.databinding.FragmentSearchBinding
import com.hadirahimi.movieapppowerfull.ui.favorite.FavoriteFragmentDirections
import com.hadirahimi.movieapppowerfull.ui.home.adapters.AdapterLastMovie
import com.hadirahimi.movieapppowerfull.utils.initRecycler
import com.hadirahimi.movieapppowerfull.utils.showInvisible
import com.hadirahimi.movieapppowerfull.viewModel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment()
{
    //binding
    private lateinit var binding : FragmentSearchBinding
    
    //adapter
    @Inject
    lateinit var searchAdapter : AdapterLastMovie
    
    //other
    private val viewModel : SearchViewModel by viewModels()
    
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?
    ) : View
    {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(layoutInflater , container , false)
        return binding.root
    }
    
    override fun onViewCreated(view : View , savedInstanceState : Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)
        //Init Views
        binding.apply {
            //search
            etSearch.addTextChangedListener { text ->
                val search = text.toString()
                if (search.isNotEmpty())
                {
                    viewModel.searchByName(search)
                }
            }
            //get movies list
            viewModel.searchResult.observe(viewLifecycleOwner) {
                searchAdapter.setData(it.data)
                recyclerMovies.initRecycler(LinearLayoutManager(requireContext()) , searchAdapter)
            }
            //loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it)
                {
                    searchLoading.showInvisible(true)
                }
                else
                {
                    searchLoading.showInvisible(false)
                }
            }
            //empty item
            viewModel.empty.observe(viewLifecycleOwner){
                if (it)
                {
                    emptyItemLayout.showInvisible(true)
                    recyclerMovies.showInvisible(false)
                }else {
                    emptyItemLayout.showInvisible(false)
                    recyclerMovies.showInvisible(true)
                }
            }
    
            //click item
            searchAdapter.setOnItemClickListener {
                val direction = SearchFragmentDirections.actionToDetail(it.id!!.toInt())
                findNavController().navigate(direction)
            }
        }
    }
}
package com.hadirahimi.movieapppowerfull.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.hadirahimi.movieapppowerfull.R
import com.hadirahimi.movieapppowerfull.databinding.FragmentSplashBinding
import com.hadirahimi.movieapppowerfull.utils.StoreUserData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment()
{
    private lateinit var binding : FragmentSplashBinding
    
    @Inject
    lateinit var storeUserData : StoreUserData
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?
    ) : View
    {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater , container , false)
        return binding.root
    }
    
    override fun onViewCreated(view : View , savedInstanceState : Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)
        //set delay
        lifecycle.coroutineScope.launchWhenCreated {
            delay(2000)
            //check user token
            storeUserData.getUserToken().collect{
                if (it.isEmpty())
                {
                    //user not registered
                    findNavController().navigate(R.id.actionSplashToRegister)
                }else
                {
                    //user is registered
                    findNavController().navigate(R.id.actionToHome)
                }
            }
        }
        
    }
}
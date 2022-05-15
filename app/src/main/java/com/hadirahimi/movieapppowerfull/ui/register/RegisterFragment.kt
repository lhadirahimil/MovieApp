package com.hadirahimi.movieapppowerfull.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.google.android.material.snackbar.Snackbar
import com.hadirahimi.movieapppowerfull.R
import com.hadirahimi.movieapppowerfull.databinding.FragmentRegisterBinding
import com.hadirahimi.movieapppowerfull.models.register.BodyRegister
import com.hadirahimi.movieapppowerfull.utils.StoreUserData
import com.hadirahimi.movieapppowerfull.utils.showInvisible
import com.hadirahimi.movieapppowerfull.viewModel.registerViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment()
{
    //binding
    private lateinit var binding : FragmentRegisterBinding
    @Inject
    lateinit var userData : StoreUserData
    //other 
    private  val viewModel : registerViewModel by viewModels()
    
    @Inject
    lateinit var body : BodyRegister
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? , savedInstanceState : Bundle?
    ) : View
    {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    
    override fun onViewCreated(view : View , savedInstanceState : Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)
        //init Views
        binding.apply {
            //click
            btnSubmit.setOnClickListener { view ->
                val name = etName.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty())
                {
                    body.name = name
                    body.email = email
                    body.password = password
                }else
                {
                    Snackbar.make(view,getString(R.string.fillAllFields),Snackbar.LENGTH_SHORT).show()
                }
                //send data
                viewModel.registerUser(body)
                //loading
                viewModel.loading.observe(viewLifecycleOwner){isShown->
                    if (isShown)
                    {
                        submitLoading.showInvisible(true)
                        btnSubmit.showInvisible(false)
                    }
                    else {
                        submitLoading.showInvisible(false)
                        btnSubmit.showInvisible(true)
                    }
                }
                //register
                viewModel.registerUser.observe(viewLifecycleOwner){ response->
                    lifecycle.coroutineScope.launchWhenCreated {
                        userData.saveUserToken(response.name.toString())
                    }
                }
            }
        }
    }
   
}
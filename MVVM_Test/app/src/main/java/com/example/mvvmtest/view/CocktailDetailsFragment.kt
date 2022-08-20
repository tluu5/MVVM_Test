package com.example.mvvmtest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmtest.R
import com.example.mvvmtest.databinding.FragmentCocktailDetailsBinding
import com.example.mvvmtest.databinding.LayoutIngredientsBinding
import com.example.mvvmtest.model.CocktailDetails
import com.example.mvvmtest.viewmodel.CocktailViewModel
import com.squareup.picasso.Picasso

private const val TAG = "CocktailDetailsFragment"

class CocktailDetailsFragment: Fragment() {

    companion object{
        const val DETAILS_ID_DRINK = "DETAILS_ID_DRINK"

        fun newInstance(idDrink: String) =
            CocktailDetailsFragment().apply {
                arguments= Bundle().apply {
                    putString(DETAILS_ID_DRINK, idDrink)
                }
            }
    }

    private lateinit var binding: FragmentCocktailDetailsBinding
    private lateinit var mergeBinding: LayoutIngredientsBinding
    private val viewModel: CocktailViewModel by lazy {
        ViewModelProvider(this)[CocktailViewModel::class.java]
    }
    // todo in the onCreateView get the arguments
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCocktailDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        mergeBinding = LayoutIngredientsBinding.bind( binding.root )

        arguments?.getString(DETAILS_ID_DRINK)?.let {
            getCocktailDetails(it)
        }
        initObservables()
        return binding.root
    }

    private fun initObservables() {
        viewModel.cocktailDetails.observe(viewLifecycleOwner){
            updateView(it)
        }
    }

    private fun updateView(data: CocktailDetails) {
        Log.d(TAG, "updateView: $data")
        mergeBinding.ingredientOne.text = data.drinks[0].strIngredient1
        mergeBinding.ingredientTwo.text = data.drinks[0].strIngredient2
        mergeBinding.ingredientThree.text = data.drinks[0].strIngredient3
        mergeBinding.ingredientForth.text = data.drinks[0].strIngredient4
        binding.cocktailNameDetails.text = data.drinks[0].strDrink
        Picasso.get()
            .load(data.drinks[0].strDrinkThumb)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.cocktailImageDetails)
    }

    private fun getCocktailDetails(idDrink: String) {
        viewModel.getCocktailDetails(idDrink)
    }
    // todo from the arguments invoke viewModel.getCocktailDetails()
    // todo initObservables and viewModel.cocktailDetails.observe...
    // todo display the data binding.tv.....
}
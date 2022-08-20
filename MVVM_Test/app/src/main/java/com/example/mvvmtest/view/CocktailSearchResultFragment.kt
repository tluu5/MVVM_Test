package com.example.mvvmtest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmtest.R
import com.example.mvvmtest.databinding.FragmentCocktailResultBinding
import com.example.mvvmtest.model.CocktailDetails
import com.example.mvvmtest.model.CocktailSearch
import com.example.mvvmtest.view.adapter.CocktailAdapter
import com.example.mvvmtest.viewmodel.CocktailViewModel

private const val TAG = "CocktailSearchResultFra"

class CocktailSearchResultFragment: Fragment() {

    private lateinit var binding: FragmentCocktailResultBinding
    private val viewModel: CocktailViewModel by lazy {
        ViewModelProvider(this)[CocktailViewModel::class.java]
    }
    private lateinit var adapter: CocktailAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCocktailResultBinding.inflate(
            inflater,
            container,
            false
        )
        initObservables()
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.cocktailSearch.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return query?.let {
                        viewModel.searchCocktail(it)
                        true
                    } ?: false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            }
        )
        adapter = CocktailAdapter {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            requireActivity().displayCocktailDetails(it)
        }

        binding.searchList.adapter = adapter
        binding.searchList.layoutManager = LinearLayoutManager(context)
    }

    private fun initObservables() {
        viewModel.cocktailSearchResult.observe(viewLifecycleOwner,
            Observer {
                updateAdapter(it)
            })
    }

    private fun updateAdapter(dataSet: CocktailSearch) {
        Log.d(TAG, "updateAdapter: $dataSet")
        adapter.submitList(dataSet.drinks)
    }

}

private fun FragmentActivity.displayCocktailDetails(idDrink: String) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.cocktail_container, CocktailDetailsFragment.newInstance(idDrink))
        .addToBackStack(null)
        .commit()
}

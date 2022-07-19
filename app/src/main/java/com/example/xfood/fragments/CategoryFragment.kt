package com.example.xfood.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.xfood.MainActivity
import com.example.xfood.R
import com.example.xfood.adapters.CategoriesAdapter
import com.example.xfood.databinding.FragmentCategoryBinding
import com.example.xfood.viewModel.HomeViewModel

class CategoryFragment : Fragment() {

    private lateinit var binding: FragmentCategoryBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var categoryFragmentAdapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRecyclerView()
        observeCategory()
    }

    private fun observeCategory() {
        viewModel.observeCategoriesItemLiveData().observe(viewLifecycleOwner){
            categoryFragmentAdapter.setCategoriesMeal(it)
        }
    }

    private fun prepareRecyclerView() {
          categoryFragmentAdapter = CategoriesAdapter()
         binding.rvCategory.apply {
             layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
             adapter = categoryFragmentAdapter
         }
    }
}
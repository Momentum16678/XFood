package com.example.xfood

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bumptech.glide.Glide
import com.example.xfood.databinding.ActivityMealBinding
import com.example.xfood.db.MealDatabase
import com.example.xfood.fragments.HomeFragment
import com.example.xfood.model.Meal
import com.example.xfood.viewModel.MealViewModel
import com.example.xfood.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId: String
    private lateinit var mealThumb: String
    private lateinit var mealName: String
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //mealMvvm = ViewModelProviders.of(this)[MealViewModel::class.java]

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        getMealInfoFromIntent()
        setInfoIntoViews()
        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observerMealLiveData()
        onYoutubeImageClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.favoriteActionButton.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeImageClick() {
        binding.ivYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }
    private var mealToSave: Meal?=null
    private fun observerMealLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this
        ) {
            onResponseCase()

            mealToSave = it

            binding.tvCategory2.text = "Category : ${it!!.strCategory}"
            binding.tvCategory2.text = "Area : ${it!!.strArea}"
            binding.tvInstructionBody.text = it.strInstructions

            youtubeLink = it.strYoutube.toString()
        }
    }

    private fun setInfoIntoViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.ivMealDetail)
        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealInfoFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.favoriteActionButton.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvCategory2.visibility = View.INVISIBLE
        binding.ivYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.favoriteActionButton.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvCategory2.visibility = View.INVISIBLE
        binding.ivYoutube.visibility = View.INVISIBLE
    }

}
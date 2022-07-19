package com.example.xfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.xfood.databinding.MealItemBinding
import com.example.xfood.model.PopularCategoryList
import com.example.xfood.model.PopularCategoryMeals

class CategoriesMealAdapter : RecyclerView.Adapter<CategoriesMealAdapter.CategoriesMealViewHolder>() {

    private var mealsList = ArrayList<PopularCategoryMeals>()

    fun setCategoryMealList(mealsList:List<PopularCategoryMeals>){
        this.mealsList = mealsList as ArrayList<PopularCategoryMeals>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesMealViewHolder {
        return CategoriesMealViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: CategoriesMealViewHolder, position: Int) {
         Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imageMeal)
         holder.binding.tvMealName.text = mealsList[position].strMeal
    }

    override fun getItemCount(): Int {
       return mealsList.size
    }

    inner class CategoriesMealViewHolder(var binding: MealItemBinding): RecyclerView.ViewHolder(binding.root)


}
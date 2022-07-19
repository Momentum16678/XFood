package com.example.xfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.xfood.databinding.CategoryItemBinding
import com.example.xfood.model.Category

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    var onItemClick : ((Category) -> Unit)? = null
    class CategoriesViewHolder(var binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    private var categoriesList = ArrayList<Category>()
    fun setCategoriesMeal(CategoryList: List<Category>){
        this.categoriesList = CategoryList as ArrayList<Category>
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesViewHolder {
       return CategoriesViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
       Glide.with(holder.itemView).load(categoriesList[position].strCategoryThumb).into(holder.binding.ivImgCategory)
       holder.binding.tvCategoryName.text = categoriesList[position].strCategory

       holder.itemView.setOnClickListener {
           onItemClick!!.invoke(categoriesList[position])
       }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

}





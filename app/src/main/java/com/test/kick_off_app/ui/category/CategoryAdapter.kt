package com.test.kick_off_app.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.kick_off_app.R
import com.test.kick_off_app.data.CategoryEnum
import com.test.kick_off_app.data.LocationEnum
import com.test.kick_off_app.data.getCategory
import com.test.kick_off_app.data.getLocation
import com.test.kick_off_app.databinding.RvLocationItemBinding

class CategoryAdapter(private val onClick: (Int) -> Unit) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categories = arrayOf<Boolean>()

    inner class CategoryViewHolder(private val binding: RvLocationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            var categoryText = getCategory(CategoryEnum.values()[position])
            //categoryText = if (categoryText.length > 2) categoryText.dropLast(1) else categoryText
            binding.textLocation.text = categoryText
            if (categories[position]){
                // 활성
                binding.buttonLocation.setBackgroundResource(R.drawable.location_selected_button)
            }
            else{
                // 비활성
                binding.buttonLocation.setBackgroundResource(R.drawable.location_button)
            }
            binding.root.setOnClickListener {
                // RecyclerView item click event
                onClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(RvLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun setLocations(categoryList: Array<Boolean>) {
        categories = categoryList
    }
}
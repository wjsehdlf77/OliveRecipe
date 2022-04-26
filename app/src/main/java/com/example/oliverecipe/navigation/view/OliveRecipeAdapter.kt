package com.example.oliverecipe.navigation.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.oliverecipe.databinding.ItemTodoBinding
import com.example.oliverecipe.navigation.database.OliveRecipe
import com.example.oliverecipe.navigation.database.OliveRecipeDatabase
class OliveRecipeAdapter: RecyclerView.Adapter<OliveRecipeAdapter.Holder>() {

    var helper: OliveRecipeDatabase? = null
    val listData = mutableListOf<OliveRecipe>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    inner class Holder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var mOliveRecipe: OliveRecipe? = null


        init {
            binding.deleteImage.setOnClickListener {
                helper?.oliveRecipeDao()?.delete(mOliveRecipe!!)
//                listData.clear()
                listData.remove(mOliveRecipe)
                notifyDataSetChanged()
            }
        }

        fun setOliveRecipe(oliveRecipe: OliveRecipe) {
            binding.todoText.text = oliveRecipe.content
            mOliveRecipe = oliveRecipe
        }

    }


        override fun onBindViewHolder(holder: OliveRecipeAdapter.Holder, position: Int) {
        val oliveRecipe = listData[position]
        holder.setOliveRecipe(oliveRecipe)
    }


    override fun getItemCount(): Int {
        return listData.size
    }}





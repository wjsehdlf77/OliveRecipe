package com.example.oliverecipe.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.R
import com.example.oliverecipe.databinding.FragmentBagBinding
import com.example.oliverecipe.databinding.ItemTodoBinding
import com.example.oliverecipe.navigation.database.OliveRecipe
import com.example.oliverecipe.navigation.database.OliveRecipeDatabase
import com.example.oliverecipe.navigation.view.OliveRecipeAdapter


class BagViewFragment : Fragment() {

    private var _binding: FragmentBagBinding? = null
    private val binding get() = _binding!!
    var helper: OliveRecipeDatabase?= null
    private var mOliveRecipe: OliveRecipe? = null

//    private lateinit var binding: FragmentBagBinding
//    private val data = arrayListOf<Todo>()


    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity

        // database
        helper = Room.databaseBuilder(context, OliveRecipeDatabase::class.java,"oliveRecipe")
            .allowMainThreadQueries()
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBagBinding.inflate(inflater, container, false)
        return binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.recyclerView.layoutManager = LinearLayoutManager(context)
//        binding.recyclerView.adapter = TodoAdapter(data,
//            onClickDeleteIcon = { deleteTask(it) }

        val adapter = OliveRecipeAdapter()
        adapter.helper = helper

        adapter.listData.addAll(helper?.oliveRecipeDao()?.getAll()?:listOf())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)



        binding.addButton.setOnClickListener{
            if (binding.editText.text.toString().isNotEmpty()) {
                val cart = OliveRecipe(
                    binding.editText.text.toString())
                helper?.oliveRecipeDao()?.insert(cart)
                adapter.listData.clear()
                adapter.listData.addAll(helper?.oliveRecipeDao()?.getAll() ?: listOf())

                adapter.notifyDataSetChanged()
                binding.editText.setText("")

            } }





        binding.urlButton.setOnClickListener {
            findNavController().navigate(R.id.action_action_shopping_bag_to_action_market)
        }
    }


//    private fun deleteTask(todo: Todo) {
//        data.remove(todo)
//
//        binding.recyclerView.adapter?.notifyDataSetChanged()
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


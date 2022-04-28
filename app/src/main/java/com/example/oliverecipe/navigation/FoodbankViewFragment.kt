package com.example.oliverecipe.navigation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.databinding.FragmentFoodbankBinding
import com.example.oliverecipe.navigation.view.oliveListAdapter
import com.example.oliverecipe.refrigeratoritem.viewmodel.ItemViewModel
import java.lang.Exception


class FoodbankViewFragment : Fragment() {

    private lateinit var mItemViewModel: ItemViewModel

    private var _binding: FragmentFoodbankBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodbankBinding.inflate(inflater, container, false)

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        super.onViewCreated(view, savedInstanceState)
        binding.areyclerView.layoutManager = LinearLayoutManager(context)

        mItemViewModel.recentName.observe(viewLifecycleOwner) { item ->

            try {
                oliveData.getOliveData(item[0].itemName) {
                    binding.areyclerView.adapter = oliveListAdapter(it.cOOKRCP01?.row!!)
                }
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }


    }


}


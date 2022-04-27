package com.example.oliverecipe.navigation

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.R

import com.example.oliverecipe.databinding.FragmentHomeBinding
import com.example.oliverecipe.navigation.view.oliveListAdapter
import com.example.oliverecipe.refrigeratoritem.list.ListAdapter
import com.example.oliverecipe.refrigeratoritem.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.fragment_refrigerator.view.*


class HomeViewFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    private lateinit var mItemViewModel: ItemViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.areyclerView.layoutManager = LinearLayoutManager(context)

        oliveData.getOliveData("토마토") {
            binding.areyclerView.adapter = oliveListAdapter(it.cOOKRCP01?.row!!)
        }






        // recyclerView
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 뷰 모델 연결
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        mItemViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { user ->
            adapter.setData(user)
        })


    }
}


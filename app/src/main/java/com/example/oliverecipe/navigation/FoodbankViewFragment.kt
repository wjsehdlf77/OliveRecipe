package com.example.oliverecipe.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.databinding.FragmentFoodbankBinding
import com.example.oliverecipe.navigation.view.oliveListAdapter

class FoodbankViewFragment : Fragment() {

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
        return binding.root }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.areyclerView.layoutManager = LinearLayoutManager(context)

        oliveData.getOliveData("토마토") {
        binding.areyclerView.adapter = oliveListAdapter(it.cOOKRCP01?.row!!)
        }

    }


}


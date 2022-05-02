package com.example.oliverecipe.navigation

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.R

import com.example.oliverecipe.databinding.FragmentHomeBinding
import com.example.oliverecipe.navigation.API.Row
import com.example.oliverecipe.navigation.model.Item
import com.example.oliverecipe.navigation.view.oliveListAdapter
import com.example.oliverecipe.refrigeratoritem.list.ListAdapter
import com.example.oliverecipe.refrigeratoritem.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.fragment_refrigerator.view.*
import java.lang.Exception


class HomeViewFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val rows = mutableListOf<Row>()

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
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.areyclerView.layoutManager = LinearLayoutManager(context)
        mItemViewModel.recentName.observe(viewLifecycleOwner, Observer { item ->

            val asyncTask = @SuppressLint("StaticFieldLeak")
            object: AsyncTask<List<Item>, Void, List<Row>>() {

                override fun doInBackground(vararg p0: List<Item>?): List<Row>? {
                    val item = p0[0]
                    try{
                        for (i in item!!) {  // for (i in item)
                            val r  = oliveData.getOliveData(i.itemName)
                            rows.addAll(r.cOOKRCP01!!.row!!)

                        }
                    } catch (e: Exception){
                        Log.e("에러", "${e.localizedMessage}")
                    }
                    return rows
                }
                override fun onPostExecute(result: List<Row>?) {
                    super.onPostExecute(result)
                    binding.areyclerView.adapter = oliveListAdapter(result!!)
                }
            }
            asyncTask.execute(item)
        })

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


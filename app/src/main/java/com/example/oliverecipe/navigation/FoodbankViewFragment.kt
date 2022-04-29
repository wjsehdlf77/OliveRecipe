package com.example.oliverecipe.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.databinding.FragmentFoodbankBinding
import com.example.oliverecipe.navigation.API.Row
import com.example.oliverecipe.navigation.model.Item
import com.example.oliverecipe.navigation.view.oliveListAdapter
import com.example.oliverecipe.refrigeratoritem.viewmodel.ItemViewModel
import java.lang.Exception


class FoodbankViewFragment : Fragment() {

    private lateinit var mItemViewModel: ItemViewModel
    val rows = mutableListOf<Row>()
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
                    } catch (e:Exception){
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
    }
}






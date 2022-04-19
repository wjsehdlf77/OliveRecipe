package com.example.oliverecipe.navigation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.R
import com.example.oliverecipe.databinding.FragmentRefrigeratorBinding
import com.example.oliverecipe.navigation.model.ItemData
import com.example.oliverecipe.navigation.view.ItemAdapter
import java.util.ArrayList

private var _binding: FragmentRefrigeratorBinding? = null

private val binding get() = _binding!!


class RefrigeratorViewFragment : Fragment() {

    private lateinit var itemList: ArrayList<ItemData>
    private lateinit var itemAdapter: ItemAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRefrigeratorBinding.inflate(inflater, container, false)

        val view = binding.root

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemList = ArrayList()

        itemAdapter = ItemAdapter(requireContext(),itemList)

        binding.mRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.mRecycler.adapter = itemAdapter

        binding.addingBtn.setOnClickListener { addInfo() }
    }

    private fun addInfo() {
        val inflter = LayoutInflater.from(requireContext())
        val v = inflter.inflate(R.layout.add_item,null)
        /**set view*/
        val additemName = v.findViewById<EditText>(R.id.add_item_name)
        val additemvalid = v.findViewById<EditText>(R.id.add_item_valid)

        val addDialog = AlertDialog.Builder(requireContext())

        addDialog.setView(v)
        addDialog.setPositiveButton("확인"){
                dialog,_->
            val names = additemName.text.toString()
            val number = additemvalid.text.toString()
            itemList.add(ItemData("재료 이름 : $names","유통 기한 : $number"))
            itemAdapter.notifyDataSetChanged()
            Toast.makeText(requireContext(),"재료를 성공적으로 추가하였습니다", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("취소"){
                dialog,_->
            dialog.dismiss()
            Toast.makeText(requireContext(),"취소", Toast.LENGTH_SHORT).show()

        }
        addDialog.create()
        addDialog.show()
    }




}
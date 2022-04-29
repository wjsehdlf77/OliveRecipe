package com.example.oliverecipe.refrigeratoritem.floatingadd

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.oliverecipe.R
import com.example.oliverecipe.navigation.model.Item
import com.example.oliverecipe.refrigeratoritem.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.fragment_floating_add.*
import kotlinx.android.synthetic.main.fragment_floating_add.view.*




class FloatingAddFragment : Fragment() {


    private lateinit var mItemViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_floating_add, container, false)

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        view.floating_add_button.setOnClickListener{
            FloatingAddItem()
        }

        return view
    }

    private fun FloatingAddItem(){

        val itemName = floating_itemName.text.toString()
        val itemProperty = floating_itemProperty.text.toString()
        val validity = floating_validity.text

        if (inputCheck(itemName,itemProperty,validity)){

            val floatingItem = Item(0, itemName, itemProperty, Integer.parseInt(validity.toString()))


            mItemViewModel.addItem(floatingItem)

            Toast.makeText(requireContext(),"성공적으로 업데이트했습니다", Toast.LENGTH_SHORT).show()

            // navigate back
            findNavController().navigate(R.id.action_floatingAddFragment_to_action_refrigerator)

        } else{

            Toast.makeText(requireContext(),"빈 부분을 채워주세요", Toast.LENGTH_SHORT).show()
        }
    }

    // 비어있는지 확인
    private fun inputCheck(label:String, property:String, vality: Editable):Boolean{
        if (TextUtils.isEmpty(label)) {
            return false
        } else if (TextUtils.isEmpty(property)) {
            return false
        } else if (TextUtils.isEmpty(vality)) {
            return false
        }
        return true

    }
}
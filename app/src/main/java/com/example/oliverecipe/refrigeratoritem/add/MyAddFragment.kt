package com.example.oliverecipe.refrigeratoritem.add

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.oliverecipe.R
import com.example.oliverecipe.navigation.model.Item
import com.example.oliverecipe.refrigeratoritem.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.alist_item.*
import kotlinx.android.synthetic.main.fragment_my_add.*
import kotlinx.android.synthetic.main.fragment_my_add.view.*


class MyAddFragment : Fragment() {
    // view model initialize
    private lateinit var mItemViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_add, container, false)

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)



        view.add_button.setOnClickListener{
            insertDataToDatabase()
        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("requestKey") { key, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getString("bundleKey")
            // Do something with the result...
            add_itemName.setText(result)
        }
    }



    private fun insertDataToDatabase() {
        // text load
        val itemName = add_itemName.text.toString()
        val itemProperty = add_itemProperty.text.toString()
        val validity = add_validity.text

        if(inputCheck(itemName,itemProperty,validity)){
            // user object를 db에 전송

            val user = Item(0,itemName, itemProperty, Integer.parseInt(validity.toString()))

            mItemViewModel.addItem(user)

            Toast.makeText(requireContext(),"성공적으로 추가했습니다", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_myAddFragment_to_action_refrigerator)
        }else{

            Toast.makeText(requireContext(), "빈 부분을 채워주세요", Toast.LENGTH_LONG).show()
        }
    }

    //텍스트 박스가 비어있는지 확인
    private fun inputCheck(firstName:String, lastName:String, age: Editable):Boolean{
        return !(TextUtils.isEmpty(firstName)&&TextUtils.isEmpty(lastName)&& age.isEmpty())
    }
}
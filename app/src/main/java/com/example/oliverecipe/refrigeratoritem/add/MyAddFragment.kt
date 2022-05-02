package com.example.oliverecipe.refrigeratoritem.add

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oliverecipe.R
import com.example.oliverecipe.navigation.model.Item
import com.example.oliverecipe.refrigeratoritem.list.ListAdapter
import com.example.oliverecipe.refrigeratoritem.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.fragment_my_add.*
import kotlinx.android.synthetic.main.fragment_my_add.view.*
import kotlinx.android.synthetic.main.fragment_refrigerator.view.*
import java.lang.Exception


class MyAddFragment : Fragment() {
    // view model initialize
    private lateinit var mItemViewModel: ItemViewModel

    private var arraySize: Int? = 0

    private  var user0: Item? = null
    private  var user1: Item? = null
    private  var user2: Item? = null
    private  var user3: Item? = null
    private  var user4: Item? = null

    private var save0: Boolean = false
    private var save1: Boolean = false
    private var save2: Boolean = false
    private var save3: Boolean = false
    private var save4: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_add, container, false)

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)


        view.add_button.setOnClickListener{

            insertDataToDatabase0()
            if (save0) {
                mItemViewModel.addItem(user0)
                Toast.makeText(requireContext(),"성공적으로 추가했습니다", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_myAddFragment_to_action_refrigerator)
            } else if(save1) {
                mItemViewModel.addItem(user0)
                mItemViewModel.addItem((user1))
                Toast.makeText(requireContext(),"성공적으로 추가했습니다", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_myAddFragment_to_action_refrigerator)
            } else if(save2) {
                mItemViewModel.addItem(user0)
                mItemViewModel.addItem(user1)
                mItemViewModel.addItem(user2)
                Toast.makeText(requireContext(),"성공적으로 추가했습니다", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_myAddFragment_to_action_refrigerator)
            } else if(save3) {
                mItemViewModel.addItem(user0)
                mItemViewModel.addItem(user1)
                mItemViewModel.addItem(user2)
                mItemViewModel.addItem(user3)
                Toast.makeText(requireContext(),"성공적으로 추가했습니다", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_myAddFragment_to_action_refrigerator)
            } else if(save4) {
                mItemViewModel.addItem(user0)
                mItemViewModel.addItem(user1)
                mItemViewModel.addItem(user2)
                mItemViewModel.addItem(user3)
                mItemViewModel.addItem(user4)
                Toast.makeText(requireContext(),"성공적으로 추가했습니다", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_myAddFragment_to_action_refrigerator)
            }



        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFragmentResultListener("requestKey") { key, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getStringArrayList("bundleKey")
            // Do something with the result...
            //add_itemName.setText(result)



            arraySize = result?.size
            if (arraySize == 5) {
                add_itemName0.setText(result?.get(0))
                add_itemName1.setText(result?.get(1))
                add_itemName2.setText(result?.get(2))
                add_itemName3.setText(result?.get(3))
                add_itemName4.setText(result?.get(4))
            } else if (arraySize == 4) {
                add_itemName0.setText(result?.get(0))
                add_itemName1.setText(result?.get(1))
                add_itemName2.setText(result?.get(2))
                add_itemName3.setText(result?.get(3))

            } else if (arraySize == 3) {
                add_itemName0.setText(result?.get(0))
                add_itemName1.setText(result?.get(1))
                add_itemName2.setText(result?.get(2))
            } else if (arraySize == 2) {
                add_itemName0.setText(result?.get(0))
                add_itemName1.setText(result?.get(1))

            } else if (arraySize == 1) {
                add_itemName0.setText(result?.get(0))
        }
            result?.clear()

        }

    }



    private fun insertDataToDatabase0() {
        // text load
        val itemName = add_itemName0.text.toString()
        val itemProperty = add_itemProperty0.text.toString()
        val validity = add_validity0.text

        if(inputCheck(itemName,itemProperty,validity)){
            // user object를 db에 전송

            user0 = Item(0,itemName, itemProperty + "개", Integer.parseInt(validity.toString()))


            if (arraySize != 1) {
                insertDataToDatabase1()

            } else {
                save0 = true
            }
        }else{
            Toast.makeText(requireContext(),"첫째줄이 비었습니다", Toast.LENGTH_SHORT).show()

        }
    }
    private fun insertDataToDatabase1() {
        // text load
        val itemName = add_itemName1.text.toString()
        val itemProperty = add_itemProperty1.text.toString()
        val validity = add_validity1.text

        if (inputCheck(itemName, itemProperty, validity)) {
            // user object를 db에 전송

            user1 = Item(0, itemName, itemProperty + "개", Integer.parseInt(validity.toString()))

            if (arraySize != 2) {
                insertDataToDatabase2()

            } else {
                save1 = true
            }

        } else {
            Toast.makeText(requireContext(),"둘째줄이 비었습니다", Toast.LENGTH_SHORT).show()

        }
    }
    private fun insertDataToDatabase2() {
        // text load
        val itemName = add_itemName2.text.toString()
        val itemProperty = add_itemProperty2.text.toString()
        val validity = add_validity2.text

        if(inputCheck(itemName,itemProperty,validity)){
            // user object를 db에 전송

            user2 = Item(0,itemName, itemProperty + "개", Integer.parseInt(validity.toString()))

            if (arraySize != 3) {
                insertDataToDatabase3()

            } else {
                save2 = true
            }

        }else{
            Toast.makeText(requireContext(),"셋째줄이 비었습니다", Toast.LENGTH_SHORT).show()

        }
    }
    private fun insertDataToDatabase3() {
        // text load
        val itemName = add_itemName3.text.toString()
        val itemProperty = add_itemProperty3.text.toString()
        val validity = add_validity3.text

        if(inputCheck(itemName,itemProperty,validity)){
            // user object를 db에 전송

            user3 = Item(0,itemName, itemProperty + "개", Integer.parseInt(validity.toString()))

            if (arraySize != 4) {
                insertDataToDatabase4()

            } else {
                save3 = true
            }
        }else{
            Toast.makeText(requireContext(),"넷째줄이 비었습니다", Toast.LENGTH_SHORT).show()
        }
    }
    private fun insertDataToDatabase4() {
        // text load
        val itemName = add_itemName4.text.toString()
        val itemProperty = add_itemProperty4.text.toString()
        val validity = add_validity4.text

        if(inputCheck(itemName,itemProperty,validity)){
            // user object를 db에 전송

            user4 = Item(0,itemName, itemProperty + "개", Integer.parseInt(validity.toString()))

            save4 = true


        }else{
            Toast.makeText(requireContext(),"다섯째줄이 비었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    //텍스트 박스가 비어있는지 확인
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
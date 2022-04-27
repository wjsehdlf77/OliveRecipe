package com.example.oliverecipe.refrigeratoritem.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.oliverecipe.R
import com.example.oliverecipe.navigation.model.Item
import com.example.oliverecipe.refrigeratoritem.viewmodel.ItemViewModel

import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>() // 만약 에러가 보인다면 dependency를 추가

    private lateinit var mItemViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)

        view.update_itemName.setText(args.currentItem.itemName)
        view.update_itemProperty.setText(args.currentItem.itemProperty)
        // api

        view.update_validity.setText(args.currentItem.validity.toString())

        view.update_button.setOnClickListener{
            updateItem()
        }

        //Add Menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem(){

        val itemName = update_itemName.text.toString()
        val itemProperty = update_itemProperty.text.toString()
        val validity = Integer.parseInt(update_validity.text.toString())

        if (inputCheck(itemName,itemProperty,update_validity.text)){

            val updatedItem = Item(args.currentItem.id, itemName, itemProperty,validity)


            mItemViewModel.updateItem(updatedItem)

            Toast.makeText(requireContext(),"성공적으로 업데이트했습니다",Toast.LENGTH_SHORT).show()

            // navigate back
            findNavController().navigate(R.id.action_updateFragment_to_action_refrigerator)

        } else{

            Toast.makeText(requireContext(),"빈 부분을 채워주세요",Toast.LENGTH_SHORT).show()
        }
    }

    // 비어있는지 확인
    private fun inputCheck(firstName:String, lastName:String, age: Editable):Boolean{
        return !(TextUtils.isEmpty(firstName)&& TextUtils.isEmpty(lastName)&& age.isEmpty())
    }

    // resource 파일을 연결
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    //아이템 클릭
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menu_delete){
            deleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteItem(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("예"){ _, _ ->

            mItemViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(),"삭제 완료 : ${args.currentItem.itemName}",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_action_refrigerator)
        }
        builder.setNegativeButton("아니오") { _, _ ->

        }


        builder.setTitle("재료 삭제")
        builder.setMessage("정말로 ${args.currentItem.itemName} 재료를 삭제하시겠습니까?")

        builder.create().show()
    }

}
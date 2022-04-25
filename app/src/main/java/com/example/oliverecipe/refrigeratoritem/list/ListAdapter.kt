package com.example.oliverecipe.refrigeratoritem.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.oliverecipe.R

import com.example.oliverecipe.navigation.RefrigeratorViewFragmentDirections
import com.example.oliverecipe.navigation.model.Item

import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var itemList = emptyList<Item>() //

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row,parent,false))
        // 위에서 만든 custom_row 레이아웃을 연결
    }

    override fun getItemCount(): Int {
        return itemList.size // 사이즈를 리턴
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = itemList[position]
//        holder.itemView.id_txt.text = currentItem.id.toString()
        holder.itemView.itemName_txt.text = currentItem.itemName
        holder.itemView.itemProperty_txt.text = currentItem.itemProperty
        holder.itemView.validity_txt.text = currentItem.validity.toString()

        holder.itemView.rowLayout.setOnClickListener{
            val action = RefrigeratorViewFragmentDirections.actionActionRefrigeratorToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(item:List<Item>){
        // 유저리스트가 변경 되었을때, 업데이트
        this.itemList = item
        notifyDataSetChanged()
    }




}
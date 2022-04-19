package com.example.oliverecipe.navigation.view

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.oliverecipe.R
import com.example.oliverecipe.navigation.model.ItemData


class ItemAdapter(val c:Context, val itemList:ArrayList<ItemData>):RecyclerView.Adapter<ItemAdapter.UserViewHolder>()
{



  inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v){
      var name:TextView
      var mbNum:TextView
      var mMenus:ImageView

      init {
          name = v.findViewById<TextView>(R.id.mTitle)
          mbNum = v.findViewById<TextView>(R.id.mSubTitle)
          mMenus = v.findViewById(R.id.mMenus)
          mMenus.setOnClickListener { popupMenus(it) }
      }

      private fun popupMenus(v:View) {
          val position = itemList[adapterPosition]
          val popupMenus = PopupMenu(c,v)
          popupMenus.inflate(R.menu.show_menu)
          popupMenus.setOnMenuItemClickListener {
              when(it.itemId){
                  R.id.editText->{
                      val v = LayoutInflater.from(c).inflate(R.layout.add_item,null)
                      val name = v.findViewById<EditText>(R.id.add_item_name)
                      val date = v.findViewById<EditText>(R.id.add_item_valid)
                              AlertDialog.Builder(c)
                                      .setView(v)
                                      .setPositiveButton("확인"){
                                          dialog,_->
                                          position.itemName = name.text.toString()
                                          position.itemDate = date.text.toString()
                                          notifyDataSetChanged()
                                          Toast.makeText(c,"편집 성공",Toast.LENGTH_SHORT).show()
                                          dialog.dismiss()

                                      }
                                      .setNegativeButton("취소"){
                                          dialog,_->
                                          dialog.dismiss()

                                      }
                                      .create()
                                      .show()

                      true
                  }
                  R.id.delete->{
                      /**set delete*/
                      AlertDialog.Builder(c)
                              .setTitle("삭제")
                              .setIcon(R.drawable.ic_warning)
                              .setMessage("정말로 삭제하시겠습니까?")
                              .setPositiveButton("삭제"){
                                  dialog,_->
                                  itemList.removeAt(adapterPosition)
                                  notifyDataSetChanged()
                                  Toast.makeText(c,"삭제하였습니다",Toast.LENGTH_SHORT).show()
                                  dialog.dismiss()
                              }
                              .setNegativeButton("취소"){
                                  dialog,_->
                                  dialog.dismiss()
                              }
                              .create()
                              .show()

                      true
                  }
                  else-> true
              }

          }
          popupMenus.show()
          val popup = PopupMenu::class.java.getDeclaredField("mPopup")
          popup.isAccessible = true
          val menu = popup.get(popupMenus)
          menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java)
                  .invoke(menu,true)
      }
  }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
       val inflater = LayoutInflater.from(parent.context)
        val v  = inflater.inflate(R.layout.list_item,parent,false)
        return UserViewHolder(v)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
       val newList = itemList[position]
        holder.name.text = newList.itemName
        holder.mbNum.text = newList.itemDate
    }

    override fun getItemCount(): Int {
      return  itemList.size
    }
}
package com.example.oliverecipe.navigation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oliverecipe.R
import com.example.oliverecipe.databinding.ItemTodoBinding

//data class Todo(
//    val text: String,
//    var isDone: Boolean = false,
//)
//
//class TodoAdapter(
//    private val dataSet: List<Todo>,
//    val onClickDeleteIcon: (todo: Todo) -> Unit
//) :
//    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
//
//    class TodoViewHolder(val binding: ItemTodoBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        val todoText: TextView
//            get() {
//                TODO()
//            }
//
//    }
//
//
//    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TodoViewHolder {
//        val view = LayoutInflater.from(viewGroup.context)
//            .inflate(R.layout.item_todo, viewGroup, false)
//
//        return TodoViewHolder(ItemTodoBinding.bind(view))
//    }
//
//    override fun onBindViewHolder(todoViewHolder: TodoViewHolder, position: Int) {
//        val listposition = dataSet[position]
//        todoViewHolder.binding.todoText.text = listposition.text
//
//        todoViewHolder.binding.deleteImage.setOnClickListener {
//            onClickDeleteIcon.invoke(listposition)
//        }
//
//
//    }
//
//    override fun getItemCount() = dataSet.size
//
//}
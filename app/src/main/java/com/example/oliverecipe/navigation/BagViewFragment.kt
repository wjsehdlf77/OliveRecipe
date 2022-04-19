package com.example.oliverecipe.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.databinding.ActivityMainBinding
import com.example.oliverecipe.databinding.FragmentAddBinding
import com.example.oliverecipe.databinding.FragmentBagBinding
import kotlinx.android.synthetic.main.fragment_bag.*
import kotlinx.android.synthetic.main.fragment_refrigerator.*
import javax.security.auth.Destroyable


class BagViewFragment : Fragment() {

    private var _binding: FragmentBagBinding? = null
    private val binding get() = _binding!!

//    private lateinit var binding: FragmentBagBinding
    private val data = arrayListOf<Todo>()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBagBinding.inflate(inflater, container, false)
//            val view = binding.root
//
//            return view

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = TodoAdapter(data,
            onClickDeleteIcon = {
                deleteTask(it)
            }

        )

        binding.addButton.setOnClickListener {
            addTask()
        }

        binding.urlButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://naver.com"))
            startActivity(intent)
        }

    }

    private fun addTask() {
        val todo = Todo(binding.editText.text.toString(),false)
        data.add(todo)

        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    private fun deleteTask(todo: Todo) {
        data.remove(todo)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



package com.example.oliverecipe.navigation

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.R
import com.example.oliverecipe.databinding.FragmentRefrigeratorBinding
import com.example.oliverecipe.fragments.list.ListAdapter
import com.example.oliverecipe.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_refrigerator.view.*
import java.util.*

private var _binding: FragmentRefrigeratorBinding? = null

private val binding get() = _binding!!

lateinit var mainActivity: MainActivity

class RefrigeratorViewFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //레이아웃 연결
        val view = inflater.inflate(R.layout.fragment_refrigerator, container, false)

        //리사이클러뷰
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //뷰모델 연결
        //뷰모델을 불러옵니다.
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { user ->
            adapter.setData(user)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_action_refrigerator_to_myAddFragment) //플로팅 버튼을 누르면 addFragment로 화면전환합니다.
        }

        //menu 추가
        setHasOptionsMenu(true)
        return view
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        mainActivity = context as MainActivity
//
//    }

    //delete user때와 같은 menu를 추가해주겠습니다.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.menu_delete){
            deleteAllUsers() //deleteAlluser 함수를 실행시킵니다.
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() { //deleteUser를 만들어줬을때와 같이 dialog를 만들겠습니다.
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton("예"){ _, _ ->
            mUserViewModel.deleteAllUsers()
            Toast.makeText(requireContext(),"삭제 완료",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("아니오") { _, _ ->
        }

        builder.setTitle("모든 재료 삭제")
        builder.setMessage("모든 항목을 삭제하시겠습니까?")
        builder.create().show()
    }

}
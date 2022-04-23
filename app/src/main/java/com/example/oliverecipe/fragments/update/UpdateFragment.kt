package com.example.oliverecipe.fragments.update

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
import com.example.oliverecipe.model.User
import com.example.oliverecipe.viewmodel.UserViewModel

import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>() //만약 에러가 보인다면 dependency를 추가해야합니다.

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        //editText부분을 argument에서 받아온 값을 넣어줍니다.
        view.updateFirstName_et.setText(args.currentUser.firstName)
        view.updateLastName_et.setText(args.currentUser.firstName)
        view.updateAge_et.setText(args.currentUser.age.toString())

        view.update_button.setOnClickListener{
            updateItem()
        }

        //Add Menu
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem(){

//        변경된 값들을 editText에서 가져옵니다.
        val firstName = updateFirstName_et.text.toString()
        val lastName = updateLastName_et.text.toString()
        val age = Integer.parseInt(updateAge_et.text.toString())

        if (inputCheck(firstName,lastName,updateAge_et.text)){

            //updatedUser는 업데이트된 데이터입니다.
            val updatedUser = User(args.currentUser.id, firstName, lastName,age)

            //updateUser쿼리를 만들어서 Update Query를 이용하여 database에 추가해줘야합니다.
            //Update 쿼리는 DAO에서 추가해야합니다.
            //지금은 viewModel에 update 쿼리가 생기면 updatedUser가 전달되도록 구현만 해놓겠습니다.

            mUserViewModel.updateUser(updatedUser)

            Toast.makeText(requireContext(),"UpdatedSuccessfully",Toast.LENGTH_SHORT).show()

            //navigate back
            findNavController().navigate(R.id.action_updateFragment_to_action_refrigerator)

        } else{

            //EditText가 빈칸이면 토스트 메세지
            Toast.makeText(requireContext(),"Please fill out all field",Toast.LENGTH_SHORT).show()
        }
    }

    //editText가 비어있는지 확인하는 함수
    private fun inputCheck(firstName:String, lastName:String, age: Editable):Boolean{
        return !(TextUtils.isEmpty(firstName)&& TextUtils.isEmpty(lastName)&& age.isEmpty())
    }

    //action menu resource파일을 연결해줍니다.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    //action menu에 아이템이 클릭되었을때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //id.menu_delete이 클릭되면 deleteUser함수 실행
        if(item.itemId == R.id.menu_delete){
            deleteUser()//아래에 이어서 만들어보겠습니다.
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser(){ //delteIcon이 나오면 Dialog를 띄워서 물어보겠습니다.
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){ _, _ ->
            //yes클릭시 viewMOdel.deleteUser를 실행시킵니다. args.currentUser를 삭제하게됩니다.
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(),"Suscessfully removed: ${args.currentUser.firstName}",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ ->
            //아무일도 일어나지않습니다.
        }

        //dialog의 UI세팅입니다.
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure to delete ${args.currentUser.firstName}")

        //dialog가 UI에 보여집니다.
        builder.create().show()
    }

}
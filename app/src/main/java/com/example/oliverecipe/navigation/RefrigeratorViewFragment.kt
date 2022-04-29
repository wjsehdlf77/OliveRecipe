package com.example.oliverecipe.navigation

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.oliverecipe.MainActivity
import com.example.oliverecipe.R
import com.example.oliverecipe.databinding.FragmentRefrigeratorBinding
import com.example.oliverecipe.navigation.view.oliveListAdapter
import com.example.oliverecipe.refrigeratoritem.list.ListAdapter
import com.example.oliverecipe.refrigeratoritem.viewmodel.ItemViewModel
import kotlinx.android.synthetic.main.alist_item.view.*
import kotlinx.android.synthetic.main.fragment_refrigerator.view.*
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.lang.Exception
import java.text.SimpleDateFormat

private var _binding: FragmentRefrigeratorBinding? = null

private val binding get() = _binding!!

lateinit var mainActivity: MainActivity

class RefrigeratorViewFragment : Fragment() {

    private lateinit var mItemViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // layout
        val view = inflater.inflate(R.layout.fragment_refrigerator, container, false)

        // recyclerView
        val adapter = ListAdapter()
        val recyclerView = view.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // 뷰 모델 연결
        mItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        mItemViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { user ->
            adapter.setData(user)
        })

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_action_refrigerator_to_myAddFragment)
        }



        mItemViewModel.readAllData.observe(viewLifecycleOwner) { item ->

            try {


                oliveData.getOliveData(item[0].itemName) {
                    oliveData.getOliveData(item[0].validity.toString()){
                        val currentTime : Long = System.currentTimeMillis()
                        val currentDate = SimpleDateFormat("yyyyMMdd")
                        currentDate.format(currentTime)

                        for (i in item){
                            val startDate = currentDate.parse(currentDate.format(currentTime)).time
                            val endDate = currentDate.parse(i.validity.toString()).time
                            if (((endDate - startDate) / (24 * 60 * 60 * 1000)) <= 1) {
                                //mqtt
                                val client =
                                    MqttClient(
                                        "tcp://172.30.1.21:1883",
                                        MqttClient.generateClientId(),
                                        null
                                    )
                                client.connect()
                                client.publish(
                                    "validity",
                                    MqttMessage("${i.itemName}의 유통기간이 1일 남았습니다.".toByteArray())
                                )
                            }

                        }

                    }

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // menu 추가
        setHasOptionsMenu(true)
        return view
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//
//        mainActivity = context as MainActivity
//
//    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId ==R.id.menu_delete){
            deleteAllUsers() //deleteAlluser 함수를 실행시킵니다.
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllUsers() {
        val builder = android.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton("예"){ _, _ ->
            mItemViewModel.deleteAllItems()
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
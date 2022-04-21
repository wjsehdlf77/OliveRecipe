package com.example.oliverecipe.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.oliverecipe.R
import com.example.oliverecipe.databinding.FragmentFavoriteBinding
import com.example.oliverecipe.navigation.model.Food
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.favorite_item.view.*


class FavoriteViewFragment : Fragment() {

    var firestore : FirebaseFirestore? = null

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_favorite, container, false)
//        return view

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.favoriteRecyclerView.adapter = RecyclerViewAdapter()
        binding.favoriteRecyclerView.layoutManager = LinearLayoutManager(context)

    }


    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        // Person 클래스 ArrayList 생성성
        var recipe : ArrayList<Food> = arrayListOf()

        init {
            firestore?.collection("test")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                recipe.clear()

                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(Food::class.java)
                    recipe.add(item!!)
                }
                notifyDataSetChanged()
            }
        }

        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.favorite_item, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView

            viewHolder.main_Title.text = recipe[position].name
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return recipe.size
        }
    }
}

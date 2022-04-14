package com.example.oliverecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.oliverecipe.databinding.FragmentBagBinding
import kotlinx.android.synthetic.main.fragment_refrigerator.*

private var _binding: FragmentBagBinding? = null

private val binding get() = _binding!!

class BagViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBagBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


}
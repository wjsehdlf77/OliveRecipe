package com.example.oliverecipe



import android.content.Intent

import android.os.Bundle
import android.provider.MediaStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import com.example.oliverecipe.databinding.FragmentRefrigeratorBinding




private var _binding: FragmentRefrigeratorBinding? = null

private val binding get() = _binding!!

class RefrigeratorViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRefrigeratorBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnCamera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        }


    }





}


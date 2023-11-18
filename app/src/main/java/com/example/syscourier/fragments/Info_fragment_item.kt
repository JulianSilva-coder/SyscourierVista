package com.example.syscourier.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.syscourier.R

class info_fragment_item : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflar el diseño del fragmento
        return inflater.inflate(R.layout.asignaciones_fragment_info_item, container, false)
    }
}
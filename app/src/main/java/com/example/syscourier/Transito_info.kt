package com.example.syscourier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syscourier.adapters.MyAdapter
import com.example.syscourier.databinding.FragmentTransitoBinding

class Transito_info : Fragment() {
    private lateinit var binding: FragmentTransitoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransitoBinding.inflate(inflater, container, false)

        val data = listOf(
            GuiaIntro(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntro(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntro(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntro(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntro(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntro(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntro(1234567, "Objeto 1", "Descripción del objeto 1")
        )


        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MyAdapter(data) // Utiliza el adaptador existente MyAdapter
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Aquí puedes agregar cualquier lógica adicional que necesites para tu fragmento
    }
}

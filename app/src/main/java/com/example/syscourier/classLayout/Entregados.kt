package com.example.syscourier.classLayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syscourier.GuiaIntro
import com.example.syscourier.adapters.Adapterentregados
import com.example.syscourier.databinding.FragmentTransitoBinding


class Entregados : Fragment() {
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
        val adapter = Adapterentregados(data) // Pasar el contexto al adaptador
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
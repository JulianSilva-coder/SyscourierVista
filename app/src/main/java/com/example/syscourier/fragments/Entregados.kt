package com.example.syscourier.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syscourier.dto.GuiaIntroDTO
import com.example.syscourier.adapters.Adapterentregados
import com.example.syscourier.databinding.EntregadosFragmentBinding


class Entregados : Fragment() {
    private lateinit var binding: EntregadosFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EntregadosFragmentBinding.inflate(inflater, container, false)

        val data = listOf(
            GuiaIntroDTO(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntroDTO(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntroDTO(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntroDTO(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntroDTO(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntroDTO(1234567, "Objeto 1", "Descripción del objeto 1"),
            GuiaIntroDTO(1234567, "Objeto 1", "Descripción del objeto 1")
        )

        val recyclerView = binding.recyclerViewEntregados
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Pasa el contexto al adaptador
        val adapter = Adapterentregados(data, requireContext())
        recyclerView.adapter = adapter

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
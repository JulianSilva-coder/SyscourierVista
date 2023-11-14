package com.example.syscourier.fragments

import com.example.syscourier.adapters.AdapterDevoluciones
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syscourier.databinding.DevolucionesFragmentBinding

data class MydevolucionesObject(val name: String, val description: String, val id: Int)

class Devoluciones : Fragment() {
    private lateinit var binding: DevolucionesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DevolucionesFragmentBinding.inflate(inflater, container, false)

        val data = listOf(
            MydevolucionesObject("Objeto 1", "Descripción del objeto 1", 1234567),
            MydevolucionesObject("Objeto 2", "Descripción del objeto 2", 987654),
            MydevolucionesObject("Objeto 3", "Descripción del objeto 3", 1234556),
            MydevolucionesObject("Objeto 4", "Descripción del objeto 4", 1234556),
            MydevolucionesObject("Objeto 5", "Descripción del objeto 5", 1234556),
            MydevolucionesObject("Objeto 6", "Descripción del objeto 6", 1234556),
            MydevolucionesObject("Objeto 1", "Descripción del objeto 1", 1234567),
            MydevolucionesObject("Objeto 2", "Descripción del objeto 2", 987654),
            MydevolucionesObject("Objeto 3", "Descripción del objeto 3", 1234556),
            MydevolucionesObject("Objeto 4", "Descripción del objeto 4", 1234556),
            MydevolucionesObject("Objeto 5", "Descripción del objeto 5", 1234556),
            MydevolucionesObject("Objeto 6", "Descripción del objeto 6", 1234556)
        )

        val recyclerView = binding.recyclerViewDevoluciones
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = AdapterDevoluciones(data, requireContext()) // Asegúrate de tener la clase com.example.syscourier.adapters.AdapterDevoluciones definida y pasando el contexto adecuadamente
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}

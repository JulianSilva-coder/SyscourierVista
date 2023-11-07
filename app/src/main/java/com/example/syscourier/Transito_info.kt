package com.example.syscourier

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.syscourier.databinding.FragmentTransitoBinding

data class MyTransitoObject(val name: String, val description: String, val id: Int)

fun List<MyTransitoObject>.toMyObjectList(): List<MyObject> {
    return this.map { MyObject(it.name, it.description, it.id) }
}

class Transito_info : Fragment() {
    private lateinit var binding: FragmentTransitoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransitoBinding.inflate(inflater, container, false)

        val data = listOf(
            MyTransitoObject("Objeto 1", "Descripción del objeto 1", 1234567),
            MyTransitoObject("Objeto 2", "Descripción del objeto 2", 987654),
            MyTransitoObject("Objeto 3", "Descripción del objeto 3", 1234556),
            MyTransitoObject("Objeto 4", "Descripción del objeto 4", 1234556),
            MyTransitoObject("Objeto 5", "Descripción del objeto 5", 1234556),
            MyTransitoObject("Objeto 6", "Descripción del objeto 6", 1234556),
            MyTransitoObject("Objeto 1", "Descripción del objeto 1", 1234567),
            MyTransitoObject("Objeto 2", "Descripción del objeto 2", 987654),
            MyTransitoObject("Objeto 3", "Descripción del objeto 3", 1234556),
            MyTransitoObject("Objeto 4", "Descripción del objeto 4", 1234556),
            MyTransitoObject("Objeto 5", "Descripción del objeto 5", 1234556),
            MyTransitoObject("Objeto 6", "Descripción del objeto 6", 1234556)
        )

        val myObjectData = data.toMyObjectList() // Convertir la lista de MyTransitoObject a MyObject

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = MyAdapter(myObjectData) // Utiliza el adaptador existente MyAdapter
        recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Aquí puedes agregar cualquier lógica adicional que necesites para tu fragmento
    }
}

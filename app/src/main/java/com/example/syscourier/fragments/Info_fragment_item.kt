package com.example.syscourier.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.syscourier.R

class info_fragment_item : Fragment() {

    /**
     * Crea la vista del fragmento.
     * @param inflater El objeto LayoutInflater que se utiliza para inflar la vista del fragmento.
     * @param container El ViewGroup al que se adjuntará la vista del fragmento.
     * @param savedInstanceState El estado previamente guardado de la actividad.
     * @return La vista inflada del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflar el diseño del fragmento
        return inflater.inflate(R.layout.asignaciones_fragment_info_item, container, false)
    }
}
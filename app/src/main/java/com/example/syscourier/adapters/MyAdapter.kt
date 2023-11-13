package com.example.syscourier.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.syscourier.R
import com.example.syscourier.classLayout.MyObject

class MyAdapter(private val data: List<MyObject>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val actionButton: Button = itemView.findViewById(R.id.actionButton) // Add this line
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_entregados, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.nameTextView.text = item.name
        holder.descriptionTextView.text = item.description

        holder.actionButton.setOnClickListener {
            // Aquí puedes definir la lógica que deseas ejecutar cuando se presiona el botón
            Toast.makeText(holder.itemView.context, "Botón presionado en posición: $position", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

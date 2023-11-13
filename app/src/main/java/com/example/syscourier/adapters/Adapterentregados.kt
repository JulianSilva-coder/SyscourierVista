package com.example.syscourier.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import com.example.syscourier.classLayout.MyObject
import com.example.syscourier.R
import com.example.syscourier.activitys.entregados_infoActivity

class Adapterentregados(private val data: List<MyObject>, private val context: Context) : RecyclerView.Adapter<Adapterentregados.ViewHolder>() {
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
            val intent = Intent(context, entregados_infoActivity::class.java) // Reemplaza Transito_info con el nombre de tu nueva actividad
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}

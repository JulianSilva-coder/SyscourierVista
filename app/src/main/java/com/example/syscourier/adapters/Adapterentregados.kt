package com.example.syscourier.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syscourier.activities.Entregados_infoActivity
import com.example.syscourier.dto.GuiaIntroDTO
import com.example.syscourier.R
import com.example.syscourier.activities.final_activity_info

class Adapterentregados(private val data: List<GuiaIntroDTO>, private val context: Context) :
    RecyclerView.Adapter<Adapterentregados.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idGuia: TextView = itemView.findViewById(R.id.idAsignacion)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val actionButton: Button = itemView.findViewById(R.id.actionButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.guia_intro_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.nameTextView.text = item.remitenteNombre
        holder.descriptionTextView.text = item.destinatarioDireccion
        holder.idGuia.text = item.id.toString()
        holder.actionButton.setOnClickListener {
            val intent = Intent(context, final_activity_info::class.java)
            intent.putExtra(final_activity_info.EXTRA_ID_GUIA, item.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

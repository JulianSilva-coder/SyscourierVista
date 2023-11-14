package com.example.syscourier.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.syscourier.activities.Entregados_infoActivity
import com.example.syscourier.dto.GuiaIntroDTO
import com.example.syscourier.R

class Adapterentregados(private val data: List<GuiaIntroDTO>, private val entregadosInfoActivity: Context) :
    RecyclerView.Adapter<Adapterentregados.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val actionButton: Button = itemView.findViewById(R.id.actionButton)
        val idGuia: TextView = itemView.findViewById(R.id.idAsignacion)

        init {
            actionButton.text = "Click Me"
            actionButton.setOnClickListener {
                val intent = Intent(entregadosInfoActivity, Entregados_infoActivity::class.java)
                entregadosInfoActivity.startActivity(intent)
            }
        }
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
            // Aquí puedes definir la lógica que deseas ejecutar cuando se presiona el botón
            Toast.makeText(
                holder.itemView.context,
                "Id del objeto: ${item.id}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

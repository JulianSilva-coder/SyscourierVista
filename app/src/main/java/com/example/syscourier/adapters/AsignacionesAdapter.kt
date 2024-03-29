/**
 * Autor: Julian Silva y Nicolas Peña
 * Descripción: Esta clase es un adaptador para mostrar asignaciones en un RecyclerView.
 * Enlaza los datos de una lista de [GuiaIntroDTO] a las vistas correspondientes.
 *
 * @param data Lista de [GuiaIntroDTO] que contiene los datos a mostrar.
 * @param context El [Context] asociado con el adaptador.
 */
package com.example.syscourier.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syscourier.dto.GuiaIntroDTO
import com.example.syscourier.R
import com.example.syscourier.activities.AsignacionesInfoItemActivity
import com.example.syscourier.activities.TransporteInfoItemActivity

class AsignacionesAdapter(private val data: List<GuiaIntroDTO>, private val context: Context) : RecyclerView.Adapter<AsignacionesAdapter.ViewHolder>() {

    /**
     * Clase ViewHolder para contener las vistas usadas en el RecyclerView.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idGuia: TextView = itemView.findViewById(R.id.idAsignacion)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val actionButton: Button = itemView.findViewById(R.id.actionButton) // Agregar esta línea
    }

    /**
     * Crea instancias de ViewHolder según sea necesario.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.guia_intro_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    /**
     * Enlaza datos a las vistas dentro del ViewHolder.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.nameTextView.text = item.remitenteNombre
        holder.descriptionTextView.text = item.destinatarioDireccion
        holder.idGuia.text = item.id.toString()
        holder.actionButton.setOnClickListener {
            val intent = Intent(context, TransporteInfoItemActivity::class.java)
            intent.putExtra(TransporteInfoItemActivity.EXTRA_ID_GUIA, item.id)
            context.startActivity(intent)
        }
    }

    /**
     * Obtiene la cantidad de elementos en el conjunto de datos.
     */
    override fun getItemCount(): Int {
        return data.size
    }
}

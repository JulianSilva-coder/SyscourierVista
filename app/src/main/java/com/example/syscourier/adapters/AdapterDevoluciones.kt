package com.example.syscourier.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syscourier.activities.Devoluciones_info_activity
import com.example.syscourier.fragments.MydevolucionesObject
import com.example.syscourier.R
import com.example.syscourier.activities.final_activity_info
import com.example.syscourier.dto.GuiaIntroDTO
/**
 * AdapterDevoluciones es un adaptador para manejar la visualización de datos de GuiaIntroDTO en un RecyclerView.
 *
 * @param data Lista de GuiaIntroDTO que se mostrarán en el RecyclerView.
 * @param context Contexto de la aplicación.
 */
class AdapterDevoluciones(private val data: List<GuiaIntroDTO>, private val context: Context) : RecyclerView.Adapter<AdapterDevoluciones.ViewHolder>() {

    /**
     * ViewHolder para mantener las vistas de los elementos de la lista.
     *
     * @param itemView Vista individual de un elemento de la lista.
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idGuia: TextView = itemView.findViewById(R.id.idAsignacion)
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val actionButton: Button = itemView.findViewById(R.id.actionButton)
    }

    /**
     * Crea nuevos ViewHolders cuando se necesitan, invocado por el LayoutManager.
     *
     * @param parent Grupo de vistas en el que se inflará el nuevo ViewHolder.
     * @param viewType El tipo de vista del nuevo ViewHolder.
     * @return ViewHolder recién creado que contiene la vista del elemento de la lista.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.guia_intro_item_layout, parent, false)
        return ViewHolder(itemView)
    }

    /**
     * Actualiza el contenido de un ViewHolder específico según su posición en la lista.
     *
     * @param holder ViewHolder a actualizar.
     * @param position Posición del elemento en la lista de datos.
     */
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

    /**
     * Obtiene el número total de elementos en la lista de datos.
     *
     * @return El número total de elementos en la lista de datos.
     */
    override fun getItemCount(): Int {
        return data.size
    }
}

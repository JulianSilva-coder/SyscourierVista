import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syscourier.Devoluciones_info_activity
import com.example.syscourier.MydevolucionesObject
import com.example.syscourier.R

class AdapterDevoluciones(private val data: List<MydevolucionesObject>, private val context: Context) : RecyclerView.Adapter<AdapterDevoluciones.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView) ?: throw NullPointerException("NameTextView not found")
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView) ?: throw NullPointerException("DescriptionTextView not found")
        val actionButton: Button = itemView.findViewById(R.id.actionButton) ?: throw NullPointerException("ActionButton not found")

        init {
            actionButton.text = "Click Me"
            actionButton.setOnClickListener {
                val intent = Intent(context, Devoluciones_info_activity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_devoluciones, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.nameTextView.text = item.name
        holder.descriptionTextView.text = item.description
    }

    override fun getItemCount(): Int {
        return data.size
    }
}

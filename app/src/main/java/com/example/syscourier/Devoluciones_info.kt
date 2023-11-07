import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.syscourier.R

class Devoluciones_info : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_devoluciones, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener referencia al Spinner en tu diseño
        val spinner: Spinner = view.findViewById(R.id.spinner)

        // Crear un ArrayAdapter usando el array de strings definido en strings.xml
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.opciones_array,
            android.R.layout.simple_spinner_item
        )

        // Especificar el diseño a utilizar cuando la lista de opciones aparece
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Aplicar el adaptador al Spinner
        spinner.adapter = adapter

        // Configurar un listener para realizar acciones cuando se selecciona un elemento
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Acciones a realizar cuando un elemento es seleccionado
                val selectedItem = parent.getItemAtPosition(position).toString()
                // Puedes hacer algo con el elemento seleccionado aquí
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Acciones a realizar cuando no se selecciona ningún elemento
            }
        }
    }
}

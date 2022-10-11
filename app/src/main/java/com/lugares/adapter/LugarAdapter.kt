package com.lugares.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.lugares.databinding.LugarFilaBinding
import com.lugares.model.Lugar
import com.lugares.ui.lugar.LugarFragmentDirections

class LugarAdapter : RecyclerView.Adapter<LugarAdapter.LugarViewHolder>() {
    //La lista de lugares a "dibujar"
    private var listaLugares = emptyList<Lugar>()
    //Contenedor de vistas "cajitas" en memoria...
    inner class LugarViewHolder(private val itemBinding: LugarFilaBinding)
        : RecyclerView.ViewHolder(itemBinding.root) {
        fun dibuja(lugar: Lugar) {
            itemBinding.tvName.text = lugar.nombre
            itemBinding.tvPhone.text = lugar.telefono
            itemBinding.tvEmail.text = lugar.correo
            itemBinding.CardView.setOnClickListener {
                val nav = LugarFragmentDirections.actionNavLugarToUpdateLugarFragment(lugar)
                itemView.findNavController().navigate(nav)
            }
        }
    }
    //Crea un "cajita" una vista del tipo lugarFila...
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LugarViewHolder {
        val itemBinding = LugarFilaBinding
            .inflate(LayoutInflater.from(parent.context)
                ,parent
                ,false)
        return LugarViewHolder(itemBinding)
    }
    //Con una "cajita" creada... se pasa a dibujar los datos del lugar x
    override fun onBindViewHolder(holder: LugarViewHolder, position: Int) {
        val lugarActual = listaLugares[position]
        holder.dibuja(lugarActual)
    }
    override fun getItemCount(): Int {
        return listaLugares.size
    }
    fun setLugares(lugares : List<Lugar>) {
        listaLugares = lugares
        notifyDataSetChanged()  //Se notifica que el conjunto de datos cambio y se redibuja toda la lista
    }
}
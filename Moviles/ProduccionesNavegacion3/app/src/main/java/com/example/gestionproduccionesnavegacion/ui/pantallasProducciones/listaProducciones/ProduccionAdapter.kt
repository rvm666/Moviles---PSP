package com.example.gestionproduccionesnavegacion.ui.pantallasProducciones.listaProducciones

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionproduccionesnavegacion.databinding.ItemProduccionBinding
import com.example.gestionproduccionesnavegacion.domain.model.Produccion


class ProduccionAdapter(
    val actions: ProduccionesActions,
    val onClickView: (Produccion) -> Unit,
) : ListAdapter<Produccion, ProduccionAdapter.ProduccionViewHolder>(
    ProduccionDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): ProduccionViewHolder{
        val binding = ItemProduccionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProduccionViewHolder(binding,onClickView,actions)
    }

    override fun onBindViewHolder(holder: ProduccionViewHolder, position: Int){
        holder.bind(getItem(position))
    }

    fun getItemAt(position: Int): Produccion? = currentList.getOrNull(position)
    class ProduccionViewHolder(private val binding: ItemProduccionBinding,
                               val onClickView : (Produccion) -> Unit,
                               val actions: ProduccionesActions,
    ) : RecyclerView.ViewHolder(binding.root)    {
        fun bind(produccion: Produccion){
            binding.NombreProduccion.text = produccion.nombre
            binding.Director.text = produccion.director
            binding.root.setOnClickListener{
                onClickView(produccion)
                actions.onItemClick(produccion)
            }
        }
    }

    class ProduccionDiffCallback: DiffUtil.ItemCallback<Produccion>(){
        override fun areItemsTheSame(oldItem: Produccion, newItem: Produccion): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Produccion, newItem: Produccion): Boolean {
            return oldItem == newItem
        }
    }

    interface ProduccionesActions{
        fun onItemClick(produccion: Produccion)
        fun onItemSwiped(produccion: Produccion)
    }
}
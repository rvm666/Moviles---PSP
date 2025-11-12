package com.example.gestionproduccionesnavegacion.ui.ListaUsuarios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionproduccionesnavegacion.databinding.ItemUsuarioBinding
import com.example.gestionproduccionesnavegacion.domain.model.Usuario

class UsuarioAdapter (
    val actions: UsuariosActions,
    val onClickView: (Usuario) -> Unit,
) : ListAdapter<Usuario, UsuarioAdapter.UsuarioViewHolder>(
    UsuarioDiffCallback()
) {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsuarioViewHolder(binding, onClickView, actions)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class UsuarioViewHolder(
        private val binding: ItemUsuarioBinding,
        val onClickView: (Usuario) -> Unit,
        val actions: UsuariosActions,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(usuario: Usuario) {
            binding.NombreProduccion.text = usuario.nombre
            binding.NombreDirector.text = usuario.email
            binding.root.setOnClickListener {
                onClickView(usuario)
                actions.onItemClick(usuario)
            }
        }
    }

    class UsuarioDiffCallback : DiffUtil.ItemCallback<Usuario>() {
        override fun areItemsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem.nombre == newItem.nombre && oldItem.email == newItem.email
        }

        override fun areContentsTheSame(oldItem: Usuario, newItem: Usuario): Boolean {
            return oldItem == newItem
        }
    }

    interface UsuariosActions {
        fun onItemClick(produccion: Usuario)
    }
}
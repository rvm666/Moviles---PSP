package com.example.gestionproduccionesnavegacion.ui.ListaUsuarios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gestionproduccionesnavegacion.databinding.FragmentListaUsuariosBinding
import com.example.gestionproduccionesnavegacion.ui.InfoUsuario.ListaUsuariosDirections

class ListaUsuarios : Fragment() {

    private var _binding: FragmentListaUsuariosBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaUsuariosBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){

            buttonAniadir.setOnClickListener {
                val action = ListaUsuariosDirections.actionInfoUsuarioToCrearUsuario()
                findNavController().navigate(action)
            }

        }

    }

}
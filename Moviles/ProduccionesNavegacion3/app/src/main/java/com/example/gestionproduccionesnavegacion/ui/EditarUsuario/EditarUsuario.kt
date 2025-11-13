package com.example.gestionproduccionesnavegacion.ui.EditarUsuario

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gestionproduccionesnavegacion.databinding.FragmentEditarUsuarioBinding
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditarUsuario : Fragment() {

    private var _binding: FragmentEditarUsuarioBinding? = null
    private val binding get() = _binding!!

    private val editarUsuarioViewModel: EditarUsuarioViewModel by viewModels()

    private val args: EditarUsuarioArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditarUsuarioBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editarUsuarioViewModel.getUsuario(args.id)
        eventos()
        observacion()

    }


    private fun eventos(){
        with(binding){

            buttonProduccionesPendientes.setOnClickListener {
                val action = EditarUsuarioDirections.actionEditarUsuarioToListaTodasProducciones(args.id)
                findNavController().navigate(action)
            }

            buttonVistas.setOnClickListener {
                val action = EditarUsuarioDirections.actionEditarUsuarioToListaTodasProducciones(args.id)
                findNavController().navigate(action)
            }

            botonActualizar.setOnClickListener {
                editarUsuarioViewModel.updateUsuario(crearUsuario())
                findNavController().navigateUp()
            }
        }
    }

    private fun observacion(){
        editarUsuarioViewModel.state.observe(viewLifecycleOwner){state ->
            with(binding){
                EmailAddress.editText?.setText(state.usuario.email)
                usuario.editText?.setText(state.usuario.nombre)
                password.editText?.setText(state.usuario.contraseña)

            }
        }
    }

    private fun crearUsuario(): Usuario {
        val nuevoUsuario = Usuario(
            0,
            binding.usuario.editText?.text.toString(),
            binding.password.editText?.text.toString(),
            binding.EmailAddress.editText?.text.toString(),
        )
        return nuevoUsuario
    }

}
package com.example.gestionproduccionesnavegacion.ui.pantallasUsuarios.listaUsuarios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionproduccionesnavegacion.databinding.FragmentListaUsuariosBinding
import com.example.gestionproduccionesnavegacion.domain.model.Usuario
import com.example.gestionproduccionesnavegacion.ui.common.UiEvent
import com.google.android.material.snackbar.Snackbar
import kotlin.getValue
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListaUsuarios : Fragment() {

    private var _binding: FragmentListaUsuariosBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UsuarioAdapter

    private val listaUsuariosViewModel: ListaUsuariosViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListaUsuariosBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UsuarioAdapter(
            actions = object : UsuarioAdapter.UsuariosActions {
                override fun onItemClick(usuario: Usuario) {
                    infoUsuario(usuario.id)
                }
                override fun onItemSwiped(usuario: Usuario) {
                    listaUsuariosViewModel.borrar(usuario.id)
                }
            }, onClickView = { usuario ->
                // Acción al hacer clic en el elemento (si es necesario)
            })

        binding.recyclerViewUsuarios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUsuarios.adapter = adapter
        binding.recyclerViewUsuarios.setHasFixedSize(true)

        listaUsuariosViewModel.cargarUsuarios()

        deleteSwip()
        eventos()
        observacion()
    }

    private fun eventos() {
        with(binding) {
            buttonAniadir.setOnClickListener {
                val action = ListaUsuariosDirections.Companion.actionListaUsuariosToCrearUsuario()
                findNavController().navigate(action)
            }

        }
    }

    private fun deleteSwip(){
        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
                val pos = vh.bindingAdapterPosition
                if (pos == RecyclerView.NO_POSITION) return
                adapter.getItemAt(pos)?.let { adapter.actions.onItemSwiped(it) }
            }

        })
        touchHelper.attachToRecyclerView(binding.recyclerViewUsuarios)
    }

    override fun onResume() {
        super.onResume()
        listaUsuariosViewModel.cargarUsuarios()
    }

    private fun infoUsuario(id: Int) {
        val action = ListaUsuariosDirections.Companion.actionListaUsuariosToEditarUsuario(id)
        findNavController().navigate(action)
    }


    private fun observacion() {
        listaUsuariosViewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.submitList(state.usuarios)

            state.uiEvent?.let { event ->
                when (event) {
                    is UiEvent.ShowSnackbar -> {
                        val snackbar = Snackbar.make(
                            binding.root,
                            event.message,
                            Snackbar.LENGTH_LONG
                        )
                        event.action?.let {
                            snackbar.setAction(event.action ?: "UNDO") {
                                // Aquí puedes manejar la acción de deshacer si lo necesitas
                            }
                        }
                        snackbar.show()
                        listaUsuariosViewModel.limpiarMensaje()
                    }

                    is UiEvent.Navigate -> TODO()
                    else -> {}
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
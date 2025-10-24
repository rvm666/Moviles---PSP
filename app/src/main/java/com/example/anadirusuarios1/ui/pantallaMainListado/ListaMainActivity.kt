package com.example.anadirusuarios1.ui.pantallaMainListado

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.anadirusuarios1.R
import com.example.anadirusuarios1.data.RepositorioProducciones
import com.example.anadirusuarios1.databinding.ActivityListaMainBinding
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.BorrarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.GetAllProduccionesUseCase
import com.example.anadirusuarios1.ui.common.UiEvent
import com.example.anadirusuarios1.ui.pantallaAniadir.MainActivity
import com.example.anadirusuarios1.ui.pantallaInfo.InfoMainActivity
import com.google.android.material.snackbar.Snackbar

class ListaMainActivity : ComponentActivity() {

    private val listaViewModel: ListaProduccionViewModel by viewModels{
        ListaProduccionViewModel.ListaProduccionViewModelFactory(
            repositorio = RepositorioProducciones,
            getAllProduccionesUseCase = GetAllProduccionesUseCase(RepositorioProducciones),
            borrarProduccionUseCase = BorrarProduccionUseCase(RepositorioProducciones)
        )
    }

    private lateinit var adapter: ProduccionAdapter
    private lateinit var binding: ActivityListaMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        adapter = ProduccionAdapter(
            actions = object : ProduccionAdapter.ProduccionesActions{
                override fun onItemClick(produccion: Produccion){
                    infoProduccion(produccion.nombre)
                }
            }, onClickView = { produccion ->
                // Acción al hacer clic en el elemento (si es necesario)
            })

        binding.recyclerViewProducciones.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProducciones.adapter = adapter

        listaViewModel.state.observe(this) { state ->
            adapter.submitList(state.producciones)
        }
        eventos()
        observacion()
        deleteSwip()
    }

    private fun infoProduccion(nombre: String){
        val intent = Intent(
            this@ListaMainActivity,
            InfoMainActivity::class.java
        )
        intent.putExtra("nombre", nombre)
        startActivity(intent)
        finish()
    }

    private fun deleteSwip(){
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewholder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean = false

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val produccion = adapter.currentList[position]
                    listaViewModel.borrar(produccion)
                }
        })
        itemTouchHelper.attachToRecyclerView(binding.recyclerViewProducciones)
    }

    private fun eventos(){
        with(binding){
            buttonAniadir.setOnClickListener {
                val intent = Intent(
                    this@ListaMainActivity,
                    MainActivity::class.java
                )
                startActivity(intent)
                finish()
            }
        }
    }


    private fun observacion(){
        listaViewModel.state.observe(this) {state ->
            adapter.submitList(state.producciones)
            state.uiEvent?.let{ event ->
                when (event){
                    is UiEvent.ShowSnackbar -> {
                        val snackbar = Snackbar.make(
                            binding.root,
                            event.message,
                            Snackbar.LENGTH_LONG
                        )
                        event.action?.let {
                            snackbar.setAction(event.action ?: "UNDO"){
                                // Aquí puedes manejar la acción de deshacer si lo necesitas
                            }
                        }
                        snackbar.show()
                        listaViewModel.limpiarMensaje()
                    }
                    is UiEvent.Navigate -> TODO()
                    else -> {}
                }
            }
        }
    }
}
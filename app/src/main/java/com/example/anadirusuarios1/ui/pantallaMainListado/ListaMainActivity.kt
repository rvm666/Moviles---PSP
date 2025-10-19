package com.example.anadirusuarios1.ui.pantallaMainListado

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.anadirusuarios1.R
import com.example.anadirusuarios1.data.RepositorioProducciones
import com.example.anadirusuarios1.databinding.ActivityListaMainBinding
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.GetAllProduccionesUseCase
import com.example.anadirusuarios1.domain.useCase.GetProduccionUseCase
import com.example.anadirusuarios1.ui.pantallaAniadir.MainActivity
import com.example.anadirusuarios1.ui.pantallaInfo.InfoMainActivity

class ListaMainActivity : ComponentActivity() {

    private val viewModel: ListaProduccionViewModel by viewModels{
        ListaProduccionViewModel.ListaProduccionViewModelFactory(
            repositorio = RepositorioProducciones,
            getProduccionUseCase = GetProduccionUseCase(RepositorioProducciones),
            getAllProduccionesUseCase = GetAllProduccionesUseCase(RepositorioProducciones)
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

                }
            } , onClickView = this::infoProduccion)

        binding.recyclerViewProducciones.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProducciones.adapter = adapter

        viewModel.state.observe(this) { state ->
            adapter.submitList(state.producciones)
        }
        eventos()
    }

    private fun infoProduccion(produccion: Produccion){
        val intent = android.content.Intent(
            this@ListaMainActivity,
            InfoMainActivity::class.java
        )
        startActivity(intent)
    }

    private fun eventos(){
        with(binding){
            buttonAniadir.setOnClickListener {
                val intent = android.content.Intent(
                    this@ListaMainActivity,
                    MainActivity::class.java
                )
                startActivity(intent)
                finish()
            }
        }
    }
}
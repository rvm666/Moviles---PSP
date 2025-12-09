package com.example.anadirusuarios1.ui.pantallaAniadir

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anadirusuarios1.R
import com.example.anadirusuarios1.data.RepositorioProducciones
import com.example.anadirusuarios1.databinding.ActivityMainBinding
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.AnadirProduccionUseCase
import com.example.anadirusuarios1.ui.common.UiEvent
import com.example.anadirusuarios1.ui.pantallaAniadir.MainViewModel.MainViewModelFactory
import com.example.anadirusuarios1.ui.pantallaMainListado.ListaMainActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.threetenabp.AndroidThreeTen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.getValue
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.LocalDate
import com.example.anadirusuarios1.ui.common.Constantes

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        val repositorio = RepositorioProducciones
        val aniadirProduccion =  AnadirProduccionUseCase(repositorio)
        MainViewModelFactory(aniadirProduccion)
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidThreeTen.init(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.bookingDateEditText.setOnClickListener {
            val datePickerBuilder = MaterialDatePicker.Builder.datePicker()


            datePickerBuilder.setTitleText("Select Date")
            if (binding.bookingDateEditText.text.toString().isNotEmpty()) {
                try {
                    val dateFormat = SimpleDateFormat(Constantes.FORMATO_FECHA, Locale.getDefault())
                    dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                    val parsedDate = dateFormat.parse(binding.bookingDateEditText.text.toString())
                    parsedDate?.let {
                        datePickerBuilder.setSelection(it.time)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            val datePicker = datePickerBuilder.build()
            datePicker.addOnPositiveButtonClickListener { selection ->
                val dateFormat = SimpleDateFormat(Constantes.FORMATO_FECHA, Locale.getDefault())
                dateFormat.timeZone = TimeZone.getTimeZone("UTC")
                val formattedDate = dateFormat.format(Date(selection))
                binding.bookingDateEditText.setText(formattedDate)
            }
            datePicker.addOnNegativeButtonClickListener {
                binding.bookingDateEditText.clearFocus()
            }
            datePicker.addOnDismissListener {
                binding.bookingDateEditText.clearFocus()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        eventos()
        observacion()
    }


    private fun eventos(){
        with(binding) {
            botonGuardar.setOnClickListener {
                viewModel.clickBotonGuardar(crearProduccion())
                root.postDelayed({
                    val intent = Intent(
                        this@MainActivity,
                        ListaMainActivity::class.java
                    )
                    startActivity(intent)
                    finish()
                }, 1500)
            }

            botonLimpiardeGuardar.setOnClickListener {
                viewModel.limpiarPantalla()
            }

            pelicula.setOnCheckedChangeListener { _, isCheked ->
                viewModel.esPelicula(isCheked)
            }

            BotonPatras?.setOnClickListener {
                val intent = Intent(
                    this@MainActivity,
                    ListaMainActivity::class.java
                )
                startActivity(intent)
                finish()
            }

        }
    }



    private fun observacion() {
        val generos = resources.getStringArray(R.array.opcionesGenero)
        val paises = resources.getStringArray(R.array.opcionesPais)
        viewModel.state.observe(this) { state ->
            binding.pelicula.isChecked == state.produccion.esPelicula
            binding.NombrePeli.setText(state.produccion.nombre)
            binding.director.setText(state.produccion.director)
            binding.bookingDateEditText.setText(state.produccion.fechaLanzamiento?.format(
                DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA)) ?:"")
            binding.numeroTemporadas.isEnabled = state.isEnable
            binding.numeroTemporadas.setText(state.produccion.numeroSeason.toString())
            binding.opcionesGenero.setSelection(generos.indexOf(state.produccion.genero))
            binding.opcionesPais.setSelection(paises.indexOf(state.produccion.pais))
            binding.valoracion.rating = state.produccion.valoracion.toFloat()

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
                        viewModel.limpiarMensaje()
                    }
                        is UiEvent.Navigate -> TODO()
                        else -> {}
                }
            }

        }
    }

    private fun crearProduccion(): Produccion {
        val numeroSeasonsTexto = binding.numeroTemporadas.text.toString()
        val lanzamientoTexto = binding.bookingDateEditText.text.toString()
        val formatter = DateTimeFormatter.ofPattern(Constantes.FORMATO_FECHA)

        val nuevaProduccion = Produccion(
            esPelicula = binding.serie.isChecked,
            nombre = binding.NombrePeli.text.toString(),
            director = binding.director.text.toString(),
            numeroSeason = numeroSeasonsTexto.toIntOrNull() ?: 0,
            fechaLanzamiento = LocalDate.parse(lanzamientoTexto, formatter),
            genero = binding.opcionesGenero.selectedItem.toString(),
            pais = binding.opcionesPais.selectedItem.toString(),
            valoracion = binding.valoracion.rating.toDouble()
        )
        return nuevaProduccion
    }
}
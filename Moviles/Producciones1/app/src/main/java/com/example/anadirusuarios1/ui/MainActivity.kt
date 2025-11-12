package com.example.anadirusuarios1.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.anadirusuarios1.R
import com.example.anadirusuarios1.data.RepositorioProducciones
import com.example.anadirusuarios1.databinding.ActivityMainBinding
import com.example.anadirusuarios1.domain.model.Produccion
import com.example.anadirusuarios1.domain.useCase.ActualizarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.AnadirProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.BorrarProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.GetProduccionUseCase
import com.example.anadirusuarios1.domain.useCase.SizeProduccionesUseCase
import com.example.anadirusuarios1.ui.MainViewModel.MainViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import com.jakewharton.threetenabp.AndroidThreeTen
import java.text.SimpleDateFormat
//import java.time.LocalDate
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.getValue
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.LocalDate

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels {
        val repositorio = RepositorioProducciones
        val aniadirProduccion =  AnadirProduccionUseCase(repositorio)
        val getProduccion = GetProduccionUseCase(repositorio)
        val borrarProduccion = BorrarProduccionUseCase(repositorio)
        val sizeProducciones = SizeProduccionesUseCase(repositorio)
        val actualizarProduccion = ActualizarProduccionUseCase(repositorio)
        MainViewModelFactory(aniadirProduccion, getProduccion, borrarProduccion, sizeProducciones, actualizarProduccion)
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
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
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
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
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
        binding.botonGuardar.setOnClickListener {
            val numeroSeasonsTexto = binding.numeroTemporadas.text.toString()
            val lanzamientoTexto = binding.bookingDateEditText.text.toString()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            val nuevaProduccion = Produccion(
                binding.serie.isChecked,
                binding.NombrePeli.text.toString(),
                binding.director.text.toString(),
                numeroSeasonsTexto.toIntOrNull() ?: 0,
                LocalDate.parse(lanzamientoTexto, formatter),
                binding.opcionesGenero.selectedItem.toString(),
                binding.opcionesPais.selectedItem.toString(),
                binding.valoracion.rating.toDouble())
            viewModel.clickBotonGuardar(nuevaProduccion)
        }

        binding.siguiente.setOnClickListener {
            viewModel.siguienteProduccion()
        }

        binding.anterior.setOnClickListener {
            viewModel.anteriorProduccion()
        }

        binding.botonBorrar.setOnClickListener {
            val numeroSeasonsTexto = binding.numeroTemporadas.text.toString()
            val lanzamientoTexto = binding.bookingDateEditText.text.toString()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            val nuevaProduccion = Produccion(
                binding.serie.isChecked,
                binding.NombrePeli.text.toString(),
                binding.director.text.toString(),
                numeroSeasonsTexto.toIntOrNull() ?: 0,
                LocalDate.parse(lanzamientoTexto, formatter),
                binding.opcionesGenero.selectedItem.toString(),
                binding.opcionesPais.selectedItem.toString(),
                binding.valoracion.rating.toDouble())
            viewModel.clickBotonBorrar(nuevaProduccion)
            viewModel.siguienteProduccion()
        }

        binding.botonLimpiar.setOnClickListener {
            viewModel.limpiarPantalla()
        }

        binding.botonActualizar.setOnClickListener {
            val numeroSeasonsTexto = binding.numeroTemporadas.text.toString()
            val lanzamientoTexto = binding.bookingDateEditText.text.toString()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val nuevaProduccion = Produccion(
                binding.serie.isChecked,
                binding.NombrePeli.text.toString(),
                binding.director.text.toString(),
                numeroSeasonsTexto.toIntOrNull() ?: 0,
                LocalDate.parse(lanzamientoTexto, formatter),
                binding.opcionesGenero.selectedItem.toString(),
                binding.opcionesPais.selectedItem.toString(),
                binding.valoracion.rating.toDouble())
            viewModel.actualizarProduccion(nuevaProduccion)
        }

        binding.pelicula.setOnCheckedChangeListener { _, isCheked ->
            viewModel.esPelicula(isCheked)
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
                DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?:"")
            binding.numeroTemporadas.isEnabled = state.isEnable
            binding.numeroTemporadas.setText(state.produccion.numeroSeason.toString())
            binding.opcionesGenero.setSelection(generos.indexOf(state.produccion.genero))
            binding.opcionesPais.setSelection(paises.indexOf(state.produccion.pais))
            binding.valoracion.rating = state.produccion.valoracion.toFloat()

            state.mensaje?.let{ mensaje ->
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
                 viewModel.limpiarMensaje()
            }
        }
    }
}
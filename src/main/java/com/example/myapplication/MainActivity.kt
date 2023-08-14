package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat

abstract class Empleado {
    abstract fun calcularLiquido(montoBruto: Double): Double
}

class EmpleadoHonorario(val montoBruto: Double = 0.0) : Empleado() {
    override fun calcularLiquido(montoBruto: Double): Double {
        return montoBruto - (montoBruto * 0.13)
    }
}

class EmpleadoRegular(val montoBruto: Double = 0.0) : Empleado() {
    override fun calcularLiquido(montoBruto: Double): Double = montoBruto - (montoBruto * 0.20)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnHonorario = findViewById<Button>(R.id.buttonHonorario)
        val btnRegular = findViewById<Button>(R.id.buttonRegular)


        btnHonorario.setOnClickListener {
            val intent = Intent(this@MainActivity, EmpleadoHonorarioActivity::class.java)
            startActivity(intent)
        }

        btnRegular.setOnClickListener {
            val intent = Intent(this@MainActivity, EmpleadoRegularActivity::class.java)
            startActivity(intent)
        }
    }
}



class EmpleadoHonorarioActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculoEmpleadoHonorario()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CalculoEmpleadoHonorario() {
        var pago by remember { mutableStateOf("") }
        var resultado by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Calculo Honorario",
                color = Color.White)
            TextField(
                value = pago,
                onValueChange = { pago = it },
                label = { Text("Pago") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Button(
                onClick = {
                    val pagoNumero = pago.toDoubleOrNull() ?: 0.0
                    val empleado: Empleado = EmpleadoHonorario(pagoNumero)
                    resultado = "El pago es: ${empleado.calcularLiquido(pagoNumero)}"
                }
            ) {
                Text("Resultado Honorario")
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                resultado,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = { volverAtras() }) {
                Text("Volver")
            }
        }
    }

    private fun volverAtras() {
        finish()
    }
}



class EmpleadoRegularActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculoEmpleadoRegular()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CalculoEmpleadoRegular() {
        var pago by remember { mutableStateOf("") }
        var resultado by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Calculo Regular",
                color = Color.White)
            TextField(
                value = pago,
                onValueChange = { pago = it },
                label = { Text("Pago") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Button(onClick = {
                val pagoNumero = pago.toDoubleOrNull() ?: 0.0
                val empleado: Empleado = EmpleadoRegular(pagoNumero)
                resultado = "El pago es: ${empleado.calcularLiquido(pagoNumero)}"
            }) {
                Text("Resultado Regular",
                    color = Color.White)
            }
                Spacer(modifier = Modifier.height(20.dp))
            Text(
                resultado,
                color = Color.White
            )
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { volverAtras() }) {
                    Text("Volver")
                }
            }
        }

    private fun volverAtras() {
        finish()
    }
}

package com.example.restaurantechileno

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantechileno.ui.theme.RestauranteChilenoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestauranteChilenoTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFF0F0F0)),
                    color = Color(0xFFF0F0F0)
                ) {
                    PantallaPrincipal()
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal() {
    // Estados para cantidades
    var cantidadChoclo by remember { mutableStateOf(0) }
    var cantidadPaila by remember { mutableStateOf(0) }
    var agregarPropina by remember { mutableStateOf(false) }

    val precioChoclo = 6500
    val precioPaila = 7500
    val totalComida = (cantidadChoclo * precioChoclo) + (cantidadPaila * precioPaila)
    val propina = if (agregarPropina) (totalComida * 0.1).toInt() else 0
    val totalFinal = totalComida + propina

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.lo_restaurant),
            contentDescription = "Logo Restaurante",
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Pastel de Choclo
        PlatoEnFila(
            nombre = "Pastel de Choclo",
            precio = precioChoclo,
            imagenId = R.drawable.pastel_de_choclo,
            cantidad = cantidadChoclo,
            onCantidadChange = { cantidadChoclo = it }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Paila Marina
        PlatoEnFila(
            nombre = "Paila Marina",
            precio = precioPaila,
            imagenId = R.drawable.paila_marina,
            cantidad = cantidadPaila,
            onCantidadChange = { cantidadPaila = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Total comida
        Text(
            text = "Comida: $$totalComida CLP",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        // Agregar propina
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text("Agregar propina (10%)", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(16.dp))
            Switch(
                checked = agregarPropina,
                onCheckedChange = { agregarPropina = it }
            )
        }

        // Total a pagar
        Text(
            text = "Total a pagar: $$totalFinal CLP",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1B5E20) // verde oscuro
        )
    }
}

@Composable
fun PlatoEnFila(
    nombre: String,
    precio: Int,
    imagenId: Int,
    cantidad: Int,
    onCantidadChange: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imagenId),
            contentDescription = nombre,
            modifier = Modifier
                .size(100.dp)
                .padding(end = 16.dp),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(nombre, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Cantidad:", fontSize = 16.sp)
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = cantidad.toString(),
                    onValueChange = {
                        val nuevoValor = it.toIntOrNull() ?: 0
                        onCantidadChange(nuevoValor)
                    },
                    modifier = Modifier
                        .width(80.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text("Precio: $$precio CLP", fontSize = 16.sp)
        }
    }
}

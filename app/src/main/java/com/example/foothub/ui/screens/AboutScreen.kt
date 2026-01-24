package com.example.foothub.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.foothub.R

@Composable
fun AboutScreen() {

    val context = LocalContext.current

    // Animación suave del logo (misma estructura que WeAnime)
    val logoScale by rememberInfiniteTransition().animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Fondo degradado adaptado a FootHub
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF7DCCF6),
                        Color(0xFFCC5E34)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            // LOGO FootHub con animación
            Image(
                painter = painterResource(id = R.drawable.ic_fh_logo),
                contentDescription = "Logo FootHub",
                modifier = Modifier
                    .size(160.dp)
                    .graphicsLayer(
                        scaleX = logoScale,
                        scaleY = logoScale
                    )
            )

            Spacer(modifier = Modifier.height(28.dp))

            // TÍTULO
            Text(
                text = "FootHub",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // DESCRIPCIÓN
            Text(
                text = "Repositorio de fútbol con datos de equipos y jugadores.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Versión 3.2",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(40.dp))

            // CONTACTO POR EMAIL
            IconButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:")
                        putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf("pacosnchz@icloud.com", "developer@foothub.com")
                        )
                        putExtra(
                            Intent.EXTRA_SUBJECT,
                            "Información sobre FootHub"
                        )
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Hola, quiero saber más información sobre FootHub."
                        )
                    }
                    context.startActivity(intent)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Contacto",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(46.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Contacto",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

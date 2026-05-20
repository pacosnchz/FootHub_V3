package com.example.foothub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.foothub.R
import com.example.foothub.model.Player
import com.example.foothub.viewmodel.FavoritesViewModel
import com.example.foothub.viewmodel.ProfileViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PlayerDetailScreen(
    player: Player,
    modifier: Modifier = Modifier,
    favoritesViewModel: FavoritesViewModel = viewModel(),
    profileViewModel: ProfileViewModel     = viewModel()
) {
    val comments  by favoritesViewModel.comments.collectAsStateWithLifecycle()
    val prefs     by profileViewModel.preferences.collectAsStateWithLifecycle()

    var showCommentDialog by remember { mutableStateOf(false) }

    // Carga comentarios de este jugador
    LaunchedEffect(player.id) { favoritesViewModel.selectPlayer(player.id) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCommentDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.action_add_comment))
            }
        }
    ) { padding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

            // Imagen / escudo del equipo
            AsyncImage(
                model = player.photoUrl,
                contentDescription = player.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier.padding(20.dp)) {

                Text(
                    text = player.name,
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(Modifier.height(16.dp))

                // Estadísticas clave
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatChip(label = stringResource(R.string.stat_goals),     value = player.goals.toString())
                    StatChip(label = stringResource(R.string.stat_assists),   value = player.assists.toString())
                    StatChip(label = stringResource(R.string.stat_penalties), value = player.penalties.toString())
                }

                Spacer(Modifier.height(20.dp))

                // Datos personales
                InfoRow(stringResource(R.string.label_team),        player.team)
                InfoRow(stringResource(R.string.label_position),    player.position)
                InfoRow(stringResource(R.string.label_nationality), player.nationality)
                if (player.dateOfBirth.isNotBlank()) {
                    InfoRow(stringResource(R.string.label_dob), player.dateOfBirth)
                }

                Spacer(Modifier.height(24.dp))
                HorizontalDivider()
                Spacer(Modifier.height(12.dp))

                // Sección de comentarios
                Text(
                    text = stringResource(R.string.label_comments),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))

                if (comments.isEmpty()) {
                    Text(
                        text = stringResource(R.string.hint_no_comments),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    comments.forEach { comment ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = comment.author,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                    Text(
                                        text = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
                                            .format(Date(comment.timestamp)),
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(text = comment.text, fontSize = 14.sp)
                            }
                        }
                    }
                }

                // Espacio extra para el FAB
                Spacer(Modifier.height(80.dp))
            }
        }
    }

    // Dialog para añadir comentario
    if (showCommentDialog) {
        AddCommentDialog(
            authorName = prefs.username,
            onDismiss  = { showCommentDialog = false },
            onConfirm  = { text ->
                val author = prefs.username.ifBlank { "Anónimo" }
                favoritesViewModel.addComment(player.id, author, text)
                showCommentDialog = false
            }
        )
    }
}

@Composable
private fun StatChip(label: String, value: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = value, fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label: ",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.width(130.dp)
        )
        Text(text = value, fontSize = 16.sp)
    }
}

@Composable
private fun AddCommentDialog(
    authorName: String,
    onDismiss:  () -> Unit,
    onConfirm:  (String) -> Unit
) {
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.dialog_add_comment_title)) },
        text  = {
            Column {
                if (authorName.isBlank()) {
                    Text(
                        text  = stringResource(R.string.hint_set_username_first),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(8.dp))
                }
                OutlinedTextField(
                    value         = text,
                    onValueChange = { text = it },
                    label         = { Text(stringResource(R.string.hint_comment)) },
                    maxLines      = 4,
                    modifier      = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick  = { if (text.isNotBlank()) onConfirm(text) },
                enabled  = text.isNotBlank()
            ) { Text(stringResource(R.string.action_publish)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.action_cancel)) }
        }
    )
}
package com.example.foothub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foothub.R
import com.example.foothub.datastore.AppTheme
import com.example.foothub.ui.theme.TextSecondary
import com.example.foothub.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = viewModel()
) {
    val prefs by profileViewModel.preferences.collectAsStateWithLifecycle()
    var editingName by remember(prefs.username) { mutableStateOf(prefs.username) }
    var nameEditMode by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFCC5E34), Color.White))),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth(0.9f).padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Avatar
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(52.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Nombre editable
                if (nameEditMode) {
                    OutlinedTextField(
                        value = editingName,
                        onValueChange = { editingName = it },
                        label = { Text(stringResource(R.string.label_username)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(
                            onClick = { nameEditMode = false; editingName = prefs.username },
                            modifier = Modifier.weight(1f)
                        ) { Text(stringResource(R.string.action_cancel)) }
                        Button(
                            onClick = {
                                profileViewModel.saveUsername(editingName.trim())
                                nameEditMode = false
                            },
                            modifier = Modifier.weight(1f)
                        ) { Text(stringResource(R.string.action_save)) }
                    }
                } else {
                    Text(
                        text = prefs.username.ifBlank { stringResource(R.string.label_guest) },
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (prefs.username.isNotBlank())
                            "@${prefs.username.lowercase().replace(" ","_")}"
                        else stringResource(R.string.hint_login_prompt),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(onClick = { nameEditMode = true }) {
                        Text(stringResource(R.string.action_edit_name))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(20.dp))

                // Selector de tema
                Text(
                    text = stringResource(R.string.label_theme),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                val themeOptions = listOf(
                    AppTheme.LIGHT  to stringResource(R.string.theme_light),
                    AppTheme.DARK   to stringResource(R.string.theme_dark),
                    AppTheme.SYSTEM to stringResource(R.string.theme_system)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    themeOptions.forEach { (theme, label) ->
                        FilterChip(
                            selected = prefs.theme == theme,
                            onClick  = { profileViewModel.saveTheme(theme) },
                            label    = { Text(label) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}
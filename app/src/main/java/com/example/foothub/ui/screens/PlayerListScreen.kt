package com.example.foothub.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import com.example.foothub.ui.components.FootHubWideHeader
import com.example.foothub.ui.components.PlayerCard
import com.example.foothub.viewmodel.FavoritesViewModel
import com.example.foothub.viewmodel.PlayerViewModel
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
                .background(Color(0xFFFFF8F0))
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {

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
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF1C1C1C)
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatChip(label = stringResource(R.string.stat_goals),     value = player.goals.toString())
                    StatChip(label = stringResource(R.string.stat_assists),   value = player.assists.toString())
                    StatChip(label = stringResource(R.string.stat_penalties), value = player.penalties.toString())
                }

                Spacer(Modifier.height(20.dp))

                InfoRow(stringResource(R.string.label_team),        player.team)
                InfoRow(stringResource(R.string.label_position),    player.position)
                InfoRow(stringResource(R.string.label_nationality), player.nationality)
                if (player.dateOfBirth.isNotBlank()) {
                    InfoRow(stringResource(R.string.label_dob), player.dateOfBirth)
                }

                Spacer(Modifier.height(24.dp))
                HorizontalDivider(color = Color(0xFFCCBBAA))
                Spacer(Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.label_comments),
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF1C1C1C)
                )
                Spacer(Modifier.height(8.dp))

                if (comments.isEmpty()) {
                    Text(
                        text = stringResource(R.string.hint_no_comments),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6E6E6E)
                    )
                } else {
                    comments.forEach { comment ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFEDE8E0)
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
                                        fontSize = 13.sp,
                                        color = Color(0xFF1C1C1C)
                                    )
                                    Text(
                                        text = SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault())
                                            .format(Date(comment.timestamp)),
                                        fontSize = 11.sp,
                                        color = Color(0xFF6E6E6E)
                                    )
                                }
                                Spacer(Modifier.height(4.dp))
                                Text(text = comment.text, fontSize = 14.sp, color = Color(0xFF1C1C1C))
                            }
                        }
                    }
                }

                Spacer(Modifier.height(80.dp))
            }
        }
    }

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

@Composable
fun PlayerListScreen(
    onPlayerClick: (Player) -> Unit,
    onInfoClick: () -> Unit,
    onShowToast: (String) -> Unit,
    modifier: Modifier = Modifier,
    playerViewModel: PlayerViewModel = viewModel()
) {
    val uiState     by playerViewModel.uiState.collectAsStateWithLifecycle()
    val favoriteIds by playerViewModel.favoriteIds.collectAsStateWithLifecycle()

    var searchQuery by remember { mutableStateOf("") }

    val leagues = listOf(
        "PL" to "Premier League",
        "PD" to "LaLiga",
        "BL1" to "Bundesliga",
        "SA" to "Serie A",
        "FL1" to "Ligue 1"
    )
    var selectedLeague by remember { mutableStateOf("PL") }
    var leagueExpanded by remember { mutableStateOf(false) }

    val filteredPlayers = uiState.players.filter { player ->
        searchQuery.isBlank() ||
                player.name.contains(searchQuery, ignoreCase = true) ||
                player.team.contains(searchQuery, ignoreCase = true) ||
                player.nationality.contains(searchQuery, ignoreCase = true)
    }.map { it.copy(isFavorite = it.id in favoriteIds) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8F0)
            )
            .systemBarsPadding()
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            uiState.errorMsg != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(R.string.error_loading))
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { playerViewModel.loadPlayers() }) {
                        Text(stringResource(R.string.action_retry))
                    }
                }
            }
            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    Spacer(Modifier.height(76.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 4.dp)
                    ) {
                        OutlinedButton(
                            onClick = { leagueExpanded = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(leagues.first { it.first == selectedLeague }.second)
                        }
                        DropdownMenu(
                            expanded = leagueExpanded,
                            onDismissRequest = { leagueExpanded = false }
                        ) {
                            leagues.forEach { (code, name) ->
                                DropdownMenuItem(
                                    text = { Text(name) },
                                    onClick = {
                                        if (selectedLeague != code) {
                                            selectedLeague = code
                                            playerViewModel.loadPlayers(code)
                                        }
                                        leagueExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text(stringResource(R.string.hint_search)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp)
                    )
                    LazyColumn(
                        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredPlayers, key = { it.id }) { player ->
                            PlayerCard(
                                name            = player.name,
                                team            = player.team,
                                nationality     = player.nationality,
                                photoUrl        = player.photoUrl,
                                isFavorite      = player.isFavorite,
                                onCardClick     = { onPlayerClick(player) },
                                onFavoriteClick = {
                                    playerViewModel.toggleFavorite(player) {
                                        onShowToast(player.name)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }

        FootHubWideHeader(
            title = stringResource(R.string.screen_list),
            onInfoClick = onInfoClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(horizontal = 20.dp)
                .padding(top = 12.dp)
        )
    }
}
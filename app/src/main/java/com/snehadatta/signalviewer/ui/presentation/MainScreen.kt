package com.snehadatta.signalviewer.ui.presentation

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.snehadatta.signalviewer.constant.NavItem
import org.koin.androidx.compose.koinViewModel

val ScreenBg    = Color(0xFF010107)
val TopBarBg    = Color(0xFF13162A)
val Blue  = Color(0xFF5913D3)
val Cyan  = Color(0xFF996EE5)

private val limitOptions = listOf(10,20,50,100)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    var dropdownExpanded by remember { mutableStateOf(false) }
    var selectedLimit   by remember { mutableStateOf(limitOptions[0]) }

    LaunchedEffect(selectedLimit) {
        viewModel.fetchPosts(selectedLimit)
    }

    Scaffold(
        containerColor = ScreenBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TopBarBg
                ),
                title = {
                    Text(
                        text = "Signal Viewer",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            brush = Brush.linearGradient(
                                listOf(Blue, Cyan)
                            )
                        )
                    )
                },
                actions = {
                    Box {
                        OutlinedButton(
                            onClick = { dropdownExpanded = true },
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Cyan
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp, Cyan.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.height(34.dp)
                        ) {
                            Text(
                                text = "Limit: $selectedLimit",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Select limit",
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false },
                            modifier = Modifier
                                .background(Color(0xFF1A1D2E))
                                .border(
                                    1.dp,
                                    Cyan.copy(alpha = 0.3f),
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            limitOptions.forEach { limit ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = "$limit posts",
                                            color = if (limit == selectedLimit) Cyan
                                                    else Color(0xFFB0B8D0),
                                            fontWeight = if (limit == selectedLimit)
                                                FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    onClick = {
                                        selectedLimit    = limit
                                        dropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    Spacer(Modifier.width(8.dp))
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(ScreenBg)
        ) {
            AnimatedVisibility(
                visible = !state.posts.isNullOrEmpty(),
                enter = fadeIn() + slideInVertically()
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    val posts = state.posts ?: emptyList()
                    items(posts.size) { i ->
                        SignalRow(post = posts[i]) {
                            navController.navigate("${NavItem.DETAIL.route}/${posts[i].id}")
                        }
                    }
                }
            }

            if (state.isLoading == true) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        color = Cyan,
                        trackColor = Cyan.copy(alpha = 0.15f)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Fetching signals…",
                        color = Color(0xFF7B8099),
                        fontSize = 13.sp
                    )
                }
            }
            state.error?.let { error ->
                if (!state.isLoading!!) {
                    Text(
                        text = error,
                        color = Color(0xFFFF4E6A),
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp)
                    )
                }
            }
        }
    }
}

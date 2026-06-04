package com.snehadatta.signalviewer.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

private val BuyGreen = Color(0xFF15CC9F)
private val SellRed  = Color(0xFFD71332)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    postId: Int?,
    navController: NavController,
    viewModel: MainViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsState().value
    val post  = state.posts?.find { it.id == postId }

    val isBuy       = (post?.id ?: 0) % 2 == 0
    val signalColor = if (isBuy) BuyGreen else SellRed
    val signalLabel = if (isBuy) "BUY SIGNAL" else "SELL SIGNAL"

    Scaffold(
        containerColor = ScreenBg,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TopBarBg
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Blue.copy(alpha = 0.12f))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Blue
                        )
                    }
                },
                title = {
                    Text(
                        text = "Signal Detail",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            brush = Brush.linearGradient(
                                listOf(Blue, Cyan)
                            )
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
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
            if (post == null) {
                CircularProgressIndicator(
                    color = Cyan,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .background(
                                color  = signalColor.copy(alpha = 0.12f),
                                shape  = RoundedCornerShape(12.dp)
                            )
                            .border(
                                1.dp,
                                signalColor.copy(alpha = 0.5f),
                                RoundedCornerShape(12.dp)
                            )
                            .padding(horizontal = 24.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = signalLabel,
                            color = signalColor,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 15.sp,
                            letterSpacing = 1.2.sp
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF291350),
                        tonalElevation = 0.dp,
                        shadowElevation = 6.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "TITLE",
                                color = signalColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.5.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = post.title.replaceFirstChar { it.uppercase() },
                                color = Color(0xFFE8EAF6),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 26.sp
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFF2B194D),
                        tonalElevation = 0.dp,
                        shadowElevation = 6.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                text = "DESCRIPTION",
                                color = signalColor,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.5.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = post.body,
                                color = Color(0xFFB0B8D0),
                                fontSize = 15.sp,
                                lineHeight = 24.sp,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}

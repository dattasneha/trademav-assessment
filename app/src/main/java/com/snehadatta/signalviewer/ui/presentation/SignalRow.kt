package com.snehadatta.signalviewer.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snehadatta.data.model.Post

private val Green  = Color(0xFF0DD3A1)
private val Red   = Color(0xFFE0052A)
private val CardBg    = Color(0xFF1F0D3F)

@Composable
fun SignalRow(
    post: Post,
    onClick: () -> Unit
) {
    val isBuy = post.id % 2 == 0
    val signalColor = if (isBuy) Green else Red
    val signalText  = if (isBuy) "BUY"   else "SELL"

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = CardBg,
        shadowElevation = 8.dp
    ) {
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        listOf(signalColor.copy(alpha = 0.6f), CardBg)
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(12.dp)

            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color.White
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 12.sp,
                        color = Color(0xFF7B8099)
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = signalColor.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = signalColor.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = signalText,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = signalColor,
                            letterSpacing = 0.8.sp
                        )
                    )
                }
            }
        }
    }
}

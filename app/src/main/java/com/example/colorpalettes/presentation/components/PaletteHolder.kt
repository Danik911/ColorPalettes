package com.example.colorpalettes.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.presentation.extractColor
import com.example.colorpalettes.presentation.hexToColor
import com.example.colorpalettes.ui.theme.PADDING_EXTRA_LARGE
import com.example.colorpalettes.ui.theme.PADDING_MEDIUM

@Composable
fun PaletteHolder(
    colorPalette: ColorPalette,
    onClick: () -> Unit
) {
    val colors = remember {
        mutableStateListOf<String>()
    }
    LaunchedEffect(key1 = colorPalette) {
        colors.clear()
        colors.addAll(extractColor(colorPalette = colorPalette))
    }
    Box(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .clickable(colorPalette.approved) { onClick() },
        contentAlignment = Alignment.BottomEnd
    ) {
        ColorPaletteComposable(colors = colors)
    }
}

@Composable
fun ColorPaletteComposable(colors: List<String>) {
    val width = remember {
        mutableStateOf(0)
    }
    val weight = remember {
        if (width.value != 0) (colors.size / width.value).toFloat() else 1f
    }
    Row(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                width.value = it.size.width
            }
            .clip(RoundedCornerShape(size = PADDING_EXTRA_LARGE))
    ) {
        colors.forEach { colorHex->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = weight)
                    .background(color = hexToColor(colorHex))
            )
        }
    }
}

@Composable
fun NumberOfLikes(number: Int) {
    Surface(
        modifier = Modifier
            .padding(end = PADDING_MEDIUM, bottom = PADDING_MEDIUM),
        shape = RoundedCornerShape(size = 50.dp),
        color = Color.Black.copy(alpha = ContentAlpha.disabled)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = PADDING_MEDIUM)
                .padding(vertical = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Heart Icon",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "$number",
                color = Color.White
            )
        }
    }
}

@Composable
fun WaitingForApproval() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(size = 20.dp))
            .background(Color.Black.copy(alpha = ContentAlpha.medium)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.HourglassBottom,
            contentDescription = "Hourglass Icon",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "Waiting for approval",
            color = Color.White
        )
    }
}









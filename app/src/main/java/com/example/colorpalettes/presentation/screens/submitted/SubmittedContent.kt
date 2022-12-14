package com.example.colorpalettes.presentation.screens.submitted

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.colorpalettes.R.drawable
import com.example.colorpalettes.R.string
import com.example.colorpalettes.domain.model.ColorPalette
import com.example.colorpalettes.domain.model.RequestState
import com.example.colorpalettes.presentation.components.DefaultContent
import com.example.colorpalettes.ui.theme.InfoGreen

@Composable
fun SubmittedContent(
    navHostController: NavHostController,
    submittedColors: List<ColorPalette>,
    requestState: RequestState,
    onSubmitClicked: () -> Unit
) {

        if (requestState is RequestState.Success || requestState is RequestState.Error) {
            if (submittedColors.isEmpty()) {
                SubmitView(onSubmitClicked = onSubmitClicked)
            } else {
                DefaultContent(
                    navController = navHostController,
                    colorPalettes = submittedColors,
                    showFab = false
                )
            }
        }
    }

@Composable
fun SubmitView(onSubmitClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            painter = painterResource(id = drawable.colorpalette),
            contentDescription = "Color Palette Image"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = stringResource(id = string.submit_palette),
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h5.fontSize
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(id = string.submit_description),
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth(),
            onClick = onSubmitClicked,
            colors = ButtonDefaults.buttonColors(backgroundColor = InfoGreen)
        ) {
            Icon(
                imageVector = Icons.Filled.Palette,
                contentDescription = "Color Palette Icon",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = stringResource(id = string.submit_button), color = Color.White)
        }
    }
}

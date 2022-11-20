package com.example.colorpalettes.presentation.screens.create

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import com.example.colorpalettes.ui.theme.topAppBarBackgroundColor
import com.example.colorpalettes.ui.theme.topAppBarContentColor

@Composable
fun CreateTopBar(onBackClicked: () -> Unit) {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Arrow Icon",
                    tint = MaterialTheme.colors.topAppBarContentColor
                )
            }
        },
        title = {
            Text(
                text = "Create Palette",
                color = MaterialTheme.colors.topAppBarContentColor
            )
        }
    )
}
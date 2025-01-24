package com.example.vistavault.presentation.util

import androidx.compose.material3.SnackbarDuration
import java.time.Duration

data class SnackbarEvent(
    val message: String,
    val duration: SnackbarDuration = SnackbarDuration.Short
)

package com.example.vistavault.domain.model

sealed class NetworkStatus {
    data object Connected: NetworkStatus()

    data object Disconnected: NetworkStatus()
}
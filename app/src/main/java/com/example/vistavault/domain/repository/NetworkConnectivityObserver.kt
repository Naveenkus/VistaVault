package com.example.vistavault.domain.repository

import com.example.vistavault.domain.model.NetworkStatus
import kotlinx.coroutines.flow.StateFlow

interface NetworkConnectivityObserver {
    val networkStatus : StateFlow<NetworkStatus>
}
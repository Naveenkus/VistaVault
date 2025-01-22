package com.example.vistavault.domain.repository

interface Downloader {

    fun downloadFile(url: String, fileName: String? )
}
package com.example.vistavault.data.mapper

import com.example.vistavault.data.remote.dto.UnsplashImageDto
import com.example.vistavault.domain.UnsplashImage

fun UnsplashImageDto.toDomainModel(): UnsplashImage{
    return UnsplashImage(
        id = this.id,
        imageUrlSmall = this.urls.small,
        imageUrlRegular = this.urls.regular,
        imageUrlRaw = this.urls.raw,
        photographerName = this.user.name,
        photographerUserName = this.user.username,
        photographerProfielImageUrl = this.user.profile_image.small,
        photographerProfileLink = this.user.links.html,
        width = this.width,
        height = this.height,
        description = description
    )
}

fun List<UnsplashImageDto>.toDomainModelList(): List<UnsplashImage> {
    return this.map { it.toDomainModel() }
}
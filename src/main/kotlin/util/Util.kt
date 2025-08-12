package com.schoolarium.util

fun String.isValidImageType(): Boolean {
    return this in listOf(
        "image/jpeg", "image/jpg", "image/png",
        "image/gif", "image/webp", "image/bmp"
    )
}

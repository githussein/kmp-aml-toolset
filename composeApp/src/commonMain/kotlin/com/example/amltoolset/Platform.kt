package com.example.amltoolset

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
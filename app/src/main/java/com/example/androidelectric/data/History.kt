package com.example.androidelectric.data.model

import java.util.*

/**
 * Data class that captures user information
 */
data class Reading (
    val userId: String,
    val time: String,
    val voltage: String,
    val current: String,
    val power: String,
    val frequency: String,
    val energy: String
)

data class MeterReading(
    val Key: String,
    val Record : Reading
)

data class ServerAddress(
    val ip: String,
    val name: String
)

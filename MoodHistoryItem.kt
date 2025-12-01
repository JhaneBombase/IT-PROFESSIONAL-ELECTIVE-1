package com.example.moodtracker

data class MoodHistoryItem(
    val name: String,
    val imageRes: Int,
    val reason: String,
    val date: String,
    var isSelected: Boolean = false   // new
)

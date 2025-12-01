package com.example.moodtracker

data class Mood(
    val name: String,
    val imageRes: Int   // <-- This is the property your MainActivity is trying to access
)

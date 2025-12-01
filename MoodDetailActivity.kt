package com.example.moodtracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.moodtracker.databinding.ActivityMoodDetailBinding

class MoodDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoodDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moodName = intent.getStringExtra("moodName")
        val moodIcon = intent.getIntExtra("moodIcon", 0)

        binding.moodIcon.setImageResource(moodIcon)
        binding.moodTitle.text = moodName

        binding.saveButton.setOnClickListener {
            val reason = binding.reasonInput.text.toString()
            val prefs = getSharedPreferences("moodPrefs", MODE_PRIVATE).edit()
            prefs.putString("mood", moodName)
            prefs.putString("reason", reason)
            prefs.apply()
            finish()
        }
    }
}

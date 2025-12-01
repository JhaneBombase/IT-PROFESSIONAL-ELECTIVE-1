package com.example.moodtracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moodtracker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val moodList = listOf(
        Mood("Happy", R.drawable.happy),
        Mood("Excited", R.drawable.excited),
        Mood("Calm", R.drawable.calm),
        Mood("Sad", R.drawable.sad),
        Mood("Stressed", R.drawable.stressed),
        Mood("Angry", R.drawable.angry)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        binding.moodRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.moodRecyclerView.adapter = MoodAdapter(moodList) { mood ->
            openReasonScreen(mood)
        }

        // History button listener — ALWAYS active
        binding.historyButton.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }


    private fun openReasonScreen(mood: Mood) {
        val intent = Intent(this, ReasonActivity::class.java)
        intent.putExtra("moodName", mood.name)
        intent.putExtra("moodImage", mood.imageRes)
        startActivity(intent)
    }
}

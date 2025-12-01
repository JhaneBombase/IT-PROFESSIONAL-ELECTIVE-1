package com.example.moodtracker

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moodtracker.databinding.ActivityReasonBinding
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ReasonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReasonBinding
    private var selectedMoodName: String? = null
    private var selectedMoodImage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReasonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedMoodName = intent.getStringExtra("moodName")
        selectedMoodImage = intent.getIntExtra("moodImage", 0)

        binding.selectedMoodText.text = selectedMoodName
        binding.selectedMoodImage.setImageResource(selectedMoodImage)

        binding.saveReasonButton.setOnClickListener {
            saveMood()
            finish()
        }
    }

    private fun saveMood() {
        val reason = binding.reasonInput.text.toString().trim()
        val moodName = selectedMoodName ?: return

        val date = SimpleDateFormat("MMM dd, yyyy | hh:mm a", Locale.getDefault()).format(Date())

        val moodObject = JSONObject().apply {
            put("name", moodName)
            put("image", selectedMoodImage)
            put("reason", reason)
            put("date", date)
        }

        val prefs = getSharedPreferences("mood_prefs", Context.MODE_PRIVATE)
        val existingData = prefs.getString("history", "[]")

        val jsonArray = JSONArray(existingData)
        jsonArray.put(moodObject)

        prefs.edit().putString("history", jsonArray.toString()).apply()

        Toast.makeText(this, "Mood Saved!", Toast.LENGTH_SHORT).show()
    }
}

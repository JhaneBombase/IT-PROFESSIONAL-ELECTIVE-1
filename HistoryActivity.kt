package com.example.moodtracker

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moodtracker.databinding.ActivityHistoryBinding
import org.json.JSONArray
import org.json.JSONException

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter
    private val historyList = mutableListOf<MoodHistoryItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = HistoryAdapter(historyList) {
            updateCancelButtonVisibility()
        }

        binding.historyRecycler.layoutManager = LinearLayoutManager(this)
        binding.historyRecycler.adapter = adapter

        loadHistory()

        binding.deleteButton.setOnClickListener {
            val selectedItems = adapter.getSelectedItems()
            if (selectedItems.isEmpty()) {
                Toast.makeText(this, "Select at least one item to delete", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            AlertDialog.Builder(this)
                .setTitle("Delete Confirmation")
                .setMessage("Are you sure you want to delete ${selectedItems.size} item(s)?")
                .setPositiveButton("Yes") { _, _ -> deleteSelectedItems(selectedItems) }
                .setNegativeButton("Cancel", null)
                .show()
        }

        binding.selectAllButton.setOnClickListener {
            historyList.forEach { it.isSelected = true }
            adapter.notifyDataSetChanged()
            updateCancelButtonVisibility()
        }

        binding.cancelButton.setOnClickListener {
            historyList.forEach { it.isSelected = false }
            adapter.notifyDataSetChanged()
            updateCancelButtonVisibility()
        }

        updateCancelButtonVisibility()
    }

    private fun updateCancelButtonVisibility() {
        binding.cancelButton.visibility = if (historyList.any { it.isSelected }) View.VISIBLE else View.INVISIBLE
    }

    private fun loadHistory() {
        historyList.clear()
        val prefs = getSharedPreferences("mood_prefs", Context.MODE_PRIVATE)
        val jsonString = prefs.getString("history", "[]")
        if (!jsonString.isNullOrEmpty()) {
            try {
                val jsonArray = JSONArray(jsonString)
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    historyList.add(
                        MoodHistoryItem(
                            name = obj.optString("name", "Unknown"),
                            imageRes = obj.optInt("image", 0),
                            reason = obj.optString("reason", ""),
                            date = obj.optString("date", ""),
                            isSelected = false
                        )
                    )
                }
            } catch (e: JSONException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load history", Toast.LENGTH_SHORT).show()
            }
        }

        adapter.notifyDataSetChanged()
        updateCancelButtonVisibility()
    }

    private fun deleteSelectedItems(selectedItems: List<MoodHistoryItem>) {
        val prefs = getSharedPreferences("mood_prefs", Context.MODE_PRIVATE)
        val jsonString = prefs.getString("history", "[]")
        if (jsonString.isNullOrEmpty()) return
        try {
            val jsonArray = JSONArray(jsonString)
            val remainingArray = JSONArray()
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val name = obj.optString("name", "")
                val date = obj.optString("date", "")
                if (selectedItems.none { it.name == name && it.date == date }) {
                    remainingArray.put(obj)
                }
            }
            prefs.edit().putString("history", remainingArray.toString()).apply()
            loadHistory()
            Toast.makeText(this, "${selectedItems.size} item(s) deleted", Toast.LENGTH_SHORT).show()
        } catch (e: JSONException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to delete items", Toast.LENGTH_SHORT).show()
        }
    }
}

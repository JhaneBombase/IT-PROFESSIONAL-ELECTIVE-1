package com.example.moodtracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.databinding.ItemHistoryBinding

class HistoryAdapter(
    private val historyList: List<MoodHistoryItem>,
    private val onSelectionChanged: () -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]

        holder.binding.historyMoodImage.setImageResource(item.imageRes)
        holder.binding.historyMoodName.text = item.name
        holder.binding.historyMoodReason.text = item.reason
        holder.binding.historyMoodDate.text = item.date

        holder.binding.historyCheckBox.setOnCheckedChangeListener(null)
        holder.binding.historyCheckBox.isChecked = item.isSelected
        holder.binding.historyCheckBox.setOnCheckedChangeListener { _, isChecked ->
            item.isSelected = isChecked
            onSelectionChanged()
        }

        holder.binding.root.setOnClickListener {
            item.isSelected = !item.isSelected
            holder.binding.historyCheckBox.isChecked = item.isSelected
            onSelectionChanged()
        }
    }

    override fun getItemCount(): Int = historyList.size

    fun getSelectedItems(): List<MoodHistoryItem> = historyList.filter { it.isSelected }
}

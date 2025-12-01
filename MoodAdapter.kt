package com.example.moodtracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moodtracker.databinding.ItemMoodBinding

class MoodAdapter(
    private val moodList: List<Mood>,
    private val onMoodClick: (Mood) -> Unit
) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

    inner class MoodViewHolder(val binding: ItemMoodBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val binding = ItemMoodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        val mood = moodList[position]

        holder.binding.moodText.text = mood.name
        holder.binding.moodImage.setImageResource(mood.imageRes)

        holder.itemView.setOnClickListener {
            onMoodClick(mood)
        }
    }

    override fun getItemCount(): Int = moodList.size
}

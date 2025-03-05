package com.example.damlaalcay_hw2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(
    private val context: Context,
    private val onDeleteClick: (Exercise) -> Unit,
    private val onUpdateClick: (Exercise) -> Unit
) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    private var exercises = emptyList<Exercise>()

    fun setData(items: List<Exercise>) {
        exercises = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(context)
        val itemView: View = inflater.inflate(R.layout.item_exercise, viewGroup, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.tvExerciseName.text = exercise.name
        holder.tvExerciseDuration.text = "Duration: ${exercise.duration} mins"
        holder.tvExerciseDate.text = "Date: ${exercise.date}"

        // Apply different background color for even and odd positions
        val backgroundColor = if (position % 2 == 0) {
            context.getColor(R.color.evenBackground) // Even position background color
        } else {
            context.getColor(R.color.oddBackground) // Odd position background color
        }

        holder.itemView.setBackgroundColor(backgroundColor)

        holder.btnUpdate.setOnClickListener {
            onUpdateClick(exercise)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(exercise)
        }
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    inner class ExerciseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvExerciseName: TextView = itemView.findViewById(R.id.tvExerciseName)
        val tvExerciseDuration: TextView = itemView.findViewById(R.id.tvExerciseDuration)
        val tvExerciseDate: TextView = itemView.findViewById(R.id.tvExerciseDate)
        val btnUpdate: Button = itemView.findViewById(R.id.btnUpdateExercise)
        val btnDelete: Button = itemView.findViewById(R.id.btnDeleteExercise)
    }
}

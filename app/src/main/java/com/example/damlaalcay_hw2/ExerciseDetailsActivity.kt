package com.example.damlaalcay_hw2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ExerciseDetailsActivity : AppCompatActivity() {

    private lateinit var exerciseDao: ExerciseDao
    private var exerciseId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_details)

        val database = AppDatabase.getDatabase(this)
        exerciseDao = database.exerciseDao()

        exerciseId = intent.getIntExtra("exerciseId", -1)

        if (exerciseId != -1) {
            loadExerciseDetails(exerciseId)
        } else {
            Toast.makeText(this, "Invalid exercise ID", Toast.LENGTH_SHORT).show()
            finish() // Eğer ID geçersizse aktiviteyi kapat
        }

        findViewById<Button>(R.id.btnDeleteExercise).setOnClickListener {
            deleteExercise(exerciseId)
        }
    }

    private fun loadExerciseDetails(exerciseId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val exercise = exerciseDao.getExerciseById(exerciseId)
            withContext(Dispatchers.Main) {
                exercise?.let {
                    findViewById<TextView>(R.id.tvExerciseName).text = it.name
                    findViewById<TextView>(R.id.tvExerciseDuration).text = "Duration: ${it.duration} mins"
                    findViewById<TextView>(R.id.tvExerciseDate).text = "Date: ${it.date}"
                } ?: run {
                    Toast.makeText(this@ExerciseDetailsActivity, "Exercise not found", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun deleteExercise(exerciseId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            exerciseDao.deleteExerciseById(exerciseId)
            withContext(Dispatchers.Main) {
                Toast.makeText(this@ExerciseDetailsActivity, "Exercise deleted!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}

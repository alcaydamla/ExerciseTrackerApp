package com.example.damlaalcay_hw2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateExerciseActivity : AppCompatActivity() {

    private lateinit var exerciseDao: ExerciseDao
    private lateinit var exercise: Exercise

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_exercise)

        val database = AppDatabase.getDatabase(this)
        exerciseDao = database.exerciseDao()

        val exerciseId = intent.getIntExtra("exerciseId", -1)
        val exerciseName = intent.getStringExtra("exerciseName")
        val exerciseDuration = intent.getIntExtra("exerciseDuration", 0)
        val exerciseDate = intent.getStringExtra("exerciseDate")

        // Set the existing data in the EditText fields
        val etExerciseName = findViewById<EditText>(R.id.etExerciseName)
        val etExerciseDuration = findViewById<EditText>(R.id.etExerciseDuration)

        etExerciseName.setText(exerciseName)
        etExerciseDuration.setText(exerciseDuration.toString())

        etExerciseName.filters = arrayOf(CustomInputFilters.ExerciseNameInputFilter())

        etExerciseDuration.filters = arrayOf(CustomInputFilters.ExerciseDurationInputFilter())

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            val updatedName = etExerciseName.text.toString()
            val updatedDurationString = etExerciseDuration.text.toString()

            if (updatedName.isEmpty()) {
                showToast("Exercise name cannot be empty.")
                return@setOnClickListener
            }

            if (!updatedName.matches("[a-zA-Z ]+".toRegex())) {
                showToast("Exercise name must contain only letters.")
                return@setOnClickListener
            }

            // Validate the duration
            val updatedDuration = updatedDurationString.toIntOrNull()
            if (updatedDuration == null || updatedDuration <= 0) {
                showToast("Please enter a valid duration (positive number).")
                return@setOnClickListener
            }

            exercise = Exercise(id = exerciseId, name = updatedName, duration = updatedDuration, date = exerciseDate ?: "")

            CoroutineScope(Dispatchers.IO).launch {
                exerciseDao.updateExercise(exercise)  // Update in the database
                withContext(Dispatchers.Main) {
                    showToast("Exercise updated successfully!")
                    finish() // Close the activity and go back to MainActivity
                }
            }
        }

        findViewById<Button>(R.id.btnCancel).setOnClickListener {
            finish()  // Close the activity without saving changes
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

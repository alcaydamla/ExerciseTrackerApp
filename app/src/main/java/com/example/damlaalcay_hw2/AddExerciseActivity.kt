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
import java.text.SimpleDateFormat
import java.util.*

class AddExerciseActivity : AppCompatActivity() {

    private lateinit var exerciseDao: ExerciseDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_exercise)

        val database = AppDatabase.getDatabase(this)
        exerciseDao = database.exerciseDao()

        val etName = findViewById<EditText>(R.id.etExerciseName)
        val etDuration = findViewById<EditText>(R.id.etExerciseDuration)
        val btnAdd = findViewById<Button>(R.id.btnAddExercise)

        btnAdd.setOnClickListener {
            val name = etName.text.toString()
            val duration = etDuration.text.toString().toIntOrNull() ?: 0
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())


            if (name.isNotEmpty() && duration > 0) {
                val exercise = Exercise(name = name, duration = duration, date = date)

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        exerciseDao.insertExercise(exercise)

                        withContext(Dispatchers.Main) {
                            showToast("Exercise added successfully!")
                            navigateToMainActivity()
                        }
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            showToast("Error adding exercise: ${e.localizedMessage}")
                        }
                    }
                }
            } else {
                showToast("Please enter valid data")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()  // Bu Activity'yi kapat
    }
}

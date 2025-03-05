package com.example.damlaalcay_hw2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var exerciseDao: ExerciseDao
    private lateinit var adapter: ExerciseAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database = AppDatabase.getDatabase(this)
        exerciseDao = database.exerciseDao()

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExerciseAdapter(
            context = this,
            onDeleteClick = { exercise -> showDeleteConfirmationDialog(exercise) },
            onUpdateClick = { exercise -> onUpdateExerciseClicked(exercise) }
        )
        recyclerView.adapter = adapter


        findViewById<Button>(R.id.btnAddExercise).setOnClickListener {
            val intent = Intent(this, AddExerciseActivity::class.java)
            startActivity(intent)
        }

        // Fetch and load exercises from API into the database
        loadDataFromApi()
    }

    private fun loadDataFromApi() {
        RetrofitInstance.api.getExercises().enqueue(object : Callback<List<Exercise>> {
            override fun onResponse(call: Call<List<Exercise>>, response: Response<List<Exercise>>) {
                if (response.isSuccessful && response.body() != null) {
                    val exercises = response.body()!!

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            exerciseDao.insertExercises(exercises)  // Insert data into database

                            // Once data is inserted, update the RecyclerView by loading data from DB
                            withContext(Dispatchers.Main) {
                                loadExercisesFromDb()  // Reload data from database to RecyclerView
                            }
                        } catch (e: Exception) {
                            showToast("Error inserting exercises into database: ${e.localizedMessage}")
                        }
                    }
                } else {
                    showToast("Error fetching data from API: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                showToast("API request failed: ${t.localizedMessage}")
            }
        })
    }

    private fun loadExercisesFromDb() {
        exerciseDao.getAllExercises().observe(this, Observer { exercises ->
            if (exercises != null && exercises.isNotEmpty()) {
                adapter.setData(exercises)  // Update RecyclerView with new data
            } else {
                showToast("No exercises found")
            }
        })
    }

    private fun onUpdateExerciseClicked(exercise: Exercise) {
        val intent = Intent(this, UpdateExerciseActivity::class.java)
        intent.putExtra("exerciseId", exercise.id)
        intent.putExtra("exerciseName", exercise.name)
        intent.putExtra("exerciseDuration", exercise.duration)
        intent.putExtra("exerciseDate", exercise.date)
        startActivity(intent)
    }

    private fun deleteExercise(exercise: Exercise) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                exerciseDao.deleteExerciseById(exercise.id)
                withContext(Dispatchers.Main) {
                    showToast("Exercise deleted!")
                    loadExercisesFromDb() // Reload the list after deletion
                }
            } catch (e: Exception) {
                showToast("Error deleting exercise: ${e.localizedMessage}")
            }
        }
    }

    private fun showDeleteConfirmationDialog(exercise: Exercise) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are you sure you want to delete this exercise?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                deleteExercise(exercise)
            }
            .setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }

        val alert = dialogBuilder.create()
        alert.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

package com.example.damlaalcay_hw2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val exerciseDao: ExerciseDao
    val allExercises: LiveData<List<Exercise>>

    init {
        val database = AppDatabase.getDatabase(application)
        exerciseDao = database.exerciseDao()
        allExercises = exerciseDao.getAllExercises()
    }

    // Add a new exercise
    fun addExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                exerciseDao.insertExercise(exercise)
            } catch (e: Exception) {
                // Error handling
            }
        }
    }

    // Delete an exercise
    fun deleteExercise(exercise: Exercise, onResult: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val numberOfDeletedRecords = exerciseDao.deleteExerciseById(exercise.id)
                withContext(Dispatchers.Main) {
                    onResult(numberOfDeletedRecords)  // Pass result to callback
                }
            } catch (e: Exception) {

            }
        }
    }

    // Update an exercise
    fun updateExercise(exercise: Exercise, onResult: (Int) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val numberOfUpdatedRecords = exerciseDao.updateExercise(exercise)
                withContext(Dispatchers.Main) {
                    onResult(numberOfUpdatedRecords)  // Pass result to callback
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun onResult(numberOfUpdatedRecords: Unit) {

    }


    fun searchExercise(searchKey: String): LiveData<List<Exercise>> {
        return exerciseDao.searchExercisesByName(searchKey)
    }
}

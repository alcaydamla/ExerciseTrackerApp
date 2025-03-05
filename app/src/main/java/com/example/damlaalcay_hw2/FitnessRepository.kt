package com.example.damlaalcay_hw2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FitnessRepository(private val exerciseDao: ExerciseDao) {

    suspend fun getAllExercises() {
        return withContext(Dispatchers.IO) {
            exerciseDao.getAllExercises()
        }
    }

    suspend fun insertExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.insertExercise(exercise)
        }
    }

    suspend fun deleteExerciseById(exerciseId: Int) {
        withContext(Dispatchers.IO) {
            exerciseDao.deleteExerciseById(exerciseId)
        }
    }

    suspend fun getExerciseById(exerciseId: Int): Exercise? {
        return withContext(Dispatchers.IO) {
            exerciseDao.getExerciseById(exerciseId)
        }
    }
}

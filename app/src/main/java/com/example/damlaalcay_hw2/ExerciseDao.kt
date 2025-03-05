package com.example.damlaalcay_hw2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExerciseDao {

    // Tek bir egzersizi ekleme işlemi
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercise(exercise: Exercise)

    // Birden fazla egzersizi ekleme işlemi
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<Exercise>)

    // Tüm egzersizleri liste olarak döndür (LiveData ile)
    @Query("SELECT * FROM exercise_table ORDER BY date DESC")
    fun getAllExercises(): LiveData<List<Exercise>>

    @Query("SELECT * FROM exercise_table WHERE id = :exerciseId")
    suspend fun getExerciseById(exerciseId: Int): Exercise?

    @Query("DELETE FROM exercise_table WHERE id = :exerciseId")
    suspend fun deleteExerciseById(exerciseId: Int)

    @Update
    suspend fun updateExercise(exercise: Exercise)

    // Egzersiz adı ile arama (LiveData ile)
    @Query("SELECT * FROM exercise_table WHERE name LIKE :name")
    fun searchExercisesByName(name: String): LiveData<List<Exercise>>
}

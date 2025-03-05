package com.example.damlaalcay_hw2

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "exercise_table")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val duration: Int,
    val date: String
)

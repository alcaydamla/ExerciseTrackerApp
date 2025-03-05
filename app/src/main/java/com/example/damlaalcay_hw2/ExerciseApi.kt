package com.example.damlaalcay_hw2

import retrofit2.Call
import retrofit2.http.GET

interface ExerciseApi {
    @GET("b/KHXX")
    fun getExercises(): Call<List<Exercise>>
}

package com.example.damlaalcay_hw2

import android.text.InputFilter
import android.text.Spanned

class CustomInputFilters {

    // Filter for Exercise Name: Only allows letters and spaces.
    class ExerciseNameInputFilter : InputFilter {
        override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
            // Allow only alphabetic characters and spaces
            if (source != null && source.matches("[a-zA-Z ]*".toRegex())) {
                return source
            }
            return "" // Reject non-alphabetic characters
        }
    }

    // Filter for Exercise Duration: Only allows digits (0-9).
    class ExerciseDurationInputFilter : InputFilter {
        override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
            // Allow only digits (0-9)
            if (source != null && source.matches("[0-9]*".toRegex())) {
                return source
            }
            return "" // Reject non-numeric characters
        }
    }

    // You can create other filters as needed, for example:
    // Filter for accepting only valid date format, or alphanumeric characters, etc.

    // Example for Alphanumeric Filter:
    class AlphanumericInputFilter : InputFilter {
        override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
            // Allow only alphanumeric characters (letters and digits)
            if (source != null && source.matches("[a-zA-Z0-9]*".toRegex())) {
                return source
            }
            return "" // Reject non-alphanumeric characters
        }
    }

}

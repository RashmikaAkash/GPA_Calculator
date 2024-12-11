package com.example.gpacalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayout: LinearLayout = findViewById(R.id.linearLayoutModules)
        val btnAddModule: Button = findViewById(R.id.btnAddModule)
        val btnCalculateGPA: Button = findViewById(R.id.btnCalculateGPA)
        val btnReset: Button = findViewById(R.id.btnReset)
        val textViewResult: TextView = findViewById(R.id.textViewResult)

        // Initialize the first module input field
        addModuleInput(linearLayout)

        // Add a new module input field when clicked
        btnAddModule.setOnClickListener {
            addModuleInput(linearLayout)
        }

        // Calculate GPA when the button is clicked
        btnCalculateGPA.setOnClickListener {
            val modules = mutableListOf<Pair<String, String>>() // Store module name and grade

            for (i in 0 until linearLayout.childCount step 2) {
                val moduleNameEditText = linearLayout.getChildAt(i) as EditText
                val gradeEditText = linearLayout.getChildAt(i + 1) as EditText

                val moduleName = moduleNameEditText.text.toString()
                val grade = gradeEditText.text.toString()

                if (moduleName.isNotEmpty() && grade.isNotEmpty()) {
                    modules.add(Pair(moduleName, grade))
                }
            }

            if (modules.isNotEmpty()) {
                val gpa = calculateGPA(modules)
                textViewResult.text = "Total GPA: $gpa"
            } else {
                Toast.makeText(this, "Please enter all modules and grades.", Toast.LENGTH_SHORT).show()
            }
        }

        // Reset fields when the reset button is clicked
        btnReset.setOnClickListener {
            resetFields(linearLayout, textViewResult)
        }
    }

    private fun addModuleInput(layout: LinearLayout) {
        // Create the input fields for the module name and grade
        val moduleNameEditText = EditText(this).apply {
            hint = "Module Name"
            inputType = android.text.InputType.TYPE_CLASS_TEXT
            setPadding(16, 16, 16, 16)
            setBackgroundResource(android.R.drawable.edit_text)
        }

        val gradeEditText = EditText(this).apply {
            hint = "Grade (A+, A, B+, etc.)"
            inputType = android.text.InputType.TYPE_CLASS_TEXT
            setPadding(16, 16, 16, 16)
            setBackgroundResource(android.R.drawable.edit_text)
        }

        // Add the module name and grade input fields to the layout
        layout.addView(moduleNameEditText)
        layout.addView(gradeEditText)
    }

    private fun calculateGPA(modules: List<Pair<String, String>>): Double {
        var totalPoints = 0.0

        for (module in modules) {
            val gradePoint = getGradePoint(module.second)
            totalPoints += gradePoint
        }

        return if (modules.isNotEmpty()) {
            totalPoints / modules.size
        } else {
            0.0
        }
    }

    // Function to determine grade point based on grade
    private fun getGradePoint(grade: String): Double {
        return when (grade.uppercase()) {
            "A+" -> 4.0
            "A" -> 4.0
            "A-" -> 3.7
            "B+" -> 3.3
            "B" -> 3.0
            "B-" -> 2.7
            "C+" -> 2.3
            "C" -> 2.0
            "C-" -> 1.7
            "D+" -> 1.3
            "D" -> 1.0
            "E" -> 0.0
            else -> 0.0 // Default to 0.0 for invalid input
        }
    }

    private fun resetFields(layout: LinearLayout, resultTextView: TextView) {
        // Clear all module name and grade input fields
        layout.removeAllViews()
        addModuleInput(layout)

        // Clear the result text
        resultTextView.text = "Total GPA: "
    }
}

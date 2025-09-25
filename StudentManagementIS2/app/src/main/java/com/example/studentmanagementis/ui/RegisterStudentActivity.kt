// ui/student/RegisterStudentActivity.kt
package com.example.studentmanagementis.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementis.R
import com.example.studentmanagementis.data.AppDatabase
import com.example.studentmanagementis.data.Student
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.jvm.java

class RegisterStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_student)

        val nameEditText = findViewById<TextInputEditText>(R.id.edit_name)
        val regNumberEditText = findViewById<TextInputEditText>(R.id.edit_reg_number)
        val courseSpinner = findViewById<Spinner>(R.id.spinner_course)
        val registerButton = findViewById<Button>(R.id.btn_register)
        val backButton = findViewById<TextView>(R.id.btn_back)

        // Setup Spinner
        val courses = arrayOf("Select course", "Computer Science", "Business Administration", "Engineering", "Archaelogy","Hunting and Gathering")
        courseSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, courses)

        val studentDao = AppDatabase.getDatabase(this).studentDao()

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val regNumber = regNumberEditText.text.toString()
            val course = courseSpinner.selectedItem.toString()

            if (name.isNotEmpty() && regNumber.isNotEmpty() && course != "Select course") {
                val student = Student(name = name, regNumber = regNumber, course = course)
                CoroutineScope(Dispatchers.IO).launch {
                    studentDao.insert(student)
                    runOnUiThread {
                        Toast.makeText(this@RegisterStudentActivity, "Student Registered Successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterStudentActivity, ViewStudentActivity::class.java))
                    }
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
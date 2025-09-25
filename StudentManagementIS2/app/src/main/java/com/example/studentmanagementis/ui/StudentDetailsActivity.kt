// ui/student/StudentDetailsActivity.kt
package com.example.studentmanagementis.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentmanagementis.MainActivity
import com.example.studentmanagementis.R
import com.example.studentmanagementis.data.AppDatabase
import com.example.studentmanagementis.data.Student
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        val nameTextView = findViewById<MaterialTextView>(R.id.text_name)
        val courseTextView = findViewById<MaterialTextView>(R.id.text_course)
        val idTextView = findViewById<MaterialTextView>(R.id.text_student_id)
        val dobTextView = findViewById<MaterialTextView>(R.id.text_dob)
        val genderTextView = findViewById<MaterialTextView>(R.id.text_gender)
        val addressTextView = findViewById<MaterialTextView>(R.id.text_address)
        val emailTextView = findViewById<MaterialTextView>(R.id.text_email)
        val phoneTextView = findViewById<MaterialTextView>(R.id.text_phone)
        val backButton = findViewById<MaterialButton>(R.id.btn_back)
        val deleteButton = findViewById<MaterialButton>(R.id.btn_delete)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val studentId = intent.getIntExtra("student_id", -1)
        val name = intent.getStringExtra("student_name") ?: "Unknown"
        val course = intent.getStringExtra("student_course") ?: "Unknown"
        val regNumber = intent.getStringExtra("student_reg_number") ?: "Unknown"
        val dob = intent.getStringExtra("student_dob") ?: "Not provided"
        val gender = intent.getStringExtra("student_gender") ?: "Not provided"
        val address = intent.getStringExtra("student_address") ?: "Not provided"
        val email = intent.getStringExtra("student_email") ?: "Not provided"
        val phone = intent.getStringExtra("student_phone") ?: "Not provided"

        nameTextView.text = name
        courseTextView.text = course
        idTextView.text = "Student ID: $regNumber"
        dobTextView.text = "Date of Birth: $dob"
        genderTextView.text = "Gender: $gender"
        addressTextView.text = "Address: $address"
        emailTextView.text = "Email: $email"
        phoneTextView.text = "Phone: $phone"

        val studentDao = AppDatabase.getDatabase(this).studentDao()

        deleteButton.setOnClickListener {
            if (studentId != -1) {
                CoroutineScope(Dispatchers.IO).launch {
                    val student = Student(
                        id = studentId,
                        name = name,
                        regNumber = regNumber,
                        course = course,
                        dob = dob,
                        gender = gender,
                        address = address,
                        email = email,
                        phone = phone
                    )
                    studentDao.delete(student)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@StudentDetailsActivity, "Student deleted", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@StudentDetailsActivity, ViewStudentActivity::class.java))
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "Error: Invalid student ID", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish()
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_students -> {
                    startActivity(Intent(this, ViewStudentActivity::class.java))
                    true
                }
                R.id.nav_results -> {
                    startActivity(Intent(this, ResultsActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
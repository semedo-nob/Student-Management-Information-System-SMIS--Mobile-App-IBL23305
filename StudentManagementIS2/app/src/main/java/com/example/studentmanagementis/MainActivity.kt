package com.example.studentmanagementis

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import android.content.Intent

import kotlin.jvm.java
import com.example.studentmanagementis.R
import com.example.studentmanagementis.ui.ResultsActivity
import com.example.studentmanagementis.ui.RegisterStudentActivity
import com.example.studentmanagementis.ui.ViewStudentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerButton = findViewById<MaterialButton>(R.id.btn_register_student)
        val viewStudentsButton = findViewById<MaterialButton>(R.id.btn_view_students)
        val generateReportButton = findViewById<MaterialButton>(R.id.btn_generate_report)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        registerButton.setOnClickListener {
            startActivity(Intent(this, RegisterStudentActivity::class.java))
        }

        viewStudentsButton.setOnClickListener {
            startActivity(Intent(this, ViewStudentActivity::class.java))
        }

        generateReportButton.setOnClickListener {
            startActivity(Intent(this, ResultsActivity::class.java))
        }

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_dashboard -> true
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
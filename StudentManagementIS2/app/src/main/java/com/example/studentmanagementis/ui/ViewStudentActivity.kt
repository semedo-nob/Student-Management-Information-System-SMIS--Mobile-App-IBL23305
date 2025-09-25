// ui/ViewStudentActivity.kt
package com.example.studentmanagementis.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementis.MainActivity
import com.example.studentmanagementis.R
import com.example.studentmanagementis.data.AppDatabase
import com.example.studentmanagementis.data.Student
import com.example.studentmanagementis.ui.RegisterStudentActivity

import com.example.studentmanagementis.ui.student.StudentAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewStudentActivity : AppCompatActivity() {
    private lateinit var adapter: StudentAdapter // Class-level property

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_student)

        val recyclerView = findViewById<RecyclerView>(R.id.student_recycler_view)
        val searchEditText = findViewById<TextInputEditText>(R.id.edit_search)
        val addButton = findViewById<MaterialButton>(R.id.btn_add_student)
        val backButton = findViewById<MaterialButton>(R.id.btn_back)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val studentDao = AppDatabase.getDatabase(this).studentDao()
        adapter = StudentAdapter(
            onItemClick = { student ->
                val intent = Intent(this, StudentDetailsActivity::class.java).apply {
                    putExtra("student_id", student.id)
                    putExtra("student_name", student.name)
                    putExtra("student_reg_number", student.regNumber)
                    putExtra("student_course", student.course)
                    putExtra("student_dob", student.dob)
                    putExtra("student_gender", student.gender)
                    putExtra("student_address", student.address)
                    putExtra("student_email", student.email)
                    putExtra("student_phone", student.phone)
                }
                startActivity(intent)
            },
            onDeleteClick = { student ->
                AlertDialog.Builder(this)
                    .setTitle("Delete Student")
                    .setMessage("Are you sure you want to delete ${student.name}?")
                    .setPositiveButton("Delete") { _, _ ->
                        CoroutineScope(Dispatchers.IO).launch {
                            studentDao.delete(student)
                            val updatedStudents = studentDao.getAllStudents()
                            withContext(Dispatchers.Main) {
                                adapter.submitList(updatedStudents)
                                Snackbar.make(
                                    recyclerView,
                                    "${student.name} deleted",
                                    Snackbar.LENGTH_LONG
                                )
                                    .setAction("Undo") {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            studentDao.insert(student)
                                            val newStudents = studentDao.getAllStudents()
                                            withContext(Dispatchers.Main) {
                                                adapter.submitList(newStudents)
                                            }
                                        }
                                    }
                                    .show()
                            }
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Swipe-to-delete with confirmation
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val student = adapter.getStudentAt(position)
                AlertDialog.Builder(this@ViewStudentActivity)
                    .setTitle("Delete Student")
                    .setMessage("Are you sure you want to delete ${student.name}?")
                    .setPositiveButton("Delete") { _, _ ->
                        CoroutineScope(Dispatchers.IO).launch {
                            studentDao.delete(student)
                            val updatedStudents = studentDao.getAllStudents()
                            withContext(Dispatchers.Main) {
                                adapter.submitList(updatedStudents)
                                Snackbar.make(recyclerView, "${student.name} deleted", Snackbar.LENGTH_LONG)
                                    .setAction("Undo") {
                                        CoroutineScope(Dispatchers.IO).launch {
                                            studentDao.insert(student)
                                            val newStudents = studentDao.getAllStudents()
                                            withContext(Dispatchers.Main) {
                                                adapter.submitList(newStudents)
                                            }
                                        }
                                    }
                                    .show()
                            }
                        }
                    }
                    .setNegativeButton("Cancel") { _, _ ->
                        adapter.notifyItemChanged(position) // Reset swipe
                    }
                    .show()
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)

        // Load students
        CoroutineScope(Dispatchers.IO).launch {
            val students = studentDao.getAllStudents()
            withContext(Dispatchers.Main) {
                adapter.submitList(students)
            }
        }

        addButton.setOnClickListener {
            startActivity(Intent(this, RegisterStudentActivity::class.java))
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
                R.id.nav_students -> true
                R.id.nav_results -> {
                    startActivity(Intent(this, ResultsActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Search functionality
        searchEditText.addTextChangedListener(object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val query = s.toString().lowercase()
                CoroutineScope(Dispatchers.IO).launch {
                    val filteredStudents = studentDao.getAllStudents().filter {
                        it.name.lowercase().contains(query) || it.regNumber.lowercase().contains(query)
                    }
                    withContext(Dispatchers.Main) {
                        adapter.submitList(filteredStudents)
                    }
                }
            }
        })
    }
}
// ui/ResultsActivity.kt
package com.example.studentmanagementis.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementis.MainActivity
import com.example.studentmanagementis.R
import com.example.studentmanagementis.network.ApiService
import com.example.studentmanagementis.network.RetrofitClient
import com.example.studentmanagementis.ui.StudentDetailsActivity
import com.example.studentmanagementis.viewmodel.ResultsViewModel
import com.example.studentmanagementis.viewmodel.ResultsViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val recyclerView = findViewById<RecyclerView>(R.id.results_recycler_view)
        val backButton = findViewById<MaterialButton>(R.id.btn_back)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val adapter = ResultAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val viewModel = ViewModelProvider(this, ResultsViewModelFactory(RetrofitClient.apiService))
            .get(ResultsViewModel::class.java)

        viewModel.results.observe(this) { results ->
            adapter.submitList(results)
        }

        viewModel.fetchResults()

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
                    startActivity(Intent(this, StudentDetailsActivity::class.java))
                    true
                }
                R.id.nav_results -> true
                else -> false
            }
        }
    }
}
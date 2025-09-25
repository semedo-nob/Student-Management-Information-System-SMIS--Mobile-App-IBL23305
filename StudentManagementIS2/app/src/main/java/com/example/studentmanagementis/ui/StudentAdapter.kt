// ui/student/StudentAdapter.kt
package com.example.studentmanagementis.ui.student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementis.R
import com.example.studentmanagementis.data.Student
import com.google.android.material.button.MaterialButton

class StudentAdapter(
    private val onItemClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private var students: List<Student> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
        holder.itemView.setOnClickListener { onItemClick(student) }
        holder.deleteButton.setOnClickListener { onDeleteClick(student) }
    }

    override fun getItemCount(): Int = students.size

    fun submitList(newStudents: List<Student>) {
        students = newStudents
        notifyDataSetChanged()
    }

    fun getStudentAt(position: Int): Student = students[position]

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.text_name)
        private val regNumberTextView = itemView.findViewById<TextView>(R.id.text_reg_number)
        private val avatarImageView = itemView.findViewById<ImageView>(R.id.image_avatar)
        val deleteButton = itemView.findViewById<MaterialButton>(R.id.btn_delete)

        fun bind(student: Student) {
            nameTextView.text = student.name
            regNumberTextView.text = "Reg. No: ${student.regNumber}"
            avatarImageView.setImageResource(R.drawable.ic_placeholder_avatar)
        }
    }
}
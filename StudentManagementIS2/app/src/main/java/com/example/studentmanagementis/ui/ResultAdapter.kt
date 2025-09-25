// ui/ResultAdapter.kt
package com.example.studentmanagementis.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentmanagementis.R
import com.example.studentmanagementis.network.Result

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {
    private var results: List<Result> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = results[position]
        holder.bind(result)
    }

    override fun getItemCount(): Int = results.size

    fun submitList(newResults: List<Result>) {
        results = newResults
        notifyDataSetChanged()
    }

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val scoreTextView = itemView.findViewById<TextView>(R.id.text_score)
        private val subjectTextView = itemView.findViewById<TextView>(R.id.text_subject)
        private val gradeTextView = itemView.findViewById<TextView>(R.id.text_grade)

        fun bind(result: Result) {
            scoreTextView.text = result.score.toString()
            subjectTextView.text = result.subject
            gradeTextView.text = result.grade
            gradeTextView.setBackgroundResource(
                when (result.grade) {
                    "A" -> R.drawable.grade_a_background
                    "B" -> R.drawable.grade_b_background
                    "C" -> R.drawable.grade_c_background
                    else -> R.drawable.grade_default_background
                }
            )
            gradeTextView.setTextColor(
                itemView.context.resources.getColor(
                    when (result.grade) {
                        "A" -> R.color.green_800
                        "B" -> R.color.blue_800
                        "C" -> R.color.yellow_800
                        else -> R.color.gray_500
                    },
                    itemView.context.theme
                )
            )
        }
    }
}
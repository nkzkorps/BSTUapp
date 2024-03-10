package com.example.bstuapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bstuapp.R
import com.example.bstuapp.api.Teacher
import com.example.bstuapp.databinding.TeacherCardBinding
import com.example.bstuapp.ui.TimetableActivity


class TeacherAdapter : ListAdapter<Teacher, TeacherAdapter.Holder>(Comparator()) {
    val teacherList = ArrayList<Teacher>()

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = TeacherCardBinding.bind(view)

        fun bind(teacher: Teacher, holder: Holder) = with(binding) {
            teacherName.text = teacher.name
            teacherMail.text = teacher.email

            teacherId.setOnClickListener {
                val viewIntent = Intent(holder.itemView.context, TimetableActivity::class.java)
                val searchId = teacher.id

                viewIntent.putExtra("searchId", searchId)
                viewIntent.putExtra("searchType", "teacher")
                holder.itemView.context.startActivity(viewIntent)
            }
        }
    }


    class Comparator : DiffUtil.ItemCallback<Teacher>() {
        override fun areItemsTheSame(oldItem: Teacher, newItem: Teacher): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Teacher, newItem: Teacher): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.teacher_card, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), holder)
    }
}

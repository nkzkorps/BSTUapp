package com.example.bstuapp.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bstuapp.R
import com.example.bstuapp.api.Event
import com.example.bstuapp.databinding.PairCardBinding


class PairAdapter(val entityType: String) : ListAdapter<Event, PairAdapter.Holder>(Comparator()) {
    val pairList = ArrayList<Event>()

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = PairCardBinding.bind(view)

        fun bind(event: Event, entityType: String) = with(binding) {
            if (event.type == "pair") {

                if (event.data.order != null) {
                    pairNumTv.isGone = false
                    pairNumTv.text = event.data.order.toString()
                } else {
                    pairNumTv.isGone = true
                }
                pairName.text = event.data.subject.shortName
                pairType.text = event.data.types[0].short_name

                when (event.data.types[0].short_name) {
                    "лек" -> {
                        pairType.setBackgroundResource(R.drawable.bg_pair_lecture)
                    }

                    "лаб" -> {
                        pairType.setBackgroundResource(R.drawable.bg_pair_lab)
                    }

                    "практ" -> {
                        pairType.setBackgroundResource(R.drawable.bg_pair_practice)
                    }

                    "конс" -> {
                        pairType.setBackgroundResource(R.drawable.bg_pair_consultation)
                    }

                    "экз" -> {
                        pairType.setBackgroundResource(R.drawable.bg_pair_exam)
                    }
                }

                pairStart.text = event.data.start
                pairEnd.text = event.data.end

                for (i in event.data.audiences) {
                    audiencesTv.append((i.name + " "))
                }

                when (entityType) {
                    "group" -> for (i in event.data.teachers) {
                        teachersTv.append(i.short_name + " ")
                    }

                    "teacher" -> for (i in event.data.groups) {
                        teachersTv.append(i.name + " ")
                    }
                }
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.data.id == newItem.data.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pair_card, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), entityType)
    }
}
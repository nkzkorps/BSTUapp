package com.example.bstuapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bstuapp.R
import com.example.bstuapp.api.Day
import com.example.bstuapp.databinding.DayCardBinding

class DayAdapter(EntityType: String) : ListAdapter<Day, DayAdapter.Holder>(Comparator()) {
    val dayList = ArrayList<Day>()
    private val entityType = EntityType


    class Holder(private val binding: DayCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(day: Day, holder: Holder, entityType: String) = with(binding) {
            dayRcView.setHasFixedSize(true)
            dayRcView.layoutManager = LinearLayoutManager(holder.itemView.context)
            val adapter = PairAdapter(entityType)
            dayRcView.adapter = adapter
            if (day.events.isNullOrEmpty()) {
                dayheaderBar.setBackgroundResource(R.color.day_header)
                emptyView.visibility = View.VISIBLE
                dayRcView.visibility = View.GONE
            } else {
                val pairList = day.events.filter { it!!.type != "pair_break" }
                adapter.submitList(pairList)
                dayheaderBar.setBackgroundResource(R.color.day_header_active)
                emptyView.visibility = View.GONE
                dayRcView.visibility = View.VISIBLE

            }
            weekday.text = day.day_short_name
            date.text = day.date
        }

        companion object {
            fun create(parent: ViewGroup): Holder {
                return Holder(
                    DayCardBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    class Comparator : DiffUtil.ItemCallback<Day>() {
        override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem.day_week_num == newItem.day_week_num
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder.create(parent)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position), holder, entityType)

    }
}
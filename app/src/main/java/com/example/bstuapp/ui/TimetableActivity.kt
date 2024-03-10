package com.example.bstuapp.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bstuapp.R
import com.example.bstuapp.adapter.DayAdapter
import com.example.bstuapp.api.TimetableApi
import com.example.bstuapp.databinding.ActivityTimetableBinding
import com.example.bstuapp.utils.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


class TimetableActivity : BaseActivity() {
    private val binding: ActivityTimetableBinding by viewBinding(ActivityTimetableBinding::bind)
    private lateinit var adapter: DayAdapter

    private val btnBackClickListener = View.OnClickListener {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timetable)



        setupToolbar(
            binding.root, " |   Расписание", R.drawable.slidermenu, true, btnBackClickListener
        )

        val type = intent.getStringExtra("searchType")!!
        val searchId = intent.getIntExtra("searchId", 0)
        var week: Int = 0

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder().baseUrl("https://lk.bstu.ru").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val timetableApi = retrofit.create(TimetableApi::class.java)

        adapter = DayAdapter(type)
        binding.timetableRcView.setHasFixedSize(true)
        binding.timetableRcView.layoutManager = LinearLayoutManager(this)
        binding.timetableRcView.adapter = adapter

        CoroutineScope(Dispatchers.IO).launch {
            val timetable = timetableApi.getTimetable(type ,searchId, week)

            runOnUiThread {
                binding.apply {
                    recipientTv.text = timetable.result.name
                    adapter.submitList(timetable.result.weeks[0].days)
                    when (timetable.result.weeks[0].is_denominator) {
                        false -> {
                            weekTv.text = "Числитель"
                        }

                        true -> {
                            weekTv.text = "Знаменатель"
                        }
                    }

                    val dateStart = LocalDate.parse(
                        timetable.result.weeks[0].start,
                        DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssxx")
                    )
                    val dateEnd = LocalDate.parse(
                        timetable.result.weeks[0].end,
                        DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssxx")
                    )
                    val dateFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))

                    weekDateTv.append(dateStart.format(dateFormatter) + " — " + dateEnd.format(dateFormatter))


                    adapter.notifyDataSetChanged()
                }
            }
        }

        binding.weekTv.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                when (week) {
                    1 -> week = 0
                    0 -> week = 1
                }
                val timetable = timetableApi.getTimetable(type ,searchId, week)

                runOnUiThread {
                    binding.apply {
                        adapter.submitList(timetable.result.weeks[0].days)
                        when (timetable.result.weeks[0].is_denominator) {
                            false -> {
                                weekTv.text = "Числитель"
                            }

                            true -> {
                                weekTv.text = "Знаменатель"
                            }
                        }

                        val dateStart = LocalDate.parse(
                            timetable.result.weeks[0].start,
                            DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssxx")
                        )
                        val dateEnd = LocalDate.parse(
                            timetable.result.weeks[0].end,
                            DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssxx")
                        )
                        val dateFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))

                        weekDateTv.text = (dateStart.format(dateFormatter) + " — " + dateEnd.format(dateFormatter))


                        adapter.notifyDataSetChanged()
                    }
                }

            }
        }

    }

}
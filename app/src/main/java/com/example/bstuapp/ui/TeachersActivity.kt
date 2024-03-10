package com.example.bstuapp.ui

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bstuapp.R
import com.example.bstuapp.adapter.TeacherAdapter

import com.example.bstuapp.api.TeacherApi
import com.example.bstuapp.databinding.ActivityTeachersBinding
import com.example.bstuapp.utils.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TeachersActivity : BaseActivity() {
    private val binding: ActivityTeachersBinding by viewBinding(ActivityTeachersBinding::bind)
    private lateinit var adapter: TeacherAdapter



    private val btnBackClickListener = View.OnClickListener {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teachers)

        adapter = TeacherAdapter()
        binding.teachersRcView.layoutManager = LinearLayoutManager(this)
        binding.teachersRcView.adapter = adapter


        setupToolbar(
            binding.root, " |   Преподаватели", R.drawable.slidermenu, true, btnBackClickListener
        )

        val searchId = intent.getIntExtra("searchId", 0)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder().baseUrl("https://lk.bstu.ru").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val teacherApi = retrofit.create(TeacherApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val teacherList = teacherApi.getAllTeachers(
                searchId
            )
            runOnUiThread {
                binding.apply {
                    adapter.submitList(teacherList.result)
                    adapter.notifyDataSetChanged()
                }
            }
        }

    }

}
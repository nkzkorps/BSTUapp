package com.example.bstuapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bstuapp.R
import com.example.bstuapp.api.GroupsApi
import com.example.bstuapp.databinding.ActivityMainBinding
import com.example.bstuapp.utils.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : BaseActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    private val btnBackClickListener = View.OnClickListener {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar(
            binding.root, " |   Личный кабинет", R.drawable.slidermenu, true, btnBackClickListener
        )

        val apiToken = intent.getStringExtra("apiToken")
        val groupName = intent.getStringExtra("groupName")
        val currentAttestation = intent.getStringExtra("currentAttestation")
        val currentProgress = intent.getStringExtra("currentProgress")

        binding.groupName.text = groupName
        binding.currentAttestation.text = currentAttestation
        binding.currentProgress.text = currentProgress


        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder().baseUrl("https://lk.bstu.ru").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val groupsApi = retrofit.create(GroupsApi::class.java)


        binding.toTeachers.setOnClickListener {
            val viewIntent = Intent(this, TeachersActivity::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val groupsList = groupsApi.getAllGroups(groupName!!)
                val searchId = groupsList.result.groups[0].id
                viewIntent.putExtra("searchId", searchId)
                runOnUiThread {
                    startActivity(viewIntent)
                }
            }
        }

        binding.toTimetable.setOnClickListener {
            val viewIntent = Intent(this, TimetableActivity::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val groupsList = groupsApi.getAllGroups(groupName!!)
                val searchId = groupsList.result.groups[0].id
                viewIntent.putExtra("searchId", searchId)
                viewIntent.putExtra("searchType", "group")
                runOnUiThread {
                    startActivity(viewIntent)
                }
            }
        }
    }
}
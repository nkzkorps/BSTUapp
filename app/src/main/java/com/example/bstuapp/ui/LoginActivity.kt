package com.example.bstuapp.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bstuapp.R
import com.example.bstuapp.api.AuthApi
import com.example.bstuapp.api.AuthRequest
import com.example.bstuapp.api.MainInfoApi
import com.example.bstuapp.api.MainInfoModel
import com.example.bstuapp.databinding.ActivityLoginBinding
import com.example.bstuapp.utils.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : BaseActivity() {
    private val binding: ActivityLoginBinding by viewBinding(ActivityLoginBinding::bind)

    private val btnBackClickListener = View.OnClickListener {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupToolbar(
            binding.root, " |   Личный кабинет", R.drawable.bstu_logo, true, null
        )

        binding.toAgreement.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.bstu.ru/pd"))
            startActivity(browserIntent)
        }

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            binding.loginButton.isEnabled = isChecked
        }

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder().baseUrl("https://lk.bstu.ru").client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

        val authApi = retrofit.create(AuthApi::class.java)
        val mainInfoApi = retrofit.create(MainInfoApi::class.java)


        binding.loginText.addTextChangedListener {
            binding.loginContainer.error = null
        }

        binding.passwordText.addTextChangedListener {
            binding.passwordContainer.error = null
        }

        binding.loginButton.setOnClickListener {
            val viewIntent = Intent(this, MainActivity::class.java)

            val login = binding.loginText.text.toString()
            val password = binding.passwordText.text.toString()

            CoroutineScope(Dispatchers.IO).launch {
                val apiResponse = authApi.auth(
                    AuthRequest(
                        login, password
                    )
                )
                lateinit var mainInfoResponse: MainInfoModel
                if(apiResponse.success) {
                    mainInfoResponse = mainInfoApi.getMainInfo(
                        apiResponse.result.user_info.api_token
                    )
                }

                runOnUiThread {
                    if (apiResponse.success) {

                        val apiToken = apiResponse.result.user_info.api_token
                        val groupName = apiResponse.result.user_info.accounts[0].data.group.name
                        val currentProgress = mainInfoResponse.result.progress
                        val currentAttestation = mainInfoResponse.result.attestation

                        viewIntent.putExtra("apiToken", apiToken)
                            .putExtra("groupName", groupName)
                            .putExtra("currentProgress", currentProgress)
                            .putExtra("currentAttestation", currentAttestation)

                        startActivity(viewIntent)
                    } else {
                        binding.loginContainer.error = " "
                        binding.passwordContainer.error = "Неправильный логин или пароль"
                    }
                }
            }
        }
    }
}
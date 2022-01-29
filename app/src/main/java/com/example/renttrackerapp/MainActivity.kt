package com.example.renttrackerapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.renttrackerapp.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    companion object {
        private const val INTENT_TYPE = "text/plain"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<ActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkMimeType()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun checkMimeType() {
        when (intent.action) {
            Intent.ACTION_SEND -> {
                if (INTENT_TYPE == intent.type) {
                    handleSendLink(intent)
                }
            }
            else -> {
                viewModel.fetchHomes()
            }
        }
    }

    private fun handleSendLink(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            viewModel.homeResultsLiveData.value = UiState.IsLoading(true)
            viewModel.addHome(viewModel.extractLink(it))
        }
    }

    private fun setupView() {
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.homeResultsLiveData.collect { uiState ->
                    when (uiState) {
                        UiState.OnEmpty -> {
                            Log.e("OnEmpty", "Data is Empty")
                        }
                        is UiState.OnError -> {
                            Log.e("OnError", "${uiState.message}")
                        }
                        is UiState.OnSuccess -> {
                            Log.e("OnSuccess", " ${uiState.result}")
                        }
                        is UiState.IsLoading -> {
                            if (uiState.loading) {
                                binding.progressLoader.visibility = View.VISIBLE
                            } else {
                                binding.progressLoader.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }
}
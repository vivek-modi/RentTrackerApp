package com.example.renttrackerapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.renttrackerapp.databinding.ActivityMainBinding
import com.example.renttrackerapp.model.Home
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity(), ItemClickListener {

    companion object {
        private const val INTENT_TYPE = "text/plain"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<ActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.rentTrackerAdapter = RentTrackerAdapter(this)

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
        }
    }

    private fun handleSendLink(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            viewModel.addHome(viewModel.extractLink(it))
        }
    }

    private fun setupView() {
        binding.rentTrackerRecyclerView.adapter = viewModel.rentTrackerAdapter
        lifecycleScope.launchWhenCreated {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.getHomeRequestFlow().collectLatest {
                    viewModel.rentTrackerAdapter?.submitData(it)
                }
            }
        }
    }

    override fun onClick(item: Home) {
        startActivity(Intent(this, HomeDetailActivity::class.java).apply {
            putExtra("itemDetail", item)
        })
    }

    override fun onItemDelete(id: Int) {
        viewModel.deleteHome(id)
    }
}
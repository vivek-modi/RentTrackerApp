package com.example.renttrackerapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.addMore) {
            openDialog()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun openDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Do you want to add House?")
        dialogBuilder.setCancelable(false)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton("Add") { dialog, _ ->
            val editText = dialogView.findViewById<EditText>(R.id.addMore)
            if (!editText.text.isNullOrEmpty()) {
                viewModel.addHome(editText.text.toString())
                dialog.dismiss()
            }
        }
        dialogBuilder.setNegativeButton("close") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(this, android.R.color.black))
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(this, android.R.color.black))
        }
        dialog.show()
    }
}
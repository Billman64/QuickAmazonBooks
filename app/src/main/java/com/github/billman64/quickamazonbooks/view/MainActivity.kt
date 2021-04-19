package com.github.billman64.quickamazonbooks.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.github.billman64.quickamazonbooks.R
import com.github.billman64.quickamazonbooks.databinding.ActivityMainBinding
import com.github.billman64.quickamazonbooks.util.State
import com.github.billman64.quickamazonbooks.viewmodel.AmazonViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val viewModel: AmazonViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private val adapter: AmazonBookAdapter = AmazonBookAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.booksRecyclerview.adapter = adapter

        viewModel.stateLiveData.observe(this, Observer {

            when (it) {
                State.LOADING -> binding.loadingPrograssbar.visibility = View.VISIBLE
                State.COMPLETED -> binding.loadingPrograssbar.visibility = View.GONE
                else -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.failed_error),
                        Snackbar.LENGTH_LONG
                    ).show()
                    binding.loadingPrograssbar.visibility = View.GONE
                }
            }
        })

        viewModel.getBooks().observe(this, Observer {
            adapter.updateListItems(it)
        })
    }
}
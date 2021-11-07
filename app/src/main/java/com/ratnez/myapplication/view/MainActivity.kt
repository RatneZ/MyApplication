package com.ratnez.myapplication.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ratnez.myapplication.common.ACTION_ACCEPTED
import com.ratnez.myapplication.common.ACTION_REJECTED
import com.ratnez.myapplication.databinding.ActivityMainBinding
import com.ratnez.myapplication.model.Person
import com.ratnez.myapplication.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var personAdapter: PersonAdapter
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()

        lifecycleScope.launchWhenStarted {
            viewModel.getPersonData().collectLatest {
                personAdapter.setDataAndNotify(it)
            }
        }
    }

    private fun setAdapter() {
        personAdapter = PersonAdapter(this, object : PersonAdapter.OnActionListener {
            override fun onAccepted(person: Person, position: Int) {
                viewModel.updateUserAction(person.email, ACTION_ACCEPTED)
            }

            override fun onDeclined(person: Person, position: Int) {
                viewModel.updateUserAction(person.email, ACTION_REJECTED)
            }
        })

        binding.rvData.adapter = personAdapter
    }
}
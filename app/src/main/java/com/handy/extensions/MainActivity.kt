package com.handy.extensions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.handy.extensions.databinding.ActivityMainBinding
import com.handy.extensions.extentions.dp2px

import com.handy.extensions.extentions.increaseViewSizeWithAnimation
import com.handy.extensions.extentions.views.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.start.setOnClickListener {
            binding.btn.increaseViewSizeWithAnimation(
                300.dp2px(applicationContext),
                duration = 500L
            ) {}
        }

    }
}
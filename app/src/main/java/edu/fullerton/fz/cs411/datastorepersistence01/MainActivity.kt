package edu.fullerton.fz.cs411.datastorepersistence01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

const val LOG_TAG = "DataStoreExample"
const val COUNTER_KEY = "CounterKey"

class MainActivity : AppCompatActivity() {

    private lateinit var counterText: TextView
    private lateinit var increaseButton: Button

    private val counterViewModel: CounterViewModel by lazy {
        MyAppRepository.initialize(this)
        ViewModelProvider(this)[CounterViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        counterText = findViewById(R.id.counter_text)
        increaseButton = findViewById(R.id.increase_button)

        increaseButton.setOnClickListener {
            val counterValue = counterViewModel.getCounter()
            val newCounterValue = counterValue + 1
            counterViewModel.setCounter(newCounterValue)
            counterText.text = newCounterValue.toString()
        }

        counterViewModel.loadCounter()
        counterText.text = counterViewModel.getCounter().toString()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(LOG_TAG, "The counter value is saved")
        outState.putInt(COUNTER_KEY, counterViewModel.getCounter())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOG_TAG, "onDestroy called")
    }
    override fun onStart() {
        super.onStart()
        Log.d(LOG_TAG, "onStart called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(LOG_TAG, "onStop called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG, "onResume called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(LOG_TAG, "onPause called")
    }

}
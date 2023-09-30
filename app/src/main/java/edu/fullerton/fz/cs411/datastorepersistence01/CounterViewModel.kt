package edu.fullerton.fz.cs411.datastorepersistence01

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Thread.sleep

const val INITIAL_COUNTER_VALUE = 0

class CounterViewModel: ViewModel() {

    private var counter = INITIAL_COUNTER_VALUE

    private val prefs = MyAppRepository.getRepository()

    private fun saveCounter() {
        viewModelScope.launch {
            prefs.saveCounter(counter)
            Log.d(LOG_TAG, "Saving the counter $counter")
        }
    }
    fun loadCounter() {
        GlobalScope.launch {
            prefs.counter.collectLatest {
                counter = it
                Log.d(LOG_TAG, "Loaded the counter $counter")
            }
        }
        sleep(1000)
    }

    fun getCounter(): Int {
        return counter
    }
    fun setCounter(newCounterValue: Int) {
        counter = newCounterValue
        saveCounter()
    }

}
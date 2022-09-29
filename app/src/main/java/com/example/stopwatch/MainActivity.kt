package com.example.stopwatch


import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var stopwatch : Chronometer
    private var running = false
    private var offset: Long = 0

    private val OFFSET_KEY = "offset"
    private val RUNNING_KEY = "running"
    private val BASE_KEY = "base"

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        stopwatch = findViewById(R.id.stopwatch)
        if(savedInstanceState !=null){
            offset = savedInstanceState.getLong(OFFSET_KEY)
            running = savedInstanceState.getBoolean(RUNNING_KEY)
            if(running){
                stopwatch.base = savedInstanceState.getLong(BASE_KEY)
                stopwatch.start()
            }else setBaseTime()
        }
        val startWatch = findViewById<Button>(R.id.btn_start)
        startWatch.setOnClickListener{
            if(!running){
                setBaseTime()
                stopwatch.start()
                running = true
            }
        }

        val pauseWatch = findViewById<Button>(R.id.btn_pause)
        pauseWatch.setOnClickListener{
            if(running){
                saveOffset()
                stopwatch.stop()
                running = false
            }
        }

        val resetButton = findViewById<Button>(R.id.btn_reset)
        resetButton.setOnClickListener{
            offset = 0
            setBaseTime()
        }
    }
    override fun onRestart() {
        super.onRestart()
        if(running){
            setBaseTime()
            stopwatch.start()
            offset = 0
        }
    }

    override fun onStop() {
        super.onStop()
        if(running){
            saveOffset()
            stopwatch.stop()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putLong(OFFSET_KEY,offset)
        savedInstanceState.putBoolean(RUNNING_KEY,running)
        savedInstanceState.putLong(BASE_KEY,stopwatch.base)
        super.onSaveInstanceState(savedInstanceState)
    }

    private fun setBaseTime(){
        stopwatch.base = SystemClock.elapsedRealtime()-offset

    }

    private fun saveOffset(){
        offset = SystemClock.elapsedRealtime()-stopwatch.base
    }
}
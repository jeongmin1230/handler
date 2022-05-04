package com.example.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.example.thread.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    val TAG : String = "jeongmin"

    lateinit var binding : ActivityMainBinding
    var workTime = 0
    var workTimeStarted = false
    var started = false
    
    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            if(started){
                when{
                    workTimeStarted -> {
                        binding.txtWorkTime.text = workTime.toString()
                        Log.d(TAG, "workTime : $workTime")
                        workTime -= 1
                        if(workTime == -1){
                            workTimeStarted = false
                        }
                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnStart.setOnClickListener {
            started = true
            binding.btnStart.isEnabled = false
            workTimerStart()
        }
        binding.btnStop.setOnClickListener {
            endTimer()
            binding.btnStart.isEnabled = true
        }
    }
    private fun workTimerStart(){
        workTime = 20
        workTimeStarted = true
        thread(start=true){
            while(workTime >= 0){
                Log.d("MyDebug", "workTime : $workTime")
                handler.sendEmptyMessage(0)
                Thread.sleep(1000)
                if(!workTimeStarted) {
                    Log.d(TAG, "타이머 끝남")
                    break
                }
            }
        }
    }
    private fun endTimer(){
        started = false
        workTimeStarted = false
        workTime = 0
        binding.txtWorkTime.text = workTime.toString()
    }
}
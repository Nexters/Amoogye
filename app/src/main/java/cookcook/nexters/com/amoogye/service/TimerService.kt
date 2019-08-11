package cookcook.nexters.com.amoogye.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import cookcook.nexters.com.amoogye.utils.TimerCode

class TimerService : Service() {

    lateinit var timerServiceBind: IBinder

    lateinit var timerStatus: TimerCode

    override fun onBind(intent: Intent?): IBinder? {
        return this.timerServiceBind
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        this.timerServiceBind = TimerServiceBinder(this)
        this.timerStatus = TimerCode.STATE_WAIT
    }

    fun onStartCountdown() {
        this.timerStatus = TimerCode.STATE_PROGRESS
    }

    fun onPauseCountdown() {
        this.timerStatus = TimerCode.STATE_PAUSE
    }

    fun onStopCountdown() {
        this.timerStatus = TimerCode.STATE_END
    }
}
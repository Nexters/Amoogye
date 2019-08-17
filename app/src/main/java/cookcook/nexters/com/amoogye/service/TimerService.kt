package cookcook.nexters.com.amoogye.service

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import cookcook.nexters.com.amoogye.utils.TimerStatus

const val COUNTDOWN_TICK_INTERVALL = 10L

class TimerService : Service() {

    lateinit var timerServiceBinder: IBinder
    lateinit var timerStatus: TimerStatus

    private lateinit var countDownTimer: CountDownTimer
    private var remindMilliSeconds: Long = 0
    private var initialMilliSeconds: Long = 0

    override fun onBind(intent: Intent?): IBinder? {
        return timerServiceBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        timerServiceBinder = TimerServiceBinder(this)
        timerStatus = TimerStatus.STATE_WAIT
    }

    fun setRemindTimes(millisSeconds: Long) {
        remindMilliSeconds = millisSeconds
    }

    fun setInitialTimes(millisSeconds: Long) {
        initialMilliSeconds = millisSeconds
        remindMilliSeconds = millisSeconds
    }

    fun getRemindTimes() = remindMilliSeconds

    fun getInitialTimes() = initialMilliSeconds

    /**
     * setInitialTImesTimes -> startOrContinue() : 초기 시작
     * onPauseCountdown() -> startOrContinue() : 멈췄다 시작
     */
    fun onStartCountdown() {
        timerStatus = TimerStatus.STATE_PROGRESS
        startOrContinue(initialMilliSeconds)
    }

    fun onPauseCountdown() {
        timerStatus = TimerStatus.STATE_PAUSE
        countDownTimer.cancel()
    }

    fun onRestartCountdown() {
        timerStatus = TimerStatus.STATE_PROGRESS
        startOrContinue(remindMilliSeconds)
    }

    fun onStopCountdown() {
        remindMilliSeconds = 0
        timerStatus = TimerStatus.STATE_END
        countDownTimer.cancel()
    }

    fun onCancelCountdown() {
        remindMilliSeconds = 0
        timerStatus = TimerStatus.STATE_WAIT
        countDownTimer.cancel()
    }

    private fun startOrContinue(millis: Long) {
        timerStatus = TimerStatus.STATE_PROGRESS
        startTimer(millis)
    }

    private fun startTimer(millis: Long) {
        countDownTimer = object : CountDownTimer(millis, COUNTDOWN_TICK_INTERVALL) {
            override fun onTick(millisUntilFinished: Long) {
                remindMilliSeconds = millisUntilFinished
            }

            override fun onFinish() {
                onStopCountdown()
            }
        }.start()
    }

    fun getState(): TimerStatus {
        return timerStatus
    }

    override fun onDestroy() {
        onStopCountdown()
        super.onDestroy()
    }
}
package cookcook.nexters.com.amoogye.service

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import cookcook.nexters.com.amoogye.utils.TimerStatus

const val COUNTDOWN_TICK_INTERVALL = 10L

class TimerService : Service() {

    lateinit var timerServiceBinder: IBinder
    lateinit var timerStatus: TimerStatus

    private lateinit var countDownTimer: CountDownTimer
    private var remindMilliSeconds: Long = 0
    private var initialMilliSeconds: Long = 0

    override fun onBind(intent: Intent?): IBinder? {
        return this.timerServiceBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        this.timerServiceBinder = TimerServiceBinder(this)
        this.timerStatus = TimerStatus.STATE_WAIT
    }

    fun setRemindTimes(millisSeconds: Long) {
        this.remindMilliSeconds = millisSeconds
    }

    fun setInitialTimes(millisSeconds: Long) {
        initialMilliSeconds = millisSeconds;
    }

    /**
     * setInitialTImesTimes -> startOrContinue() : 초기 시작
     * onPauseCountdown() -> startOrContinue() : 멈췄다 시작
     */
    fun onStartCountdown() {
        this.timerStatus = TimerStatus.STATE_PROGRESS
        startOrContinue(initialMilliSeconds)
    }

    fun onPauseCountdown() {
        this.timerStatus = TimerStatus.STATE_PAUSE
        countDownTimer.cancel()
    }

    fun onStopCountdown() {
        remindMilliSeconds = 0
        this.timerStatus = TimerStatus.STATE_END
        countDownTimer.onFinish()
    }

    fun startOrContinue(millis: Long) {
        this.timerStatus = TimerStatus.STATE_PROGRESS
        startTimer(millis)
    }

    fun startTimer(millis: Long) {
        this.countDownTimer = object : CountDownTimer(millis, COUNTDOWN_TICK_INTERVALL) {
            override fun onFinish() {
                onStopCountdown()
            }

            override fun onTick(millisUntilFinished: Long) {
                remindMilliSeconds = millisUntilFinished
            }
        }.start()
    }

    fun getState(): TimerStatus {
        return this.timerStatus
    }

    override fun onDestroy() {
        onStopCountdown()
        super.onDestroy()
    }
}
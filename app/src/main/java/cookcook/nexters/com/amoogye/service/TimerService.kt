package cookcook.nexters.com.amoogye.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import cookcook.nexters.com.amoogye.R
import cookcook.nexters.com.amoogye.utils.TimerStatus


const val COUNTDOWN_TICK_INTERVALL = 10L

class TimerService : Service() {

    lateinit var timerServiceBinder: IBinder
    lateinit var timerStatus: TimerStatus

    private lateinit var countDownTimer: CountDownTimer
    private var remindMilliSeconds: Long = 0
    private var initialMilliSeconds: Long = 0

    private var isVibration: Boolean = true
    private var isSound: Boolean = true

    fun setSound(isSound: Boolean) {
        this.isSound = isSound
    }

    fun setVibration(isVibration: Boolean) {
        this.isVibration = isVibration
    }

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
                makeNotification()
                onStopCountdown()
            }
        }.start()
    }

    private fun changeTimeFromMilli(): String {
        var initTime = this.initialMilliSeconds

        initTime /= 1000

        var result = ""
        if (initTime / 3600 > 0) {
            result += ("${initTime / 3600} 시간 ")
            initTime %= 3600
        }

        if (initTime / 60 > 0) {
            result += ("${initTime / 60} 분 ")
            initTime %= 60
        }

        result += "${initTime}초 타이머가 종료되었습니다."

        return result
    }

    private fun makeNotification() {

        var notification = NotificationCompat.Builder(this, "TIMER")
            .setSmallIcon(R.drawable.amoogye_logo)
            .setContentTitle("타이머 종료")
            .setContentText(changeTimeFromMilli())
            .setPriority(Notification.PRIORITY_DEFAULT)



        Log.d("TAG", "sound is ${isSound}")
        Log.d("TAG", "vibration is ${isVibration}")

        if (isSound) {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            notification.setSound(alarmSound)
        } else {
            notification.priority = NotificationCompat.PRIORITY_LOW
            notification.setSound(null)
        }

        if (isVibration) {
            notification.setVibrate(longArrayOf(1000, 1000))
        } else {
            notification.priority = NotificationCompat.PRIORITY_LOW
            notification.setVibrate(null)
        }

        if (!isVibration && !isSound) {
            notification.setOnlyAlertOnce(true)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "TIMER NAME"
            val descriptionText = "TIMER DESCRIPTION"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("TIMER", name, importance).apply {
                description = descriptionText
                if (isSound) {
                    var audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                        .build()
                    val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    this.setSound(alarmSound, audioAttributes)
                } else {
                    this.setSound(null, null)
                }
                this.enableVibration(isVibration)
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(1, notification.build())
        }
    }

    fun getState(): TimerStatus {
        return timerStatus
    }

    override fun onDestroy() {
        onStopCountdown()
        super.onDestroy()
    }
}
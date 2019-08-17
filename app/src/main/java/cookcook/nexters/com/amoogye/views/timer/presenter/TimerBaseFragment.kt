package cookcook.nexters.com.amoogye.views.timer.presenter

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import cookcook.nexters.com.amoogye.base.BaseFragment
import cookcook.nexters.com.amoogye.service.TimerService
import cookcook.nexters.com.amoogye.service.TimerServiceBinder
import cookcook.nexters.com.amoogye.utils.TimerStatus
import java.util.*

abstract class TimerBaseFragment : BaseFragment() {

    private val TAG = TimerBaseFragment::class.java.simpleName

    private var serviceConnection: ServiceConnection? = null
    private var timerService: TimerService? = null
    private lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceIntent = Intent(context, TimerService::class.java)
    }

    fun isServiceBound(): Boolean {
        return this.timerService != null
    }

    private fun startAndBindTimerService() {
        Log.d("TAG", "service is bound ${isServiceBound()}")
        if (isServiceBound()) {
            this.afterServiceConnected(getTimerService()!!.timerStatus)
            return
        }

        this.startTimerService()
        this.initServiceConnection()
        context!!.bindService(this.serviceIntent, this.serviceConnection!!, 0)
        Log.d("TAG", "service is bound ${isServiceBound()}")
    }

    private fun startTimerService() {
        context!!.startService(serviceIntent)
    }

    private fun initServiceConnection() {
        this.serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val timer: TimerServiceBinder = service!! as TimerServiceBinder
                onServiceConnected(timer)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                onServiceDisconnected()
            }
        }
    }

    private fun onServiceConnected(binder: TimerServiceBinder) {
        this.timerService = binder.timerService
        afterServiceConnected(getTimerService()!!.timerStatus)
    }

    private fun onServiceDisconnected() {
        this.timerService = null
        this.serviceConnection = null
        this.onAfterServiceDisconnected()
    }

    protected open fun unbindTimerService() {
        if (this.isServiceBound()) {
            this.onBeforeServiceDisconnected()
            context!!.unbindService(this.serviceConnection!!)
            this.timerService = null
            this.serviceConnection = null
        } else {
            Log.d(TAG, "service is not bound")
        }
    }


    fun afterServiceConnected(timerStatus: TimerStatus) {
        Log.d("TAG", "status is $timerStatus")
        val handleStates: Array<TimerStatus> = this.getHandledServiceStates()
        val finishingStats: Array<TimerStatus> = this.getFinishingServiceStates()

        if (Arrays.binarySearch(handleStates, timerStatus) >= 0) {
            handleState(timerStatus)
        } else if (Arrays.binarySearch(finishingStats, timerStatus) >= 0) {
            onBeforeFinish()
        }
    }

    protected open fun getTimerService(): TimerService? {
        if (isServiceBound()) {
            return this.timerService!!
        } else {
            throw IllegalStateException("TimerService is not bound to activity")
        }
    }

    protected abstract fun getHandledServiceStates(): Array<TimerStatus>
    protected abstract fun getFinishingServiceStates(): Array<TimerStatus>

    protected open fun handleState(timerStatus: TimerStatus) {}


    protected open fun onAfterServiceDisconnected() {}

    protected open fun onBeforeServiceDisconnected() {}
    protected open fun onBeforeFinish() {}

    override fun subscribeUI() {

    }

    override fun onResume() {
        super.onResume()
        startAndBindTimerService()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
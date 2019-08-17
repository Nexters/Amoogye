package cookcook.nexters.com.amoogye.service

import android.os.Binder


class TimerServiceBinder(val timerService: TimerService) : Binder()
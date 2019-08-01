package cookcook.nexters.com.amoogye

import cookcook.nexters.com.amoogye.views.main.presenter.MainViewModel
import cookcook.nexters.com.amoogye.views.main.domain.MainRepository
import cookcook.nexters.com.amoogye.views.main.data.MainRepositoryImpl
import cookcook.nexters.com.amoogye.views.timer.data.TimerRepositoryImpl
import cookcook.nexters.com.amoogye.views.timer.domain.TimerRepository
import cookcook.nexters.com.amoogye.views.timer.presenter.TimerViewModel
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val appModule = module {

    single<MainRepository> { MainRepositoryImpl() }

    viewModel { MainViewModel(get()) }

    single<TimerRepository> {TimerRepositoryImpl()}
    viewModel { TimerViewModel(get()) }
}
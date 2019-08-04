package cookcook.nexters.com.amoogye

import cookcook.nexters.com.amoogye.views.calc.data.CalculatorRepositoryImpl
import cookcook.nexters.com.amoogye.views.calc.domain.CalculatorRepository
import cookcook.nexters.com.amoogye.views.calc.presenter.CalculatorViewModel
import cookcook.nexters.com.amoogye.views.main.presenter.MainViewModel
import cookcook.nexters.com.amoogye.views.main.domain.MainRepository
import cookcook.nexters.com.amoogye.views.main.data.MainRepositoryImpl
import cookcook.nexters.com.amoogye.views.timer.data.TimerRepositoryImpl
import cookcook.nexters.com.amoogye.views.timer.domain.TimerRepository
import cookcook.nexters.com.amoogye.views.timer.presenter.TimerViewModel
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val calcModule = module {
    single<CalculatorRepository> { CalculatorRepositoryImpl() }
    viewModel { CalculatorViewModel(get()) }
}

val timerModule = module {
    single<TimerRepository> { TimerRepositoryImpl() }
    viewModel { TimerViewModel(get()) }
}

val mainModule = module {
    single<MainRepository> { MainRepositoryImpl() }
    viewModel { MainViewModel(get()) }
}

val appModule = listOf(mainModule, calcModule, timerModule)
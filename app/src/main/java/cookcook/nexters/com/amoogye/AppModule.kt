package cookcook.nexters.com.amoogye

import cookcook.nexters.com.amoogye.main.MainViewModel
import cookcook.nexters.com.amoogye.main.domain.MainRepository
import cookcook.nexters.com.amoogye.main.service.MainRepositoryImpl
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val appModule = module {

    single<MainRepository> { MainRepositoryImpl() }

    viewModel { MainViewModel(get()) }
}
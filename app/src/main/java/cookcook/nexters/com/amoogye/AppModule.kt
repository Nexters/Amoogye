package cookcook.nexters.com.amoogye

import cookcook.nexters.com.amoogye.views.main.presenter.MainViewModel
import cookcook.nexters.com.amoogye.views.main.domain.MainRepository
import cookcook.nexters.com.amoogye.views.main.data.MainRepositoryImpl
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

val appModule = module {

    single<MainRepository> { MainRepositoryImpl() }

    viewModel { MainViewModel(get()) }
}
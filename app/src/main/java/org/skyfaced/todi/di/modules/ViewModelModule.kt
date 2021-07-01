package org.skyfaced.todi.di.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.skyfaced.todi.ui.detail.DetailViewModel
import org.skyfaced.todi.ui.home.HomeViewModel
import org.skyfaced.todi.ui.signUp.SignUpViewModel

val viewModelModule = module {
    viewModel { SignUpViewModel() }

    viewModel { HomeViewModel(get()) }

    viewModel { DetailViewModel(get()) }
}
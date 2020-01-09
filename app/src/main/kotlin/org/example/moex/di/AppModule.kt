package org.example.moex.di

import org.example.moex.ui.shares.SharesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SharesViewModel(get()) }
}

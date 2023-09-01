package com.demo.takenote.di

import com.demo.takenote.ui.addnote.AddNoteViewModel
import com.demo.takenote.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { AddNoteViewModel(get()) }
}
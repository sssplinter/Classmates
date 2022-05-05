package com.breaktime.classmates.di

import com.breaktime.classmates.di.modules.loaderModule
import com.breaktime.classmates.di.modules.repositoryModule
import com.breaktime.classmates.di.modules.useCaseModule
import com.breaktime.classmates.di.modules.viewModelModule
import org.kodein.di.DI

val kodein = DI {
    importAll(viewModelModule, useCaseModule, repositoryModule, loaderModule)
}
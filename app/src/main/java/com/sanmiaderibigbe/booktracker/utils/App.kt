//package com.sanmiaderibigbe.booktracker
//
//import android.app.Application
//import com.sanmiaderibigbe.booktracker.di.DependencyModules
//import org.koin.android.ext.android.startKoin
//
//class App : Application() {
//
//    override fun onCreate() {
//        super.onCreate()
//
//        startKoin(this, listOf(
//                DependencyModules.appModules
//        ))
//    }
//}
package com.example.athousandcourses.di

import com.example.athousandcourses.presentation.account.AccountFragment
import com.example.athousandcourses.presentation.courseinfo.CourseInfoFragment
import com.example.athousandcourses.presentation.favorite.FavoriteFragment
import com.example.athousandcourses.presentation.home.HomeFragment
import com.example.athousandcourses.presentation.login.LoginFragment
import dagger.Component

@Component(modules = [AppModule::class,DomainModule::class,DataModule::class])
interface AppComponent {
    fun inject(loginFragment: LoginFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(courseInfoFragment: CourseInfoFragment)
    fun inject(favoriteFragment: FavoriteFragment)
    fun inject(accountFragment: AccountFragment)
}
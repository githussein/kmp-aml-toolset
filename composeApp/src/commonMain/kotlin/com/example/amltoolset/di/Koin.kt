package com.example.amltoolset.di

import com.example.aml_kyc_tool.SearchScreen.SearchViewModel
import com.example.aml_kyc_tool.data.LocalJsonPersonRepository
import com.example.aml_kyc_tool.data.PersonRepository
import com.example.amltoolset.LoginScreen.LoginViewModel
import com.example.amltoolset.data.UNSanctionsService
import com.example.amltoolset.data.UNSanctionsServiceImpl
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.dsl.module

val repositoryModule = module {
    single<PersonRepository> { LocalJsonPersonRepository() }
}


val viewmodelModule = module {
    factory { SearchViewModel(get()) }
    factory { LoginViewModel() }
}

val networkModule = module {
    single { HttpClient() } // Simple HttpClient without extra configuration
    single<UNSanctionsService> { UNSanctionsServiceImpl(get()) }
}


fun initKoin() {
    startKoin {
        modules(repositoryModule + viewmodelModule + networkModule)
    }
}
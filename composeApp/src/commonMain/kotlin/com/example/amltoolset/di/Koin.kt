package com.example.amltoolset.di

import com.example.aml_kyc_tool.SearchScreen.SearchViewModel
import com.example.aml_kyc_tool.data.LocalJsonPersonRepository
import com.example.aml_kyc_tool.data.PersonRepository
import com.example.amltoolset.ui.LoginScreen.LoginViewModel
import com.example.amltoolset.data.SearchService
import com.example.amltoolset.data.SearchServiceImpl
import com.example.amltoolset.data.UNSanctionsRepository
import com.example.amltoolset.data.UNSanctionsRepositoryImpl
import com.example.amltoolset.data.UNSanctionsService
import com.example.amltoolset.data.UNSanctionsServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.xml.xml
import org.koin.core.context.startKoin
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) { xml() }
        }
    }
    single<UNSanctionsService> { UNSanctionsServiceImpl(get()) }
}


val repositoryModule = module {
    single<PersonRepository> { LocalJsonPersonRepository() }
    single<UNSanctionsRepository> { UNSanctionsRepositoryImpl(get()) }
}

val serviceModule = module {
    single { SearchServiceImpl(localRepository = get(), unRepository = get()) }
}

val viewmodelModule = module {
    factory { SearchViewModel(get()) }
    factory { LoginViewModel() }
}


fun initKoin() {
    startKoin {
        modules(modules = networkModule + repositoryModule + serviceModule + viewmodelModule)
    }
}
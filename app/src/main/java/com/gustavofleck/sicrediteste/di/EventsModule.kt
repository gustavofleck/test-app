package com.gustavofleck.sicrediteste.di

import com.gustavofleck.data.api.EventsServiceProvider
import com.gustavofleck.data.errors.handler.EventsErrorHandler
import com.gustavofleck.data.mappers.EventMapper
import com.gustavofleck.data.repository.EventListRepositoryImpl
import com.gustavofleck.data.sources.EventListDataSource
import com.gustavofleck.domain.repository.EventListRepository
import com.gustavofleck.domain.usecases.FetchEventListUseCase
import com.gustavofleck.presentation.viewmodels.EventListViewModel
import org.koin.dsl.module

private val presentationModule = module {
    factory { EventListViewModel(fetchEventList = get()) }
}

private val domainModule = module {
    factory { FetchEventListUseCase(repository = get()) }
}

private val dataModule = module {
    single { EventsServiceProvider.serviceInstance() }

    factory<EventListRepository> {
        EventListRepositoryImpl(
            dataSource = get(),
            errorHandler = get()
        )
    }
    factory {
        EventListDataSource(
            service = get(),
            mapper = EventMapper()
        )
    }
    factory { EventsErrorHandler() }
}

val eventModules = listOf(presentationModule, domainModule, dataModule)
package com.gustavofleck.sicrediteste.di

import com.gustavofleck.data.api.EventsServiceProvider
import com.gustavofleck.data.errors.handler.EventsErrorHandler
import com.gustavofleck.data.mappers.EventListMapper
import com.gustavofleck.data.mappers.EventMapper
import com.gustavofleck.data.repository.EventListRepositoryImpl
import com.gustavofleck.data.repository.EventDetailsRepositoryImpl
import com.gustavofleck.data.sources.EventDetailsDataSource
import com.gustavofleck.data.sources.EventListDataSource
import com.gustavofleck.domain.repository.EventListRepository
import com.gustavofleck.domain.repository.EventDetailsRepository
import com.gustavofleck.domain.usecases.FetchEventDetailsUseCase
import com.gustavofleck.domain.usecases.FetchEventListUseCase
import com.gustavofleck.presentation.viewmodels.EventListViewModel
import com.gustavofleck.presentation.viewmodels.EventManagementViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val presentationModule = module {
    viewModel { EventListViewModel(fetchEventList = get()) }
    viewModel { EventManagementViewModel(fetchEventDetails = get()) }
}

private val domainModule = module {
    factory { FetchEventListUseCase(repository = get()) }
    factory { FetchEventDetailsUseCase(eventDetailsRepository = get()) }
}

private val dataModule = module {
    val eventMapper = EventMapper()

    single { EventsServiceProvider.serviceInstance() }

    factory<EventListRepository> {
        EventListRepositoryImpl(
            dataSource = get(),
            errorHandler = get()
        )
    }
    factory<EventDetailsRepository> {
        EventDetailsRepositoryImpl(
            dataSource = get(),
            errorHandler = get()
        )
    }
    factory {
        EventListDataSource(
            service = get(),
            mapper = EventListMapper(eventMapper)
        )
    }
    factory {
        EventDetailsDataSource(
            service = get(),
            eventMapper = eventMapper
        )
    }
    factory { EventsErrorHandler() }
}

val eventModules = listOf(presentationModule, domainModule, dataModule)
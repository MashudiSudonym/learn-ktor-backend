package com.example.di

import com.example.database.DatabaseFactory
import com.example.database.DatabaseFactoryImpl
import com.example.games.domain.GamesRepository
import com.example.games.domain.GamesRepositoryImpl
import com.example.orders.domain.OrdersRepository
import com.example.orders.domain.OrdersRepositoryImpl
import com.example.users.data.dao.UsersDAOFacade
import com.example.users.data.dao.UsersDAOFacadeImpl
import com.example.users.domain.UsersRepository
import com.example.users.domain.UsersRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single<DatabaseFactory> { DatabaseFactoryImpl() }
    single<UsersDAOFacade> { UsersDAOFacadeImpl() }
    single<UsersRepository> { UsersRepositoryImpl(get()) }
    single<GamesRepository> { GamesRepositoryImpl() }
    single<OrdersRepository> { OrdersRepositoryImpl(get()) }
}
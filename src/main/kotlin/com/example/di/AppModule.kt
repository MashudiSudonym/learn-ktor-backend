package com.example.di

import com.example.database.DatabaseFactory
import com.example.database.DatabaseFactoryImpl
import com.example.games.repository.GamesRepository
import com.example.games.repository.GamesRepositoryImpl
import com.example.orders.repository.OrdersRepository
import com.example.orders.repository.OrdersRepositoryImpl
import com.example.users.dao.UsersDAOFacade
import com.example.users.dao.UsersDAOFacadeImpl
import com.example.users.repository.UsersRepository
import com.example.users.repository.UsersRepositoryImpl
import org.koin.dsl.module

val appModule = module {
    single<DatabaseFactory> { DatabaseFactoryImpl() }
    single<UsersDAOFacade> { UsersDAOFacadeImpl() }
    single<UsersRepository> { UsersRepositoryImpl(get()) }
    single<GamesRepository> { GamesRepositoryImpl() }
    single<OrdersRepository> { OrdersRepositoryImpl(get()) }
}
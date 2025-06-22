package net.ifmain.mnistscan.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.ifmain.mnistscan.domain.DigitClassifierUseCase
import net.ifmain.mnistscan.domain.DigitClassifierUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    @Singleton
    abstract fun bindDigitClassifierUseCase(
        impl: DigitClassifierUseCaseImpl
    ): DigitClassifierUseCase
}
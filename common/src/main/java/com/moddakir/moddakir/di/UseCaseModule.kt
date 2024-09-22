package com.moddakir.moddakir.di

import com.moddakir.moddakir.useCase.AuthenticationUseCase
import com.moddakir.moddakir.useCase.AuthenticationUseCaseImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    @ViewModelScoped
    abstract fun bindAuthenticationUseCase(authenticationUseCaseImp: AuthenticationUseCaseImp): AuthenticationUseCase

}
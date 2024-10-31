package com.moddakir.moddakir.di

import com.moddakir.moddakir.useCase.AuthenticationUseCase
import com.moddakir.moddakir.useCase.AuthenticationUseCaseImp
import com.moddakir.moddakir.useCase.BannerUseCase
import com.moddakir.moddakir.useCase.BannerUseCaseImp
import com.moddakir.moddakir.useCase.SessionsUseCase
import com.moddakir.moddakir.useCase.SessionsUseCaseImp
import com.moddakir.moddakir.useCase.SettingsUseCase
import com.moddakir.moddakir.useCase.SettingsUseCaseImp
import com.moddakir.moddakir.useCase.StaticPagesUseCase
import com.moddakir.moddakir.useCase.StaticPagesUseCaseImp
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

    @Binds
    @ViewModelScoped
    abstract fun bindStaticPagesUseCase(staticPagesUseCaseImp: StaticPagesUseCaseImp): StaticPagesUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSettingsUseCase(settingsUseCaseImp: SettingsUseCaseImp): SettingsUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindBannerUseCase(bannerUseCaseImp: BannerUseCaseImp): BannerUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindSessionsUseCase(sessionsUseCaseImp: SessionsUseCaseImp): SessionsUseCase

}
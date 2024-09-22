package com.moddakir.moddakir.di

import com.moddakir.moddakir.network.remote.RemoteRepository
import com.moddakir.moddakir.network.remote.RemoteRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RemoteRepositoryModel {
    @Binds
    @ViewModelScoped
    abstract fun bindRemoteRepository(remoteRepositoryImp: RemoteRepositoryImp): RemoteRepository

}
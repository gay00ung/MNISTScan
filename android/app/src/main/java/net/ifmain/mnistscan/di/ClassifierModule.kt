package net.ifmain.mnistscan.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.ifmain.mnistscan.data.tflite.DigitClassifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClassifierModule {

    @Provides
    @Singleton
    fun provideDigitClassifier(
        @ApplicationContext context: Context
    ): DigitClassifier = DigitClassifier(context)
}

/*
Copyright 2022 Savvas Dalkitsis

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.savvasdalkitsis.uhuruphotos.log.module

import android.content.Context
import com.michaelflisar.lumberjack.FileLoggingSetup
import com.michaelflisar.lumberjack.FileLoggingTree
import com.michaelflisar.lumberjack.L
import com.savvasdalkitsis.uhuruphotos.log.BuildConfig
import com.savvasdalkitsis.uhuruphotos.log.NoOpTree
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import timber.log.ConsoleTree
import timber.log.Timber
import java.util.logging.Logger
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class LogModule {

    @Provides
    @Singleton
    fun loggingSetup(@ApplicationContext context: Context): FileLoggingSetup =
        FileLoggingSetup.DateFiles(context)

    @Provides
    @IntoSet
    fun consoleTree(): Timber.Tree = when  {
        BuildConfig.DEBUG -> ConsoleTree()
        else -> NoOpTree()
    }

    @Provides
    @IntoSet
    fun fileTree(
        loggingSetup: FileLoggingSetup,
    ): Timber.Tree = FileLoggingTree(loggingSetup)
}
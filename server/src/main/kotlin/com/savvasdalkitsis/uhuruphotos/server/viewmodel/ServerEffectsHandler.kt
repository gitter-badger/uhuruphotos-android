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
package com.savvasdalkitsis.uhuruphotos.server.viewmodel

import com.savvasdalkitsis.api.log.usecase.FeedbackUseCase
import com.savvasdalkitsis.uhuruphotos.navigation.ControllersProvider
import com.savvasdalkitsis.uhuruphotos.server.mvflow.ServerEffect
import com.savvasdalkitsis.uhuruphotos.server.mvflow.ServerEffect.Close
import com.savvasdalkitsis.uhuruphotos.server.mvflow.ServerEffect.ErrorLoggingIn
import com.savvasdalkitsis.uhuruphotos.server.mvflow.ServerEffect.SendFeedback
import com.savvasdalkitsis.uhuruphotos.strings.R
import com.savvasdalkitsis.uhuruphotos.toaster.Toaster
import com.savvasdalkitsis.uhuruphotos.viewmodel.EffectHandler
import javax.inject.Inject

class ServerEffectsHandler @Inject constructor(
    private val controllersProvider: ControllersProvider,
    private val toaster: Toaster,
    private val feedbackUseCase: FeedbackUseCase,
) : EffectHandler<ServerEffect> {

    override suspend fun handleEffect(effect: ServerEffect) {
        when (effect) {
            Close -> controllersProvider.navController!!.popBackStack()
            is ErrorLoggingIn -> toaster.show(R.string.error_logging_in)
            SendFeedback -> feedbackUseCase.sendFeedback()
        }
    }
}
package com.savvasdalkitsis.librephotos.server.mvflow

import dev.zacsweers.redacted.annotations.Redacted

sealed class ServerMutation {

    data class AskForServerDetails(val previousUrl: String?) : ServerMutation()
    data class AskForUserCredentials(val userName: String, @Redacted val password: String) : ServerMutation()
    object PerformingBackgroundJob : ServerMutation()
    data class ChangeUrlTo(val url: String) : ServerMutation()
    data class ChangeUsernameTo(val username: String) : ServerMutation()
    data class ChangePasswordTo(@Redacted val password: String) : ServerMutation()
}

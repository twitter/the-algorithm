package com.twitter.unified_user_actions.service.module

import com.google.inject.Provides
import com.twitter.finagle.thrift.ClientId
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import javax.inject.Singleton

object ClientIdModule extends TwitterModule {
  private final val flagName = "thrift.client.id"

  flag[String](
    name = flagName,
    help = "Thrift Client ID"
  )

  @Provides
  @Singleton
  def providesClientId(
    @Flag(flagName) thriftClientId: String,
  ): ClientId = ClientId(
    name = thriftClientId
  )
}

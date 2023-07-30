package com.X.unified_user_actions.service.module

import com.google.inject.Provides
import com.X.finagle.thrift.ClientId
import com.X.inject.XModule
import com.X.inject.annotations.Flag
import javax.inject.Singleton

object ClientIdModule extends XModule {
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

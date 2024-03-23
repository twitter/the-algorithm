package com.ExTwitter.cr_mixer.module
import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.strato.client.{Client => StratoClient}
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.frigate.common.store.strato.StratoFetchableStore
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.usersignalservice.thriftscala.BatchSignalRequest
import com.ExTwitter.usersignalservice.thriftscala.BatchSignalResponse
import javax.inject.Named

object UserSignalServiceColumnModule extends ExTwitterModule {
  private val UssColumnPath = "recommendations/user-signal-service/signals"

  @Provides
  @Singleton
  @Named(ModuleNames.UssStratoColumn)
  def providesUserSignalServiceStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
  ): ReadableStore[BatchSignalRequest, BatchSignalResponse] = {
    ObservedReadableStore(
      StratoFetchableStore
        .withUnitView[BatchSignalRequest, BatchSignalResponse](stratoClient, UssColumnPath))(
      statsReceiver.scope("user_signal_service_store"))
  }
}

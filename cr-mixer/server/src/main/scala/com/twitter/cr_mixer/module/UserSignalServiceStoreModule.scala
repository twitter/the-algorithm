package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.strato.client.{Client => StratoClient}
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.source_signal.UssStore
import com.ExTwitter.cr_mixer.source_signal.UssStore.Query
import com.ExTwitter.frigate.common.store.strato.StratoFetchableStore
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.usersignalservice.thriftscala.BatchSignalRequest
import com.ExTwitter.usersignalservice.thriftscala.BatchSignalResponse
import com.ExTwitter.usersignalservice.thriftscala.SignalType
import com.ExTwitter.usersignalservice.thriftscala.{Signal => UssSignal}
import javax.inject.Named

object UserSignalServiceStoreModule extends ExTwitterModule {

  private val UssColumnPath = "recommendations/user-signal-service/signals"

  @Provides
  @Singleton
  @Named(ModuleNames.UssStore)
  def providesUserSignalServiceStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
  ): ReadableStore[Query, Seq[(SignalType, Seq[UssSignal])]] = {
    ObservedReadableStore(
      UssStore(
        StratoFetchableStore
          .withUnitView[BatchSignalRequest, BatchSignalResponse](stratoClient, UssColumnPath),
        statsReceiver))(statsReceiver.scope("user_signal_service_store"))
  }
}

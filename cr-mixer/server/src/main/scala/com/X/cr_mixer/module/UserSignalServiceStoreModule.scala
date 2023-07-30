package com.X.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.finagle.stats.StatsReceiver
import com.X.inject.XModule
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import com.X.cr_mixer.model.ModuleNames
import com.X.cr_mixer.source_signal.UssStore
import com.X.cr_mixer.source_signal.UssStore.Query
import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.hermit.store.common.ObservedReadableStore
import com.X.usersignalservice.thriftscala.BatchSignalRequest
import com.X.usersignalservice.thriftscala.BatchSignalResponse
import com.X.usersignalservice.thriftscala.SignalType
import com.X.usersignalservice.thriftscala.{Signal => UssSignal}
import javax.inject.Named

object UserSignalServiceStoreModule extends XModule {

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

package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.cr_mixer.source_signal.UssStore
import com.twitter.cr_mixer.source_signal.UssStore.Query
import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.usersignalservice.thriftscala.BatchSignalRequest
import com.twitter.usersignalservice.thriftscala.BatchSignalResponse
import com.twitter.usersignalservice.thriftscala.SignalType
import com.twitter.usersignalservice.thriftscala.{Signal => UssSignal}
import javax.inject.Named

object UserSignalServiceStoreModule extends TwitterModule {

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

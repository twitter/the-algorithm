package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.app.Flag
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.common.UserId
import com.twitter.hermit.stp.thriftscala.STPResult
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.{Client => StratoClient}
import javax.inject.Named

object StrongTiePredictionStoreModule extends TwitterModule {

  private val strongTiePredictionColumnPath: Flag[String] = flag[String](
    name = "crMixer.strongTiePredictionColumnPath",
    default = "onboarding/userrecs/strong_tie_prediction_big",
    help = "Strato column path for StrongTiePredictionStore"
  )

  @Provides
  @Singleton
  @Named(ModuleNames.StpStore)
  def providesStrongTiePredictionStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
  ): ReadableStore[UserId, STPResult] = {
    val strongTiePredictionStratoFetchableStore = StratoFetchableStore
      .withUnitView[UserId, STPResult](stratoClient, strongTiePredictionColumnPath())

    ObservedReadableStore(
      strongTiePredictionStratoFetchableStore
    )(statsReceiver.scope("strong_tie_prediction_big_store"))
  }
}

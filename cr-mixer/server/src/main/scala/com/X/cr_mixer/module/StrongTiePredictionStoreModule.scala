package com.X.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.app.Flag
import com.X.cr_mixer.model.ModuleNames
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.hermit.store.common.ObservedReadableStore
import com.X.inject.XModule
import com.X.simclusters_v2.common.UserId
import com.X.hermit.stp.thriftscala.STPResult
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import javax.inject.Named

object StrongTiePredictionStoreModule extends XModule {

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

package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.app.Flag
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.frigate.common.store.strato.StratoFetchableStore
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.hermit.stp.thriftscala.STPResult
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.strato.client.{Client => StratoClient}
import javax.inject.Named

object StrongTiePredictionStoreModule extends ExTwitterModule {

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

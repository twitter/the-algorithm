package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.ExTwitter.app.Flag
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.frigate.common.store.strato.StratoFetchableStore
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton
import com.ExTwitter.strato.client.{Client => StratoClient}
import com.ExTwitter.wtf.candidate.thriftscala.CandidateSeq

object RealGraphOonStoreModule extends ExTwitterModule {

  private val userRealGraphOonColumnPath: Flag[String] = flag[String](
    name = "crMixer.userRealGraphOonColumnPath",
    default = "recommendations/twistly/userRealgraphOon",
    help = "Strato column path for user real graph OON Store"
  )

  @Provides
  @Singleton
  @Named(ModuleNames.RealGraphOonStore)
  def providesRealGraphOonStore(
    stratoClient: StratoClient,
    statsReceiver: StatsReceiver
  ): ReadableStore[UserId, CandidateSeq] = {
    val realGraphOonStratoFetchableStore = StratoFetchableStore
      .withUnitView[UserId, CandidateSeq](stratoClient, userRealGraphOonColumnPath())

    ObservedReadableStore(
      realGraphOonStratoFetchableStore
    )(statsReceiver.scope("user_real_graph_oon_store"))
  }
}

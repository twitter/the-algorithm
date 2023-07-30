package com.X.cr_mixer.module

import com.google.inject.Provides
import com.X.app.Flag
import com.X.cr_mixer.model.ModuleNames
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.hermit.store.common.ObservedReadableStore
import com.X.inject.XModule
import com.X.simclusters_v2.common.UserId
import com.X.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton
import com.X.strato.client.{Client => StratoClient}
import com.X.wtf.candidate.thriftscala.CandidateSeq

object RealGraphOonStoreModule extends XModule {

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

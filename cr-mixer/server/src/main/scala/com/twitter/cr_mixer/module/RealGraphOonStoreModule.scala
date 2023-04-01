package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.twitter.app.Flag
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.common.UserId
import com.twitter.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.wtf.candidate.thriftscala.CandidateSeq

object RealGraphOonStoreModule extends TwitterModule {

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

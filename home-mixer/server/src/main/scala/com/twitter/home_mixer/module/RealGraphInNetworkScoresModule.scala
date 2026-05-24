package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.google.inject.name.Named
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RealGraphInNetworkScoresOnPrem
import com.twitter.home_mixer.param.HomeMixerInjectionNames.RealGraphManhattanEndpoint
import com.twitter.home_mixer.store.RealGraphInNetworkScoresStore
import com.twitter.inject.TwitterModule
import com.twitter.storage.client.manhattan.kv.ManhattanKVEndpoint
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.util.CommonTypes.ViewerId
import com.twitter.wtf.candidate.thriftscala.Candidate
import javax.inject.Singleton

object RealGraphInNetworkScoresModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(RealGraphInNetworkScoresOnPrem)
  def providesRealGraphInNetworkScoresFeaturesStore(
    @Named(RealGraphManhattanEndpoint) realGraphInNetworkScoresManhattanKVEndpoint: ManhattanKVEndpoint
  ): ReadableStore[ViewerId, Seq[Candidate]] = {
    new RealGraphInNetworkScoresStore(realGraphInNetworkScoresManhattanKVEndpoint)
  }
}

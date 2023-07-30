package com.X.home_mixer.module

import com.google.inject.Provides
import com.google.inject.name.Named
import com.X.home_mixer.param.HomeMixerInjectionNames.RealGraphInNetworkScores
import com.X.home_mixer.param.HomeMixerInjectionNames.RealGraphManhattanEndpoint
import com.X.home_mixer.store.RealGraphInNetworkScoresStore
import com.X.inject.XModule
import com.X.storage.client.manhattan.kv.ManhattanKVEndpoint
import com.X.storehaus.ReadableStore
import com.X.timelines.util.CommonTypes.ViewerId
import com.X.wtf.candidate.thriftscala.Candidate

import javax.inject.Singleton

object RealGraphInNetworkScoresModule extends XModule {

  @Provides
  @Singleton
  @Named(RealGraphInNetworkScores)
  def providesRealGraphInNetworkScoresFeaturesStore(
    @Named(RealGraphManhattanEndpoint) realGraphInNetworkScoresManhattanKVEndpoint: ManhattanKVEndpoint
  ): ReadableStore[ViewerId, Seq[Candidate]] = {
    new RealGraphInNetworkScoresStore(realGraphInNetworkScoresManhattanKVEndpoint)
  }
}

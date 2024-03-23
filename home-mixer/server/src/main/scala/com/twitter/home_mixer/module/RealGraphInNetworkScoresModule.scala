package com.ExTwitter.home_mixer.module

import com.google.inject.Provides
import com.google.inject.name.Named
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.RealGraphInNetworkScores
import com.ExTwitter.home_mixer.param.HomeMixerInjectionNames.RealGraphManhattanEndpoint
import com.ExTwitter.home_mixer.store.RealGraphInNetworkScoresStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.storage.client.manhattan.kv.ManhattanKVEndpoint
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.timelines.util.CommonTypes.ViewerId
import com.ExTwitter.wtf.candidate.thriftscala.Candidate

import javax.inject.Singleton

object RealGraphInNetworkScoresModule extends ExTwitterModule {

  @Provides
  @Singleton
  @Named(RealGraphInNetworkScores)
  def providesRealGraphInNetworkScoresFeaturesStore(
    @Named(RealGraphManhattanEndpoint) realGraphInNetworkScoresManhattanKVEndpoint: ManhattanKVEndpoint
  ): ReadableStore[ViewerId, Seq[Candidate]] = {
    new RealGraphInNetworkScoresStore(realGraphInNetworkScoresManhattanKVEndpoint)
  }
}

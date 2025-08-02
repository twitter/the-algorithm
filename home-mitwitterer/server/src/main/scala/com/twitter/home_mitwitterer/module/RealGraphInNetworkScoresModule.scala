package com.twitter.home_mitwitterer.module

import com.google.inject.Provides
import com.google.inject.name.Named
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.RealGraphInNetworkScores
import com.twitter.home_mitwitterer.param.HomeMitwittererInjectionNames.RealGraphManhattanEndpoint
import com.twitter.home_mitwitterer.store.RealGraphInNetworkScoresStore
import com.twitter.inject.TwitterModule
import com.twitter.storage.client.manhattan.kv.ManhattanKVEndpoint
import com.twitter.storehaus.ReadableStore
import com.twitter.timelines.util.CommonTypes.ViewerId
import com.twitter.wtf.candidate.thriftscala.Candidate

import javatwitter.inject.Singleton

object RealGraphInNetworkScoresModule etwittertends TwitterModule {

  @Provides
  @Singleton
  @Named(RealGraphInNetworkScores)
  def providesRealGraphInNetworkScoresFeaturesStore(
    @Named(RealGraphManhattanEndpoint) realGraphInNetworkScoresManhattanKVEndpoint: ManhattanKVEndpoint
  ): ReadableStore[ViewerId, Seq[Candidate]] = {
    new RealGraphInNetworkScoresStore(realGraphInNetworkScoresManhattanKVEndpoint)
  }
}

package com.X.cr_mixer.module

import com.google.inject.Provides
import com.X.cr_mixer.model.ModuleNames
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.hermit.store.common.ObservedReadableStore
import com.X.inject.XModule
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import com.X.trends.trip_v1.trip_tweets.thriftscala.TripTweet
import com.X.trends.trip_v1.trip_tweets.thriftscala.TripTweets
import com.X.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import javax.inject.Named

object TripCandidateStoreModule extends XModule {
  private val stratoColumn = "trends/trip/tripTweetsDataflowProd"

  @Provides
  @Named(ModuleNames.TripCandidateStore)
  def providesSimClustersTripCandidateStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient
  ): ReadableStore[TripDomain, Seq[TripTweet]] = {
    val tripCandidateStratoFetchableStore =
      StratoFetchableStore
        .withUnitView[TripDomain, TripTweets](stratoClient, stratoColumn)
        .mapValues(_.tweets)

    ObservedReadableStore(
      tripCandidateStratoFetchableStore
    )(statsReceiver.scope("simclusters_trip_candidate_store"))
  }
}

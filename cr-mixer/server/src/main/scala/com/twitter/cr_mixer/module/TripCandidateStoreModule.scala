package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.twitter.cr_mixer.model.ModuleNames
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.inject.TwitterModule
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripTweet
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripTweets
import com.twitter.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import javax.inject.Named

object TripCandidateStoreModule extends TwitterModule {
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

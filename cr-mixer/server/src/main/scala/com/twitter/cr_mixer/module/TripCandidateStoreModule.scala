package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.frigate.common.store.strato.StratoFetchableStore
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.strato.client.{Client => StratoClient}
import com.ExTwitter.trends.trip_v1.trip_tweets.thriftscala.TripTweet
import com.ExTwitter.trends.trip_v1.trip_tweets.thriftscala.TripTweets
import com.ExTwitter.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import javax.inject.Named

object TripCandidateStoreModule extends ExTwitterModule {
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

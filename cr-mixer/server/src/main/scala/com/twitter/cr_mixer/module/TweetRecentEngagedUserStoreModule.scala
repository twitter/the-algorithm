package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.twitter.app.Flag
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.store.strato.StratoFetchableStore
import com.twitter.hermit.store.common.ObservedReadableStore
import com.twitter.inject.TwitterModule
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.storehaus.ReadableStore
import com.twitter.strato.client.{Client => StratoClient}
import com.twitter.twistly.thriftscala.TweetRecentEngagedUsers

object TweetRecentEngagedUserStoreModule extends TwitterModule {

  private val tweetRecentEngagedUsersStoreDefaultVersion =
    0 // DefaultVersion for tweetEngagedUsersStore, whose key = (tweetId, DefaultVersion)
  private val tweetRecentEngagedUsersColumnPath: Flag[String] = flag[String](
    name = "crMixer.tweetRecentEngagedUsersColumnPath",
    default = "recommendations/twistly/tweetRecentEngagedUsers",
    help = "Strato column path for TweetRecentEngagedUsersStore"
  )
  private type Version = Long

  @Provides
  @Singleton
  def providesTweetRecentEngagedUserStore(
    statsReceiver: StatsReceiver,
    stratoClient: StratoClient,
  ): ReadableStore[TweetId, TweetRecentEngagedUsers] = {
    val tweetRecentEngagedUsersStratoFetchableStore = StratoFetchableStore
      .withUnitView[(TweetId, Version), TweetRecentEngagedUsers](
        stratoClient,
        tweetRecentEngagedUsersColumnPath()).composeKeyMapping[TweetId](tweetId =>
        (tweetId, tweetRecentEngagedUsersStoreDefaultVersion))

    ObservedReadableStore(
      tweetRecentEngagedUsersStratoFetchableStore
    )(statsReceiver.scope("tweet_recent_engaged_users_store"))
  }
}

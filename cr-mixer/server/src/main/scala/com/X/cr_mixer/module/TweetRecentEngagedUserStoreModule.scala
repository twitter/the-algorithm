package com.X.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.X.app.Flag
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.store.strato.StratoFetchableStore
import com.X.hermit.store.common.ObservedReadableStore
import com.X.inject.XModule
import com.X.simclusters_v2.common.TweetId
import com.X.storehaus.ReadableStore
import com.X.strato.client.{Client => StratoClient}
import com.X.twistly.thriftscala.TweetRecentEngagedUsers

object TweetRecentEngagedUserStoreModule extends XModule {

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

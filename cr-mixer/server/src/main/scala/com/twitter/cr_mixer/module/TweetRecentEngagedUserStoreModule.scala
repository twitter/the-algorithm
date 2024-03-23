package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.google.inject.Singleton
import com.ExTwitter.app.Flag
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.frigate.common.store.strato.StratoFetchableStore
import com.ExTwitter.hermit.store.common.ObservedReadableStore
import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.simclusters_v2.common.TweetId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.strato.client.{Client => StratoClient}
import com.ExTwitter.twistly.thriftscala.TweetRecentEngagedUsers

object TweetRecentEngagedUserStoreModule extends ExTwitterModule {

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

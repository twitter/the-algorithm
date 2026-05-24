package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.home_mixer.param.HomeMixerInjectionNames.TweetWatchTimeMetadataStore
import com.twitter.home_mixer.store.TweetWatchTimeMetadataStore
import com.twitter.inject.TwitterModule
import com.twitter.storehaus.ReadableStore
import com.twitter.twistly.thriftscala.VideoViewEngagementType
import com.twitter.twistly.thriftscala.WatchTimeMetadata
import javax.inject.Named
import javax.inject.Singleton

object TweetWatchTimeMetadataModule extends TwitterModule {

  @Provides
  @Singleton
  @Named(TweetWatchTimeMetadataStore)
  def providesTweetWatchTimeMetadataStore(
    tweetWatchTimeMetadataStore: TweetWatchTimeMetadataStore
  ): ReadableStore[(Long, VideoViewEngagementType), WatchTimeMetadata] = {
    tweetWatchTimeMetadataStore.tweetWatchTimeMetadataStore
  }
}

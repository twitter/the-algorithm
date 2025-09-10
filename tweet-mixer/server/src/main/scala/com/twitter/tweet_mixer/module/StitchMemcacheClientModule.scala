package com.twitter.tweet_mixer.module

import com.google.inject.Provides
import com.twitter.finagle.memcached.{Client => MemcachedClient}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.inject.TwitterModule
import com.twitter.tweet_mixer.utils.MemcacheStitchClient
import javax.inject.Singleton

object StitchMemcacheClientModule extends TwitterModule {

  @Provides
  @Singleton
  def providesMemcacheStitchClient(
    memcacheClient: MemcachedClient,
    statsReceiver: StatsReceiver
  ): MemcacheStitchClient = {
    new MemcacheStitchClient(memcacheClient, statsReceiver)
  }
}

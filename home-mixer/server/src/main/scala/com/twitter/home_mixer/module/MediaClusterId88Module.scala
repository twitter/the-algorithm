package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MediaClusterId88Store
import com.twitter.home_mixer.store.MediaClusterId88Store
import com.twitter.inject.TwitterModule
import com.twitter.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object MediaClusterId88Module extends TwitterModule {

  @Provides
  @Singleton
  @Named(MediaClusterId88Store)
  def providesMediaClusterId88Store(
    mediaClusterId88Store: MediaClusterId88Store
  ): ReadableStore[Long, Long] = {
    mediaClusterId88Store.clusterIdStore
  }
}

package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.home_mixer.param.HomeMixerInjectionNames.MediaClusterId95Store
import com.twitter.home_mixer.store.MediaClusterId95Store
import com.twitter.inject.TwitterModule
import com.twitter.storehaus.ReadableStore
import javax.inject.Named
import javax.inject.Singleton

object MediaClusterId95Module extends TwitterModule {

  @Provides
  @Singleton
  @Named(MediaClusterId95Store)
  def providesMediaClusterId95Store(
    mediaClusterId95Store: MediaClusterId95Store
  ): ReadableStore[Long, Long] = {
    mediaClusterId95Store.clusterIdStore
  }
}

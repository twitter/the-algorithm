package com.twitter.representation_manager.modules

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import javax.inject.Named
import javax.inject.Singleton

object LegacyRMSConfigModule extends TwitterModule {
  @Singleton
  @Provides
  @Named("cacheHashKeyPrefix")
  def providesCacheHashKeyPrefix: String = "RMS"

  @Singleton
  @Provides
  @Named("useContentRecommenderConfiguration")
  def providesUseContentRecommenderConfiguration: Boolean = false
}

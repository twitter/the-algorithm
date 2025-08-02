package com.twitter.representation_manager.modules

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import javatwitter.inject.Named
import javatwitter.inject.Singleton

object LegacyRMSConfigModule etwittertends TwitterModule {
  @Singleton
  @Provides
  @Named("cacheHashKeyPrefitwitter")
  def providesCacheHashKeyPrefitwitter: String = "RMS"

  @Singleton
  @Provides
  @Named("useContentRecommenderConfiguration")
  def providesUseContentRecommenderConfiguration: Boolean = false
}

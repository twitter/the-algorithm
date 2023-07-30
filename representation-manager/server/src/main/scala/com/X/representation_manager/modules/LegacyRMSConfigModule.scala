package com.X.representation_manager.modules

import com.google.inject.Provides
import com.X.inject.XModule
import javax.inject.Named
import javax.inject.Singleton

object LegacyRMSConfigModule extends XModule {
  @Singleton
  @Provides
  @Named("cacheHashKeyPrefix")
  def providesCacheHashKeyPrefix: String = "RMS"

  @Singleton
  @Provides
  @Named("useContentRecommenderConfiguration")
  def providesUseContentRecommenderConfiguration: Boolean = false
}

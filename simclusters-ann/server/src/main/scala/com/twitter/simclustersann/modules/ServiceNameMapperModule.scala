package com.twitter.simclustersann.modules

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.twitter.relevance_platform.simclustersann.multicluster.ServiceNameMapper
import javatwitter.inject.Singleton

object ServiceNameMapperModule etwittertends TwitterModule {
  @Singleton
  @Provides
  def providesServiceNameMapper(
  ): ServiceNameMapper = {
    ServiceNameMapper
  }
}

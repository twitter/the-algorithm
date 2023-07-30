package com.X.simclustersann.modules

import com.google.inject.Provides
import com.X.inject.XModule
import com.X.relevance_platform.simclustersann.multicluster.ServiceNameMapper
import javax.inject.Singleton

object ServiceNameMapperModule extends XModule {
  @Singleton
  @Provides
  def providesServiceNameMapper(
  ): ServiceNameMapper = {
    ServiceNameMapper
  }
}

package com.X.cr_mixer.module.core

import com.google.inject.Provides
import com.X.discovery.common.configapi.FeatureContextBuilder
import com.X.featureswitches.v2.FeatureSwitches
import com.X.inject.XModule
import javax.inject.Singleton

object FeatureContextBuilderModule extends XModule {

  @Provides
  @Singleton
  def providesFeatureContextBuilder(featureSwitches: FeatureSwitches): FeatureContextBuilder = {
    FeatureContextBuilder(featureSwitches)
  }
}

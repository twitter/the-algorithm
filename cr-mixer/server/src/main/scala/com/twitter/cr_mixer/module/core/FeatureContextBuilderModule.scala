package com.twitter.cr_mixer.module.core

import com.google.inject.Provides
import com.twitter.discovery.common.configapi.FeatureContextBuilder
import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.inject.TwitterModule
import javax.inject.Singleton

object FeatureContextBuilderModule extends TwitterModule {

  @Provides
  @Singleton
  def providesFeatureContextBuilder(featureSwitches: FeatureSwitches): FeatureContextBuilder = {
    FeatureContextBuilder(featureSwitches)
  }
}

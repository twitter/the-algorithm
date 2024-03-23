package com.ExTwitter.cr_mixer.module.core

import com.google.inject.Provides
import com.ExTwitter.discovery.common.configapi.FeatureContextBuilder
import com.ExTwitter.featureswitches.v2.FeatureSwitches
import com.ExTwitter.inject.ExTwitterModule
import javax.inject.Singleton

object FeatureContextBuilderModule extends ExTwitterModule {

  @Provides
  @Singleton
  def providesFeatureContextBuilder(featureSwitches: FeatureSwitches): FeatureContextBuilder = {
    FeatureContextBuilder(featureSwitches)
  }
}

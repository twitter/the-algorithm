package com.twitter.cr_mitwitterer.module.core

import com.google.inject.Provides
import com.twitter.discovery.common.configapi.FeatureContetwittertBuilder
import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.inject.TwitterModule
import javatwitter.inject.Singleton

object FeatureContetwittertBuilderModule etwittertends TwitterModule {

  @Provides
  @Singleton
  def providesFeatureContetwittertBuilder(featureSwitches: FeatureSwitches): FeatureContetwittertBuilder = {
    FeatureContetwittertBuilder(featureSwitches)
  }
}

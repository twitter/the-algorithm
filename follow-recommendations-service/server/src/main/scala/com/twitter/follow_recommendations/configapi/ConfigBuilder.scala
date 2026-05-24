package com.twitter.follow_recommendations.configapi

import com.twitter.timelines.configapi.CompositeConfig
import com.twitter.timelines.configapi.Config
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConfigBuilder @Inject() (
  deciderConfigs: DeciderConfigs,
  featureSwitchConfigs: FeatureSwitchConfigs) {
  // The order of configs added to `CompositeConfig` is important. The config will be matched with
  // the first possible rule. So, current setup will give priority to Deciders instead of FS
  def build(): Config =
    new CompositeConfig(Seq(deciderConfigs.config, featureSwitchConfigs.config))
}

package com.twitter.home_mixer.module

import com.google.inject.Provides
import com.twitter.config.yaml.YamlMap
import com.twitter.home_mixer.param.HomeMixerInjectionNames.DDGStatsAuthors
import com.twitter.inject.TwitterModule
import javax.inject.Named
import javax.inject.Singleton

object HomeMixerResourcesModule extends TwitterModule {

  private val AuthorsFile = "/config/authors.yml"

  @Provides
  @Singleton
  @Named(DDGStatsAuthors)
  def providesDDGStatsAuthors(): YamlMap = YamlMap.load(AuthorsFile)
}

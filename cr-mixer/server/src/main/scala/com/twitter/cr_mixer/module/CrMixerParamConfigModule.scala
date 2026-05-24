package com.twitter.cr_mixer.module

import com.google.inject.Provides
import com.twitter.timelines.configapi.Config
import com.twitter.cr_mixer.param.CrMixerParamConfig
import com.twitter.inject.TwitterModule
import javax.inject.Singleton

object CrMixerParamConfigModule extends TwitterModule {

  @Provides
  @Singleton
  def provideConfig(): Config = {
    CrMixerParamConfig.config
  }
}

package com.ExTwitter.cr_mixer.module

import com.google.inject.Provides
import com.ExTwitter.timelines.configapi.Config
import com.ExTwitter.cr_mixer.param.CrMixerParamConfig
import com.ExTwitter.inject.ExTwitterModule
import javax.inject.Singleton

object CrMixerParamConfigModule extends ExTwitterModule {

  @Provides
  @Singleton
  def provideConfig(): Config = {
    CrMixerParamConfig.config
  }
}

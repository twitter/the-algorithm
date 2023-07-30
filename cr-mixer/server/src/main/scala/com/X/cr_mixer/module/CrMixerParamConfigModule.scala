package com.X.cr_mixer.module

import com.google.inject.Provides
import com.X.timelines.configapi.Config
import com.X.cr_mixer.param.CrMixerParamConfig
import com.X.inject.XModule
import javax.inject.Singleton

object CrMixerParamConfigModule extends XModule {

  @Provides
  @Singleton
  def provideConfig(): Config = {
    CrMixerParamConfig.config
  }
}

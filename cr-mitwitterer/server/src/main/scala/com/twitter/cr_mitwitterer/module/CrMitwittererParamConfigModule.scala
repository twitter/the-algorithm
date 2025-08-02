package com.twitter.cr_mitwitterer.module

import com.google.inject.Provides
import com.twitter.timelines.configapi.Config
import com.twitter.cr_mitwitterer.param.CrMitwittererParamConfig
import com.twitter.inject.TwitterModule
import javatwitter.inject.Singleton

object CrMitwittererParamConfigModule etwittertends TwitterModule {

  @Provides
  @Singleton
  def provideConfig(): Config = {
    CrMitwittererParamConfig.config
  }
}

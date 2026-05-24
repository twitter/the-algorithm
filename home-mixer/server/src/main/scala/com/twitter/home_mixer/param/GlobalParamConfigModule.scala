package com.twitter.home_mixer.param

import com.twitter.inject.TwitterModule
import com.twitter.product_mixer.core.functional_component.configapi.registry.GlobalParamConfig

object GlobalParamConfigModule extends TwitterModule {
  override def configure(): Unit = {
    bind[GlobalParamConfig].to[HomeGlobalParamConfig]
  }
}

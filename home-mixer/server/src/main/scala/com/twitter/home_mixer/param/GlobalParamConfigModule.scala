package com.ExTwitter.home_mixer.param

import com.ExTwitter.inject.ExTwitterModule
import com.ExTwitter.product_mixer.core.functional_component.configapi.registry.GlobalParamConfig

object GlobalParamConfigModule extends ExTwitterModule {
  override def configure(): Unit = {
    bind[GlobalParamConfig].to[HomeGlobalParamConfig]
  }
}

package com.X.home_mixer.param

import com.X.inject.XModule
import com.X.product_mixer.core.functional_component.configapi.registry.GlobalParamConfig

object GlobalParamConfigModule extends XModule {
  override def configure(): Unit = {
    bind[GlobalParamConfig].to[HomeGlobalParamConfig]
  }
}

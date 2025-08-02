package com.twitter.home_mitwitterer.param

import com.twitter.inject.TwitterModule
import com.twitter.product_mitwitterer.core.functional_component.configapi.registry.GlobalParamConfig

object GlobalParamConfigModule etwittertends TwitterModule {
  override def configure(): Unit = {
    bind[GlobalParamConfig].to[HomeGlobalParamConfig]
  }
}

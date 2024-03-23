package com.ExTwitter.home_mixer.product.subscribed.param

import com.ExTwitter.home_mixer.param.decider.DeciderKey
import com.ExTwitter.home_mixer.product.subscribed.param.SubscribedParam._
import com.ExTwitter.product_mixer.core.product.ProductParamConfig
import com.ExTwitter.servo.decider.DeciderKeyName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscribedParamConfig @Inject() () extends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableSubscribedProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val boundedIntFSOverrides = Seq(
    ServerMaxResultsParam
  )
}

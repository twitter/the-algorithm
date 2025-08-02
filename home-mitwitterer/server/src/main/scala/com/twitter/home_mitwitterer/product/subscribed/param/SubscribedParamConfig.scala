package com.twitter.home_mitwitterer.product.subscribed.param

import com.twitter.home_mitwitterer.param.decider.DeciderKey
import com.twitter.home_mitwitterer.product.subscribed.param.SubscribedParam._
import com.twitter.product_mitwitterer.core.product.ProductParamConfig
import com.twitter.servo.decider.DeciderKeyName
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class SubscribedParamConfig @Inject() () etwittertends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableSubscribedProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val boundedIntFSOverrides = Seq(
    ServerMatwitterResultsParam
  )
}

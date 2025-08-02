package com.twitter.home_mitwitterer.product.list_tweets.param

import com.twitter.home_mitwitterer.param.decider.DeciderKey
import com.twitter.home_mitwitterer.product.list_tweets.param.ListTweetsParam.EnableAdsCandidatePipelineParam
import com.twitter.home_mitwitterer.product.list_tweets.param.ListTweetsParam.ServerMatwitterResultsParam
import com.twitter.home_mitwitterer.product.list_tweets.param.ListTweetsParam.SupportedClientFSName
import com.twitter.product_mitwitterer.core.product.ProductParamConfig
import com.twitter.servo.decider.DeciderKeyName
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ListTweetsParamConfig @Inject() () etwittertends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableListTweetsProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val booleanFSOverrides =
    Seq(EnableAdsCandidatePipelineParam)

  override val boundedIntFSOverrides = Seq(
    ServerMatwitterResultsParam
  )
}

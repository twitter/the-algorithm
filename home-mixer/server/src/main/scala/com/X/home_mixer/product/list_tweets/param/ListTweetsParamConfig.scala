package com.X.home_mixer.product.list_tweets.param

import com.X.home_mixer.param.decider.DeciderKey
import com.X.home_mixer.product.list_tweets.param.ListTweetsParam.EnableAdsCandidatePipelineParam
import com.X.home_mixer.product.list_tweets.param.ListTweetsParam.ServerMaxResultsParam
import com.X.home_mixer.product.list_tweets.param.ListTweetsParam.SupportedClientFSName
import com.X.product_mixer.core.product.ProductParamConfig
import com.X.servo.decider.DeciderKeyName
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListTweetsParamConfig @Inject() () extends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableListTweetsProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val booleanFSOverrides =
    Seq(EnableAdsCandidatePipelineParam)

  override val boundedIntFSOverrides = Seq(
    ServerMaxResultsParam
  )
}

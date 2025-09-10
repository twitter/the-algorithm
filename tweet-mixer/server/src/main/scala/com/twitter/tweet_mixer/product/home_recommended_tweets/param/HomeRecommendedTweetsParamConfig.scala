package com.twitter.tweet_mixer.product.home_recommended_tweets.param

import com.twitter.product_mixer.core.product.ProductParamConfig
import com.twitter.servo.decider.DeciderKeyName
import com.twitter.tweet_mixer.param.decider.DeciderKey.EnableHomeRecommendedTweetsProduct
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRecommendedTweetsParamConfig @Inject() () extends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = EnableHomeRecommendedTweetsProduct

  override val supportedClientFSName: String = HomeRecommendedTweetsParam.SupportedClientFSName

  override val boundedIntFSOverrides = HomeRecommendedTweetsParam.boundedIntFSOverrides
}

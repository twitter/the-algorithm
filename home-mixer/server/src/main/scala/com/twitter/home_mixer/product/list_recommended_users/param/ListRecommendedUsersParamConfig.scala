package com.twitter.home_mixer.product.list_recommended_users.param

import com.twitter.home_mixer.param.decider.DeciderKey
import com.twitter.home_mixer.product.list_recommended_users.param.ListRecommendedUsersParam.ExcludedIdsMaxLengthParam
import com.twitter.home_mixer.product.list_recommended_users.param.ListRecommendedUsersParam.ServerMaxResultsParam
import com.twitter.home_mixer.product.list_recommended_users.param.ListRecommendedUsersParam.SupportedClientFSName
import com.twitter.product_mixer.core.product.ProductParamConfig
import com.twitter.servo.decider.DeciderKeyName

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRecommendedUsersParamConfig @Inject() () extends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableListRecommendedUsersProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val boundedIntFSOverrides = Seq(
    ServerMaxResultsParam,
    ExcludedIdsMaxLengthParam
  )
}

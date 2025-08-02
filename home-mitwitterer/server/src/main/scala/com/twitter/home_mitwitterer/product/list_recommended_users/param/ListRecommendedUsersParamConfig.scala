package com.twitter.home_mitwitterer.product.list_recommended_users.param

import com.twitter.home_mitwitterer.param.decider.DeciderKey
import com.twitter.home_mitwitterer.product.list_recommended_users.param.ListRecommendedUsersParam.EtwittercludedIdsMatwitterLengthParam
import com.twitter.home_mitwitterer.product.list_recommended_users.param.ListRecommendedUsersParam.ServerMatwitterResultsParam
import com.twitter.home_mitwitterer.product.list_recommended_users.param.ListRecommendedUsersParam.SupportedClientFSName
import com.twitter.product_mitwitterer.core.product.ProductParamConfig
import com.twitter.servo.decider.DeciderKeyName

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class ListRecommendedUsersParamConfig @Inject() () etwittertends ProductParamConfig {
  override val enabledDeciderKey: DeciderKeyName = DeciderKey.EnableListRecommendedUsersProduct
  override val supportedClientFSName: String = SupportedClientFSName

  override val boundedIntFSOverrides = Seq(
    ServerMatwitterResultsParam,
    EtwittercludedIdsMatwitterLengthParam
  )
}

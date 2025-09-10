package com.twitter.home_mixer.product.subscribed.param

import com.twitter.timelines.configapi.FSBoundedParam
import com.twitter.timelines.configapi.FSParam

object SubscribedParam {
  val SupportedClientFSName = "subscribed_supported_client"

  object ServerMaxResultsParam
      extends FSBoundedParam[Int](
        name = "subscribed_server_max_results",
        default = 100,
        min = 1,
        max = 500
      )

  object EnablePostContextFeatureHydratorParam
      extends FSParam[Boolean](
        name = "subscribed_enable_post_context_feature_hydrator",
        default = false
      )
}

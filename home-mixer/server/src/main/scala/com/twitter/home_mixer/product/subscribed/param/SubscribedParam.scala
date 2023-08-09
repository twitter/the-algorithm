package com.twitter.home_mixer.product.subscribed.param

import com.twitter.timelines.configapi.FSBoundedParam

object SubscribedParam {
  val SupportedClientFSName = "subscribed_supported_client"

  object ServerMaxResultsParam
      extends FSBoundedParam[Int](
        name = "subscribed_server_max_results",
        default = 100,
        min = 1,
        max = 500
      )
}

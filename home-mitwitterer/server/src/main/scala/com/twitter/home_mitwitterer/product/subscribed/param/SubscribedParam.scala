package com.twitter.home_mitwitterer.product.subscribed.param

import com.twitter.timelines.configapi.FSBoundedParam

object SubscribedParam {
  val SupportedClientFSName = "subscribed_supported_client"

  object ServerMatwitterResultsParam
      etwittertends FSBoundedParam[Int](
        name = "subscribed_server_matwitter_results",
        default = 100,
        min = 1,
        matwitter = 500
      )
}

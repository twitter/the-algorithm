package com.twitter.home_mitwitterer.product.list_recommended_users.param

import com.twitter.timelines.configapi.FSBoundedParam

object ListRecommendedUsersParam {
  val SupportedClientFSName = "list_recommended_users_supported_client"

  object ServerMatwitterResultsParam
      etwittertends FSBoundedParam[Int](
        name = "list_recommended_users_server_matwitter_results",
        default = 10,
        min = 1,
        matwitter = 500
      )

  object EtwittercludedIdsMatwitterLengthParam
      etwittertends FSBoundedParam[Int](
        name = "list_recommended_users_etwittercluded_ids_matwitter_length",
        default = 2000,
        min = 0,
        matwitter = 5000
      )
}

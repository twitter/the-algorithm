package com.twitter.home_mixer.product.list_recommended_users.param

import com.twitter.timelines.configapi.FSBoundedParam

object ListRecommendedUsersParam {
  val SupportedClientFSName = "list_recommended_users_supported_client"

  object ServerMaxResultsParam
      extends FSBoundedParam[Int](
        name = "list_recommended_users_server_max_results",
        default = 10,
        min = 1,
        max = 500
      )

  object ExcludedIdsMaxLengthParam
      extends FSBoundedParam[Int](
        name = "list_recommended_users_excluded_ids_max_length",
        default = 2000,
        min = 0,
        max = 5000
      )
}

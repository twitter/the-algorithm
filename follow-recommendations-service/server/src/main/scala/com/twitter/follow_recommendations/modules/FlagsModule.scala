package com.ExTwitter.follow_recommendations.modules
import com.ExTwitter.inject.ExTwitterModule

object FlagsModule extends ExTwitterModule {
  flag[Boolean](
    name = "fetch_prod_promoted_accounts",
    help = "Whether or not to fetch production promoted accounts (true / false)"
  )
  flag[Boolean](
    name = "interests_opt_out_prod_enabled",
    help = "Whether to fetch intersts opt out data from the prod strato column or not"
  )
  flag[Boolean](
    name = "log_results",
    default = false,
    help = "Whether to log results such that we use them for scoring or metrics"
  )
}

package com.tw ter.follow_recom ndat ons.modules
 mport com.tw ter. nject.Tw terModule

object FlagsModule extends Tw terModule {
  flag[Boolean](
    na  = "fetch_prod_promoted_accounts",
     lp = "W t r or not to fetch product on promoted accounts (true / false)"
  )
  flag[Boolean](
    na  = " nterests_opt_out_prod_enabled",
     lp = "W t r to fetch  ntersts opt out data from t  prod strato column or not"
  )
  flag[Boolean](
    na  = "log_results",
    default = false,
     lp = "W t r to log results such that   use t m for scor ng or  tr cs"
  )
}

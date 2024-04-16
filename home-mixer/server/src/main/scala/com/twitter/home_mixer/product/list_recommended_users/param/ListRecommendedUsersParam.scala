package com.tw ter.ho _m xer.product.l st_recom nded_users.param

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam

object L stRecom ndedUsersParam {
  val SupportedCl entFSNa  = "l st_recom nded_users_supported_cl ent"

  object ServerMaxResultsParam
      extends FSBoundedParam[ nt](
        na  = "l st_recom nded_users_server_max_results",
        default = 10,
        m n = 1,
        max = 500
      )

  object Excluded dsMaxLengthParam
      extends FSBoundedParam[ nt](
        na  = "l st_recom nded_users_excluded_ ds_max_length",
        default = 2000,
        m n = 0,
        max = 5000
      )
}

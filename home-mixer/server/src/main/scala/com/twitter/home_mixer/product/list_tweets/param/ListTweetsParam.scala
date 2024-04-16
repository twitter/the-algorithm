package com.tw ter.ho _m xer.product.l st_t ets.param

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam

object L stT etsParam {
  val SupportedCl entFSNa  = "l st_t ets_supported_cl ent"

  object EnableAdsCand dateP pel neParam
      extends FSParam[Boolean](
        na  = "l st_t ets_enable_ads",
        default = false
      )

  object ServerMaxResultsParam
      extends FSBoundedParam[ nt](
        na  = "l st_t ets_server_max_results",
        default = 100,
        m n = 1,
        max = 500
      )
}

package com.tw ter.ho _m xer.product.subscr bed.param

 mport com.tw ter.t  l nes.conf gap .FSBoundedParam

object Subscr bedParam {
  val SupportedCl entFSNa  = "subscr bed_supported_cl ent"

  object ServerMaxResultsParam
      extends FSBoundedParam[ nt](
        na  = "subscr bed_server_max_results",
        default = 100,
        m n = 1,
        max = 500
      )
}

package com.tw ter.t  l neranker.para ters.mon or ng

 mport com.tw ter.t  l nes.conf gap .FSParam

object Mon or ngParams {

  object DebugAuthorsAllowL stParam
      extends FSParam[Seq[Long]](
        na  = "mon or ng_debug_authors_allow_l st",
        default = Seq.empty[Long]
      )

}

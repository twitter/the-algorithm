package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap ._

object RealGraph nParams {
  object EnableS ceGraphParam
      extends FSParam[Boolean](
        na  = "graph_realgraph n_enable_s ce",
        default = false
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableS ceGraphParam,
  )

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableS ceGraphParam
    )

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .bu ld()
  }
}

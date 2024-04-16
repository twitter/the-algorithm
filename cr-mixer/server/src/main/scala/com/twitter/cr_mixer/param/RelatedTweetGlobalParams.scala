package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object RelatedT etGlobalParams {

  object MaxCand datesPerRequestParam
      extends FSBoundedParam[ nt](
        na  = "related_t et_core_max_cand dates_per_request",
        default = 100,
        m n = 0,
        max = 500
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(MaxCand datesPerRequestParam)

  lazy val conf g: BaseConf g = {

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      MaxCand datesPerRequestParam
    )

    BaseConf gBu lder()
      .set( ntOverr des: _*)
      .bu ld()
  }
}

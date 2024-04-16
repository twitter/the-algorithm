package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object Consu rEmbedd ngBasedTr pParams {
  object S ce dParam
      extends FSParam[Str ng](
        na  = "consu r_embedd ng_based_tr p_s ce_ d",
        default = "EXPLR_TOPK_V D_48H_V3")

  object MaxNumCand datesParam
      extends FSBoundedParam[ nt](
        na  = "consu r_embedd ng_based_tr p_max_num_cand dates",
        default = 80,
        m n = 0,
        max = 200
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    S ce dParam,
    MaxNumCand datesParam
  )

  lazy val conf g: BaseConf g = {
    val str ngFSOverr des =
      FeatureSw chOverr deUt l.getStr ngFSOverr des(
        S ce dParam
      )

    val  ntFSOverr des =
      FeatureSw chOverr deUt l.getBounded ntFSOverr des(
        MaxNumCand datesParam
      )

    BaseConf gBu lder()
      .set(str ngFSOverr des: _*)
      .set( ntFSOverr des: _*)
      .bu ld()
  }
}

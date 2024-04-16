package com.tw ter.cr_m xer.param

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

object UtegT etGlobalParams {

  object MaxUtegCand datesToRequestParam
      extends FSBoundedParam[ nt](
        na  = "max_uteg_cand dates_to_request",
        default = 800,
        m n = 10,
        max = 200
      )

  object Cand dateRefreshS nceT  OffsetH sParam
      extends FSBoundedParam[Durat on](
        na  = "cand date_refresh_s nce_t  _offset_h s",
        default = 48.h s,
        m n = 1.h s,
        max = 96.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromH s
  }

  object EnableTLR althF lterParam
      extends FSParam[Boolean](
        na  = "enable_uteg_tlr_ alth_f lter",
        default = true
      )

  object EnableRepl esToNonFollo dUsersF lterParam
      extends FSParam[Boolean](
        na  = "enable_uteg_repl es_to_non_follo d_users_f lter",
        default = false
      )

  object EnableRet etF lterParam
      extends FSParam[Boolean](
        na  = "enable_uteg_ret et_f lter",
        default = true
      )

  object Enable nNetworkF lterParam
      extends FSParam[Boolean](
        na  = "enable_uteg_ n_network_f lter",
        default = true
      )

  val AllParams: Seq[Param[_] w h FSNa ] =
    Seq(
      MaxUtegCand datesToRequestParam,
      Cand dateRefreshS nceT  OffsetH sParam,
      EnableTLR althF lterParam,
      EnableRepl esToNonFollo dUsersF lterParam,
      EnableRet etF lterParam,
      Enable nNetworkF lterParam
    )

  lazy val conf g: BaseConf g = {

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      MaxUtegCand datesToRequestParam
    )

    val durat onFSOverr des =
      FeatureSw chOverr deUt l.getDurat onFSOverr des(
        Cand dateRefreshS nceT  OffsetH sParam
      )

    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableTLR althF lterParam,
      EnableRepl esToNonFollo dUsersF lterParam,
      EnableRet etF lterParam,
      Enable nNetworkF lterParam
    )

    BaseConf gBu lder()
      .set( ntOverr des: _*)
      .set(durat onFSOverr des: _*)
      .set(booleanOverr des: _*)
      .bu ld()
  }
}

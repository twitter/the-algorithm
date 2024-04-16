package com.tw ter.cr_m xer.param

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.logg ng.Logger
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

object Top cT etParams {
  object MaxT etAge
      extends FSBoundedParam[Durat on](
        na  = "top c_t et_cand date_generat on_max_t et_age_h s",
        default = 24.h s,
        m n = 12.h s,
        max = 48.h s
      )
      w h HasDurat onConvers on {
    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromH s
  }

  object MaxTop cT etCand datesParam
      extends FSBoundedParam[ nt](
        na  = "top c_t et_max_cand dates_num",
        default = 200,
        m n = 0,
        max = 1000
      )

  object MaxSk TfgCand datesParam
      extends FSBoundedParam[ nt](
        na  = "top c_t et_sk _tfg_max_cand dates_num",
        default = 100,
        m n = 0,
        max = 1000
      )

  object MaxSk H ghPrec s onCand datesParam
      extends FSBoundedParam[ nt](
        na  = "top c_t et_sk _h gh_prec s on_max_cand dates_num",
        default = 100,
        m n = 0,
        max = 1000
      )

  object MaxCertoCand datesParam
      extends FSBoundedParam[ nt](
        na  = "top c_t et_certo_max_cand dates_num",
        default = 100,
        m n = 0,
        max = 1000
      )

  // T  m n prod score for Certo L2-normal zed cos ne cand dates
  object CertoScoreThresholdParam
      extends FSBoundedParam[Double](
        na  = "top c_t et_certo_score_threshold",
        default = 0.015,
        m n = 0,
        max = 1
      )

  object Semant cCoreVers on dParam
      extends FSParam[Long](
        na  = "semant c_core_vers on_ d",
        default = 1380520918896713735L
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    CertoScoreThresholdParam,
    MaxTop cT etCand datesParam,
    MaxT etAge,
    MaxCertoCand datesParam,
    MaxSk TfgCand datesParam,
    MaxSk H ghPrec s onCand datesParam,
    Semant cCoreVers on dParam
  )

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des()

    val doubleOverr des =
      FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(CertoScoreThresholdParam)

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      MaxCertoCand datesParam,
      MaxSk TfgCand datesParam,
      MaxSk H ghPrec s onCand datesParam,
      MaxTop cT etCand datesParam
    )

    val longOverr des = FeatureSw chOverr deUt l.getLongFSOverr des(Semant cCoreVers on dParam)

    val durat onFSOverr des = FeatureSw chOverr deUt l.getDurat onFSOverr des(MaxT etAge)

    val enumOverr des =
      FeatureSw chOverr deUt l.getEnumFSOverr des(NullStatsRece ver, Logger(getClass))

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(doubleOverr des: _*)
      .set( ntOverr des: _*)
      .set(longOverr des: _*)
      .set(enumOverr des: _*)
      .set(durat onFSOverr des: _*)
      .bu ld()
  }
}

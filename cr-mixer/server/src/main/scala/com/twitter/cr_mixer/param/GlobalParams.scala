package com.tw ter.cr_m xer.param

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.s mclusters_v2.common.ModelVers ons
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .Durat onConvers on
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .HasDurat onConvers on
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Durat on

/**
 *  nstant ate Params that do not relate to a spec f c product.
 * T  params  n t  f le correspond to conf g repo f le
 * [[https://s cegraph.tw ter.b z/conf g-g .tw ter.b z/conf g/-/blob/features/cr-m xer/ma n/tw stly_core.yml]]
 */
object GlobalParams {

  object MaxCand datesPerRequestParam
      extends FSBoundedParam[ nt](
        na  = "tw stly_core_max_cand dates_per_request",
        default = 100,
        m n = 0,
        max = 9000
      )

  object ModelVers onParam
      extends FSEnumParam[ModelVers ons.Enum.type](
        na  = "tw stly_core_s mclusters_model_vers on_ d",
        default = ModelVers ons.Enum.Model20M145K2020,
        enum = ModelVers ons.Enum
      )

  object Un f edMaxS ceKeyNum
      extends FSBoundedParam[ nt](
        na  = "tw stly_core_un f ed_max_s cekey_num",
        default = 15,
        m n = 0,
        max = 100
      )

  object MaxCand dateNumPerS ceKeyParam
      extends FSBoundedParam[ nt](
        na  = "tw stly_core_cand date_per_s cekey_max_num",
        default = 200,
        m n = 0,
        max = 1000
      )

  // 1 h s to 30 days
  object MaxT etAgeH sParam
      extends FSBoundedParam[Durat on](
        na  = "tw stly_core_max_t et_age_h s",
        default = 720.h s,
        m n = 1.h s,
        max = 720.h s
      )
      w h HasDurat onConvers on {

    overr de val durat onConvers on: Durat onConvers on = Durat onConvers on.FromH s
  }

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    MaxCand datesPerRequestParam,
    Un f edMaxS ceKeyNum,
    MaxCand dateNumPerS ceKeyParam,
    ModelVers onParam,
    MaxT etAgeH sParam
  )

  lazy val conf g: BaseConf g = {

    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des()

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      MaxCand datesPerRequestParam,
      Un f edMaxS ceKeyNum,
      MaxCand dateNumPerS ceKeyParam
    )

    val enumOverr des = FeatureSw chOverr deUt l.getEnumFSOverr des(
      NullStatsRece ver,
      Logger(getClass),
      ModelVers onParam
    )

    val boundedDurat onFSOverr des =
      FeatureSw chOverr deUt l.getBoundedDurat onFSOverr des(MaxT etAgeH sParam)

    val seqOverr des = FeatureSw chOverr deUt l.getLongSeqFSOverr des()

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set( ntOverr des: _*)
      .set(boundedDurat onFSOverr des: _*)
      .set(enumOverr des: _*)
      .set(seqOverr des: _*)
      .bu ld()
  }
}

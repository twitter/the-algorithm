package com.tw ter.tsp.common

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l

object Top cSoc alProofParams {

  object Top cT etsSemant cCoreVers on d
      extends FSBoundedParam[Long](
        na  = "top c_t ets_semant c_core_annotat on_vers on_ d",
        default = 1433487161551032320L,
        m n = 0L,
        max = Long.MaxValue
      )
  object Top cT etsSemant cCoreVers on dsSet
      extends FSParam[Set[Long]](
        na  = "top c_t ets_semant c_core_annotat on_vers on_ d_allo d_set",
        default = Set(Top cT etsSemant cCoreVers on d.default))

  /**
   * Controls t  Top c Soc al Proof cos ne s m lar y threshold for t  Top c T ets.
   */
  object T etToTop cCos neS m lar yThreshold
      extends FSBoundedParam[Double](
        na  = "top c_t ets_cos ne_s m lar y_threshold_tsp",
        default = 0.0,
        m n = 0.0,
        max = 1.0
      )

  object EnablePersonal zedContextTop cs // master feature sw ch to enable backf ll
      extends FSParam[Boolean](
        na  = "top c_t ets_personal zed_contexts_enable_personal zed_contexts",
        default = false
      )

  object Enable M ghtL keTop c
      extends FSParam[Boolean](
        na  = "top c_t ets_personal zed_contexts_enable_ _m ght_l ke",
        default = false
      )

  object EnableRecentEngage ntsTop c
      extends FSParam[Boolean](
        na  = "top c_t ets_personal zed_contexts_enable_recent_engage nts",
        default = false
      )

  object EnableTop cT et althF lterPersonal zedContexts
      extends FSParam[Boolean](
        na  = "top c_t ets_personal zed_contexts_ alth_sw ch",
        default = true
      )

  object EnableT etToTop cScoreRank ng
      extends FSParam[Boolean](
        na  = "top c_t ets_enable_t et_to_top c_score_rank ng",
        default = true
      )

}

object FeatureSw chConf g {
  pr vate val enumFeatureSw chOverr des = FeatureSw chOverr deUt l
    .getEnumFSOverr des(
      NullStatsRece ver,
      Logger(getClass),
    )

  pr vate val  ntFeatureSw chOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des()

  pr vate val longFeatureSw chOverr des = FeatureSw chOverr deUt l.getBoundedLongFSOverr des(
    Top cSoc alProofParams.Top cT etsSemant cCoreVers on d
  )

  pr vate val doubleFeatureSw chOverr des = FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(
    Top cSoc alProofParams.T etToTop cCos neS m lar yThreshold,
  )

  pr vate val longSetFeatureSw chOverr des = FeatureSw chOverr deUt l.getLongSetFSOverr des(
    Top cSoc alProofParams.Top cT etsSemant cCoreVers on dsSet,
  )

  pr vate val booleanFeatureSw chOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
    Top cSoc alProofParams.EnablePersonal zedContextTop cs,
    Top cSoc alProofParams.Enable M ghtL keTop c,
    Top cSoc alProofParams.EnableRecentEngage ntsTop c,
    Top cSoc alProofParams.EnableTop cT et althF lterPersonal zedContexts,
    Top cSoc alProofParams.EnableT etToTop cScoreRank ng,
  )
  val conf g: BaseConf g = BaseConf gBu lder()
    .set(enumFeatureSw chOverr des: _*)
    .set( ntFeatureSw chOverr des: _*)
    .set(longFeatureSw chOverr des: _*)
    .set(doubleFeatureSw chOverr des: _*)
    .set(longSetFeatureSw chOverr des: _*)
    .set(booleanFeatureSw chOverr des: _*)
    .bu ld()
}

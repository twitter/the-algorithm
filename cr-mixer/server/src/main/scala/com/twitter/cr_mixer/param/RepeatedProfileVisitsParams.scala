package com.tw ter.cr_m xer.param

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object RepeatedProf leV s sParams {
  object Prof leM nV s Param extends Enu rat on {
    protected case class S gnalTypeValue(s gnalType: S gnalType) extends super.Val
     mport scala.language. mpl c Convers ons
     mpl c  def valueToS gnalTypeValue(x: Value): S gnalTypeValue =
      x.as nstanceOf[S gnalTypeValue]

    val TotalV s s nPast180Days = S gnalTypeValue(S gnalType.RepeatedProf leV s 180dM nV s 6V1)
    val TotalV s s nPast90Days = S gnalTypeValue(S gnalType.RepeatedProf leV s 90dM nV s 6V1)
    val TotalV s s nPast14Days = S gnalTypeValue(S gnalType.RepeatedProf leV s 14dM nV s 2V1)
    val TotalV s s nPast180DaysNoNegat ve = S gnalTypeValue(
      S gnalType.RepeatedProf leV s 180dM nV s 6V1NoNegat ve)
    val TotalV s s nPast90DaysNoNegat ve = S gnalTypeValue(
      S gnalType.RepeatedProf leV s 90dM nV s 6V1NoNegat ve)
    val TotalV s s nPast14DaysNoNegat ve = S gnalTypeValue(
      S gnalType.RepeatedProf leV s 14dM nV s 2V1NoNegat ve)
  }

  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "tw stly_repeatedprof lev s s_enable_s ce",
        default = true
      )

  object M nScoreParam
      extends FSBoundedParam[Double](
        na  = "tw stly_repeatedprof lev s s_m n_score",
        default = 0.5,
        m n = 0.0,
        max = 1.0
      )

  object Prof leM nV s Type
      extends FSEnumParam[Prof leM nV s Param.type](
        na  = "tw stly_repeatedprof lev s s_m n_v s _type_ d",
        default = Prof leM nV s Param.TotalV s s nPast14Days,
        enum = Prof leM nV s Param
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(EnableS ceParam, Prof leM nV s Type)

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableS ceParam
    )

    val enumOverr des = FeatureSw chOverr deUt l.getEnumFSOverr des(
      NullStatsRece ver,
      Logger(getClass),
      Prof leM nV s Type
    )

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(enumOverr des: _*)
      .bu ld()
  }
}

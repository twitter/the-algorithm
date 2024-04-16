package com.tw ter.cr_m xer.param

 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSBoundedParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.follow_recom ndat ons.thr ftscala.D splayLocat on
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.f nagle.stats.NullStatsRece ver

object FrsParams {
  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "s gnal_frs_enable_s ce",
        default = false
      )

  object EnableS ceGraphParam
      extends FSParam[Boolean](
        na  = "graph_frs_enable_s ce",
        default = false
      )

  object M nScoreParam
      extends FSBoundedParam[Double](
        na  = "s gnal_frs_m n_score",
        default = 0.4,
        m n = 0.0,
        max = 1.0
      )

  object MaxConsu rSeedsNumParam
      extends FSBoundedParam[ nt](
        na  = "graph_frs_max_user_seeds_num",
        default = 200,
        m n = 0,
        max = 1000
      )

  /**
   * T se params below are only used for FrsT etCand dateGenerator and shouldn't be used  n ot r endpo nts
   *    * FrsBasedCand dateGenerat onMaxSeedsNumParam
   *    * FrsCand dateGenerat onD splayLocat onParam
   *    * FrsCand dateGenerat onD splayLocat on
   *    * FrsBasedCand dateGenerat onMaxCand datesNumParam
   */
  object FrsBasedCand dateGenerat onEnableV s b l yF lter ngParam
      extends FSParam[Boolean](
        na  = "frs_based_cand date_generat on_enable_vf",
        default = true
      )

  object FrsBasedCand dateGenerat onMaxSeedsNumParam
      extends FSBoundedParam[ nt](
        na  = "frs_based_cand date_generat on_max_seeds_num",
        default = 100,
        m n = 0,
        max = 800
      )

  object FrsBasedCand dateGenerat onD splayLocat on extends Enu rat on {
    protected case class FrsD splayLocat onValue(d splayLocat on: D splayLocat on) extends super.Val
     mport scala.language. mpl c Convers ons
     mpl c  def valueToD splayLocat onValue(x: Value): FrsD splayLocat onValue =
      x.as nstanceOf[FrsD splayLocat onValue]

    val D splayLocat on_ContentRecom nder: FrsD splayLocat onValue = FrsD splayLocat onValue(
      D splayLocat on.ContentRecom nder)
    val D splayLocat on_Ho : FrsD splayLocat onValue = FrsD splayLocat onValue(
      D splayLocat on.Ho T  l neT etRecs)
    val D splayLocat on_Not f cat ons: FrsD splayLocat onValue = FrsD splayLocat onValue(
      D splayLocat on.T etNot f cat onRecs)
  }

  object FrsBasedCand dateGenerat onD splayLocat onParam
      extends FSEnumParam[FrsBasedCand dateGenerat onD splayLocat on.type](
        na  = "frs_based_cand date_generat on_d splay_locat on_ d",
        default = FrsBasedCand dateGenerat onD splayLocat on.D splayLocat on_Ho ,
        enum = FrsBasedCand dateGenerat onD splayLocat on
      )

  object FrsBasedCand dateGenerat onMaxCand datesNumParam
      extends FSBoundedParam[ nt](
        na  = "frs_based_cand date_generat on_max_cand dates_num",
        default = 100,
        m n = 0,
        max = 2000
      )

  val AllParams: Seq[Param[_] w h FSNa ] = Seq(
    EnableS ceParam,
    EnableS ceGraphParam,
    M nScoreParam,
    MaxConsu rSeedsNumParam,
    FrsBasedCand dateGenerat onMaxSeedsNumParam,
    FrsBasedCand dateGenerat onD splayLocat onParam,
    FrsBasedCand dateGenerat onMaxCand datesNumParam,
    FrsBasedCand dateGenerat onEnableV s b l yF lter ngParam
  )

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableS ceParam,
      EnableS ceGraphParam,
      FrsBasedCand dateGenerat onEnableV s b l yF lter ngParam
    )

    val doubleOverr des = FeatureSw chOverr deUt l.getBoundedDoubleFSOverr des(M nScoreParam)

    val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
      MaxConsu rSeedsNumParam,
      FrsBasedCand dateGenerat onMaxSeedsNumParam,
      FrsBasedCand dateGenerat onMaxCand datesNumParam)

    val enumOverr des = FeatureSw chOverr deUt l.getEnumFSOverr des(
      NullStatsRece ver,
      Logger(getClass),
      FrsBasedCand dateGenerat onD splayLocat onParam,
    )
    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(doubleOverr des: _*)
      .set( ntOverr des: _*)
      .set(enumOverr des: _*)
      .bu ld()
  }
}

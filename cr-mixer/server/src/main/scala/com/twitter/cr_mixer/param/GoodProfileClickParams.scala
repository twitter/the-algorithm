package com.tw ter.cr_m xer.param

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.logg ng.Logger
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FSEnumParam
 mport com.tw ter.t  l nes.conf gap .FSNa 
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.users gnalserv ce.thr ftscala.S gnalType

object GoodProf leCl ckParams {

  object Cl ckM nD llT  Param extends Enu rat on {
    protected case class S gnalTypeValue(s gnalType: S gnalType) extends super.Val
     mport scala.language. mpl c Convers ons
     mpl c  def valueToS gnalTypeValue(x: Value): S gnalTypeValue =
      x.as nstanceOf[S gnalTypeValue]

    val TotalD llT  10s = S gnalTypeValue(S gnalType.GoodProf leCl ck)
    val TotalD llT  20s = S gnalTypeValue(S gnalType.GoodProf leCl ck20s)
    val TotalD llT  30s = S gnalTypeValue(S gnalType.GoodProf leCl ck30s)

  }

  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "s gnal_good_prof le_cl cks_enable_s ce",
        default = false
      )

  object Cl ckM nD llT  Type
      extends FSEnumParam[Cl ckM nD llT  Param.type](
        na  = "s gnal_good_prof le_cl cks_m n_d llt  _type_ d",
        default = Cl ckM nD llT  Param.TotalD llT  10s,
        enum = Cl ckM nD llT  Param
      )

  val AllParams: Seq[Param[_] w h FSNa ] =
    Seq(EnableS ceParam, Cl ckM nD llT  Type)

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableS ceParam
    )

    val enumOverr des = FeatureSw chOverr deUt l.getEnumFSOverr des(
      NullStatsRece ver,
      Logger(getClass),
      Cl ckM nD llT  Type
    )

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(enumOverr des: _*)
      .bu ld()
  }
}

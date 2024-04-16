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

object V deoV ewT etsParams {
  object EnableS ceParam
      extends FSParam[Boolean](
        na  = "s gnal_v deov ewt ets_enable_s ce",
        default = false
      )

  object EnableS ce mpress onParam
      extends FSParam[Boolean](
        na  = "s gnal_v deov ewt ets_enable mpress on_s ce",
        default = false
      )

  object V deoV ewT etType extends Enu rat on {
    protected case class S gnalTypeValue(s gnalType: S gnalType) extends super.Val
     mport scala.language. mpl c Convers ons
     mpl c  def valueToS gnalTypeValue(x: Value): S gnalTypeValue =
      x.as nstanceOf[S gnalTypeValue]

    val V deoT etQual yV ew: S gnalTypeValue = S gnalTypeValue(S gnalType.V deoV ew90dQual yV1)
    val V deoT etPlayback50: S gnalTypeValue = S gnalTypeValue(S gnalType.V deoV ew90dPlayback50V1)
  }

  object V deoV ewT etTypeParam
      extends FSEnumParam[V deoV ewT etType.type](
        na  = "s gnal_v deov ewt ets_v deov ewtype_ d",
        default = V deoV ewT etType.V deoT etQual yV ew,
        enum = V deoV ewT etType
      )

  val AllParams: Seq[Param[_] w h FSNa ] =
    Seq(EnableS ceParam, EnableS ce mpress onParam, V deoV ewT etTypeParam)

  lazy val conf g: BaseConf g = {
    val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
      EnableS ceParam,
      EnableS ce mpress onParam,
    )
    val enumOverr des =
      FeatureSw chOverr deUt l.getEnumFSOverr des(
        NullStatsRece ver,
        Logger(getClass),
        V deoV ewT etTypeParam)

    BaseConf gBu lder()
      .set(booleanOverr des: _*)
      .set(enumOverr des: _*)
      .bu ld()
  }

}

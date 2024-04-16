package com.tw ter.t  l neranker.para ters.recap_hydrat on

 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.servo.dec der.Dec derKeyNa 
 mport com.tw ter.t  l neranker.dec der.Dec derKey
 mport com.tw ter.t  l neranker.para ters.recap_hydrat on.RecapHydrat onParams._
 mport com.tw ter.t  l neranker.para ters.ut l.Conf g lper
 mport com.tw ter.t  l nes.conf gap ._

object RecapHydrat onProduct on {
  val dec derByParam: Map[Param[_], Dec derKeyNa ] = Map[Param[_], Dec derKeyNa ](
    EnableContentFeaturesHydrat onParam -> Dec derKey.RecapHydrat onEnableContentFeaturesHydrat on
  )

  val booleanParams: Seq[EnableContentFeaturesHydrat onParam.type] = Seq(
    EnableContentFeaturesHydrat onParam
  )

  val booleanFeatureSw chParams: Seq[FSParam[Boolean]] = Seq(
    EnableTokens nContentFeaturesHydrat onParam,
    EnableT etText nContentFeaturesHydrat onParam,
    EnableConversat onControl nContentFeaturesHydrat onParam,
    EnableT et d aHydrat onParam
  )
}

class RecapHydrat onProduct on(dec derGateBu lder: Dec derGateBu lder) {
  val conf g lper: Conf g lper =
    new Conf g lper(RecapHydrat onProduct on.dec derByParam, dec derGateBu lder)
  val booleanOverr des: Seq[Opt onalOverr de[Boolean]] =
    conf g lper.createDec derBasedBooleanOverr des(RecapHydrat onProduct on.booleanParams)

  val booleanFeatureSw chOverr des: Seq[Opt onalOverr de[Boolean]] =
    FeatureSw chOverr deUt l.getBooleanFSOverr des(
      RecapHydrat onProduct on.booleanFeatureSw chParams: _*
    )

  val conf g: BaseConf g = new BaseConf gBu lder()
    .set(
      booleanOverr des: _*
    ).set(
      booleanFeatureSw chOverr des: _*
    )
    .bu ld(RecapHydrat onProduct on.getClass.getS mpleNa )
}

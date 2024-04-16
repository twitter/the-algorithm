package com.tw ter.t  l neranker.para ters.ent y_t ets

 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.servo.dec der.Dec derKeyNa 
 mport com.tw ter.t  l neranker.dec der.Dec derKey
 mport com.tw ter.t  l neranker.para ters.ent y_t ets.Ent yT etsParams._
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derUt ls
 mport com.tw ter.t  l nes.conf gap .BaseConf g
 mport com.tw ter.t  l nes.conf gap .BaseConf gBu lder
 mport com.tw ter.t  l nes.conf gap .FeatureSw chOverr deUt l
 mport com.tw ter.t  l nes.conf gap .Param

object Ent yT etsProduct on {
  val dec derByParam: Map[Param[_], Dec derKeyNa ] = Map[Param[_], Dec derKeyNa ](
    EnableContentFeaturesHydrat onParam -> Dec derKey.Ent yT etsEnableContentFeaturesHydrat on
  )
}

case class Ent yT etsProduct on(dec derGateBu lder: Dec derGateBu lder) {

  val booleanDec derOverr des = Dec derUt ls.getBooleanDec derOverr des(
    dec derGateBu lder,
    EnableContentFeaturesHydrat onParam
  )

  val booleanFeatureSw chOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
    EnableTokens nContentFeaturesHydrat onParam,
    EnableT etText nContentFeaturesHydrat onParam,
    EnableConversat onControl nContentFeaturesHydrat onParam,
    EnableT et d aHydrat onParam
  )

  val  ntFeatureSw chOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
    MaxFollo dUsersParam
  )

  val conf g: BaseConf g = new BaseConf gBu lder()
    .set(booleanDec derOverr des: _*)
    .set(booleanFeatureSw chOverr des: _*)
    .set( ntFeatureSw chOverr des: _*)
    .bu ld()
}

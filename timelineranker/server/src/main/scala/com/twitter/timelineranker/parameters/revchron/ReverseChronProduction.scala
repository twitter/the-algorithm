package com.tw ter.t  l neranker.para ters.revchron

 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.t  l nes.conf gap ._

object ReverseChronProduct on {
  val  ntFeatureSw chParams = Seq(ReverseChronParams.MaxFollo dUsersParam)
  val booleanFeatureSw chParams = Seq(
    ReverseChronParams.ReturnEmptyW nOverMaxFollowsParam,
    ReverseChronParams.D rectedAtNarrowcast ngV aSearchParam,
    ReverseChronParams.PostF lter ngBasedOnSearch tadataEnabledParam
  )
}

class ReverseChronProduct on(dec derGateBu lder: Dec derGateBu lder) {
  val  ntOverr des = FeatureSw chOverr deUt l.getBounded ntFSOverr des(
    ReverseChronProduct on. ntFeatureSw chParams: _*
  )

  val booleanOverr des = FeatureSw chOverr deUt l.getBooleanFSOverr des(
    ReverseChronProduct on.booleanFeatureSw chParams: _*
  )

  val conf g: BaseConf g = new BaseConf gBu lder()
    .set( ntOverr des: _*)
    .set(booleanOverr des: _*)
    .bu ld(ReverseChronProduct on.getClass.getS mpleNa )
}

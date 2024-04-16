package com.tw ter.t  l neranker.para ters.ut l

 mport com.tw ter.servo.dec der.Dec derGateBu lder
 mport com.tw ter.servo.dec der.Dec derKeyNa 
 mport com.tw ter.t  l nes.conf gap ._
 mport com.tw ter.t  l nes.conf gap .dec der.Dec der ntSpaceOverr deValue
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derSw chOverr deValue
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derValueConverter
 mport com.tw ter.t  l nes.conf gap .dec der.Rec p entBu lder

class Conf g lper(
  dec derByParam: Map[Param[_], Dec derKeyNa ],
  dec derGateBu lder: Dec derGateBu lder) {
  def createDec derBasedBooleanOverr des(
    para ters: Seq[Param[Boolean]]
  ): Seq[Opt onalOverr de[Boolean]] = {
    para ters.map { para ter =>
      para ter.opt onalOverr deValue(
        Dec derSw chOverr deValue(
          feature = dec derGateBu lder.keyToFeature(dec derByParam(para ter)),
          rec p entBu lder = Rec p entBu lder.User,
          enabledValue = true,
          d sabledValueOpt on = So (false)
        )
      )
    }
  }

  def createDec derBasedOverr des[T](
    para ters: Seq[Param[T] w h Dec derValueConverter[T]]
  ): Seq[Opt onalOverr de[T]] = {
    para ters.map { para ter =>
      para ter.opt onalOverr deValue(
        Dec der ntSpaceOverr deValue(
          feature = dec derGateBu lder.keyToFeature(dec derByParam(para ter)),
          convers on = para ter.convert
        )
      )
    }
  }
}

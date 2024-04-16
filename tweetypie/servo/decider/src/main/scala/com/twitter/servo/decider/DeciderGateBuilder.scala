package com.tw ter.servo.dec der

 mport com.tw ter.dec der.{Dec der, Feature}
 mport com.tw ter.servo.ut l.Gate
 mport com.tw ter.servo.gate.Dec derGate

/**
 * Conven ence syntax for creat ng dec der gates
 */
class Dec derGateBu lder(dec der: Dec der) {

  /**
   *  dGate should be used w n t  result of t  gate needs to be cons stent bet en repeated
   *  nvocat ons, w h t  cond  on that cons stency  s dependent up on pass ng  dent cal
   * para ter bet en t   nvocat ons.
   */
  def  dGate(key: Dec derKeyNa ): Gate[Long] =
    Dec derGate.by d(keyToFeature(key))

  /**
   * l nearGate should be used w n t  probab l y of t  gate return ng true needs to
   *  ncrease l nearly w h t  ava lab l y of feature.
   */
  def l nearGate(key: Dec derKeyNa ): Gate[Un ] =
    Dec derGate.l near(keyToFeature(key))

  /**
   * typedL nearGate  s a l nearGate that conforms to t  gate of t  spec f ed type.
   */
  def typedL nearGate[T](key: Dec derKeyNa ): Gate[T] =
    l nearGate(key).contramap[T] { _ => () }

  /**
   * expGate should be used w n t  probab l y of t  gate return ng true needs to
   *  ncrease exponent ally w h t  ava lab l y of feature.
   */
  def expGate(key: Dec derKeyNa , exponent:  nt): Gate[Un ] =
    Dec derGate.exp(keyToFeature(key), exponent)

  def keyToFeature(key: Dec derKeyNa ): Feature = dec der.feature(key.toStr ng)
}

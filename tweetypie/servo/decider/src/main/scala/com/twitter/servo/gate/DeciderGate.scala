package com.tw ter.servo.gate

 mport com.tw ter.dec der
 mport com.tw ter.servo.ut l.Gate
 mport scala.annotat on.ta lrec

object Dec derGate {

  /**
   * Create a Gate[Un ] w h a probab l y of return ng true
   * that  ncreases l nearly w h t  ava lab l y of feature.
   */
  def l near(feature: dec der.Feature): Gate[Un ] =
    Gate(_ => feature. sAva lable, "Dec derGate.l near(%s)".format(feature))

  /**
   * Create a Gate[Un ] w h a probab l y of return ng true
   * that  ncreases exponent ally w h t  ava lab l y of feature.
   */
  def exp(feature: dec der.Feature, exponent:  nt): Gate[Un ] = {
    val gate =  f (exponent >= 0) l near(feature) else !l near(feature)

    @ta lrec
    def go(exp:  nt): Boolean =  f (exp == 0) true else (gate() && go(exp - 1))

    Gate(_ => go(math.abs(exponent)), "Dec derGate.exp(%s, %s)".format(feature, exponent))
  }

  /**
   * Create a Gate[Long] that returns true  f t  g ven feature  s ava lable for an  d.
   */
  def by d(feature: dec der.Feature): Gate[Long] =
    Gate( d => feature. sAva lable( d), "Dec derGate.by d(%s)".format(feature))
}

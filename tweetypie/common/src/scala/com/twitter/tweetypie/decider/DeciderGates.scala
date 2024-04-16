package com.tw ter.t etyp e
package dec der

 mport com.google.common.hash.Hash ng
 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.Feature
 mport com.tw ter.servo.gate.Dec derGate
 mport com.tw ter.servo.ut l.Gate
 mport java.n o.charset.StandardCharsets
 mport scala.collect on.mutable
tra  Dec derGates {
  def overr des: Map[Str ng, Boolean] = Map.empty
  def dec der: Dec der
  def pref x: Str ng

  protected val seenFeatures: mutable.HashSet[Str ng] = new mutable.HashSet[Str ng]

  pr vate def dec derFeature(na : Str ng): Feature = {
    dec der.feature(pref x + "_" + na )
  }

  def w hOverr de[T](na : Str ng, mkGate: Feature => Gate[T]): Gate[T] = {
    seenFeatures += na 
    overr des.get(na ).map(Gate.const).getOrElse(mkGate(dec derFeature(na )))
  }

  protected def l near(na : Str ng): Gate[Un ] = w hOverr de[Un ](na , Dec derGate.l near)
  protected def by d(na : Str ng): Gate[Long] = w hOverr de[Long](na , Dec derGate.by d)

  /**
   *   returns a Gate[Str ng] that can be used to c ck ava lab l y of t  feature.
   * T  str ng  s has d  nto a Long and used as an " d" and t n used to call servo's
   * Dec derGate.by d
   *
   * @param na  dec der na 
   * @return Gate[Str ng]
   */
  protected def byStr ng d(na : Str ng): Gate[Str ng] =
    by d(na ).contramap { s: Str ng =>
      Hash ng.s pHash24().hashStr ng(s, StandardCharsets.UTF_8).asLong()
    }

  def all: Traversable[Str ng] = seenFeatures

  def unusedOverr des: Set[Str ng] = overr des.keySet.d ff(all.toSet)

  /**
   * Generate a map of na  -> ava lab l y, tak ng  nto account overr des.
   * Overr des are e  r on or off so map to 10000 or 0, respect vely.
   */
  def ava lab l yMap: Map[Str ng, Opt on[ nt]] =
    all.map { na  =>
      val ava lab l y: Opt on[ nt] = overr des
        .get(na )
        .map(on =>  f (on) 10000 else 0)
        .orElse(dec derFeature(na ).ava lab l y)

      na  -> ava lab l y
    }.toMap
}

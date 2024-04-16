package com.tw ter.t etyp e.cach ng

 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  

/**
 *  lpers for creat ng common exp ry funct ons.
 *
 * An exp ry funct on maps from t  value to a t    n t  future w n
 * t  value should exp re from cac . T se are useful  n t 
 *  mple ntat on of a [[ValueSer al zer]].
 */
object Exp ry {

  /**
   * Return a t   that  nd cates to  mcac d to never exp re t 
   * value.
   *
   * T  funct on takes [[Any]] so that   can be used at any value
   * type, s nce   doesn't exam ne t  value at all.
   */
  val Never: Any => T   =
    _ => T  .Top

  /**
   * Return funct on that  nd cates to  mcac d that t  value should
   * not be used after t  `ttl` has elapsed.
   *
   * T  funct on takes [[Any]] so that   can be used at any value
   * type, s nce   doesn't exam ne t  value at all.
   */
  def byAge(ttl: Durat on): Any => T   =
    _ => T  .now + ttl
}

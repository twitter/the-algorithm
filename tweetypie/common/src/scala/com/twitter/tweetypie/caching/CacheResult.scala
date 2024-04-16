package com.tw ter.t etyp e.cach ng

/**
 * Encodes t  poss ble states of a value loaded from  mcac d.
 *
 * @see [[ValueSer al zer]] and [[Cac Operat ons]]
 */
sealed tra  Cac Result[+V]

object Cac Result {

  /**
   * S gnals that t  value could not be successfully loaded from
   * cac . `Fa lure` values should not be wr ten back to cac .
   *
   * T  value may result from an error talk ng to t   mcac d
   *  nstance or   may be returned from t  Ser al zer w n t  value
   * should not be reused, but should also not be overwr ten.
   */
  f nal case class Fa lure(e: Throwable) extends Cac Result[Noth ng]

  /**
   * S gnals that t  cac  load attempt was successful, but t re was
   * not a usable value.
   *
   * W n process ng a `M ss`, t  value should be wr ten back to
   * cac   f   loads successfully.
   */
  case object M ss extends Cac Result[Noth ng]

  /**
   * S gnals that t  value was found  n cac .
   *
   *    s not necessary to load t  value from t  or g nal s ce.
   */
  case class Fresh[V](value: V) extends Cac Result[V]

  /**
   * S gnals that t  value was found  n cac .
   *
   * T  value should be used, but   should be refres d
   * out-of-band.
   */
  case class Stale[V](value: V) extends Cac Result[V]
}

package com.tw ter.t etyp e.serverut l

 mport com.tw ter.t etyp e.thr ftscala.CardReference
 mport com.tw ter.ut l.Try
 mport java.net.UR 
 mport scala.ut l.control.NonFatal

/**
 * Ut l y to extract t  stored card  d out of a CardReference
 */
object StoredCard {

  pr vate val cardSc   = "card"
  pr vate val cardPref x = s"$cardSc  ://"

  /**
   * Looks at t  CardReference to determ nes  f t  cardUr  po nts to a "stored"
   * card  d. Stored Card UR s are are expected to be  n t  format "card://<long>"
   * (case sens  ve).  n future t se UR s can potent ally be:
   * "card://<long>[/path[?queryStr ng]]. Note that t  ut l y cares just about t 
   * "Stored Card" types. So   just sk ps t  ot r card types.
   */
  def unapply(cr: CardReference): Opt on[Long] = {
    try {
      for {
        ur Str <- Opt on(cr.cardUr )  f ur Str.startsW h(cardPref x)
        ur  <- Try(new UR (ur Str)).toOpt on
         f ur .getSc   == cardSc   && ur .getHost != null
      } y eld ur .getHost.toLong // throws NumberFormatExcept on non nu r c host (card ds)
    } catch {
      // T  val dat ons are done upstream by t  T etBu lder, so except ons
      // due to bad UR s w ll be swallo d.
      case NonFatal(e) => None
    }
  }
}

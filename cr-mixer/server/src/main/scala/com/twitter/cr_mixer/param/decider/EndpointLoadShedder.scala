package com.tw ter.cr_m xer.param.dec der

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport scala.ut l.control.NoStackTrace

/*
  Prov des dec ders-controlled load s dd ng for a g ven Product from a g ven endpo nt.
  T  format of t  dec der keys  s:

    enable_loads dd ng_<endpo nt na >_<product na >
  E.g.:
    enable_loads dd ng_getT etRecom ndat ons_Not f cat ons

  Dec ders are fract onal, so a value of 50.00 w ll drop 50% of responses.  f a dec der key  s not
  def ned for a part cular endpo nt/product comb nat on, those requests w ll always be
  served.

    should t refore a m to def ne keys for t  endpo nts/product   care most about  n dec der.yml,
  so that   can control t m dur ng  nc dents.
 */
case class Endpo ntLoadS dder @ nject() (
  dec der: Dec der,
  statsRece ver: StatsRece ver) {
   mport Endpo ntLoadS dder._

  // Fall back to False for any undef ned key
  pr vate val dec derW hFalseFallback: Dec der = dec der.orElse(Dec der.False)
  pr vate val keyPref x = "enable_loads dd ng"
  pr vate val scopedStats = statsRece ver.scope("Endpo ntLoadS dder")

  def apply[T](endpo ntNa : Str ng, product: Str ng)(serve: => Future[T]): Future[T] = {
    /*
    C cks  f e  r per-product or top-level load s dd ng  s enabled
     f both are enabled at d fferent percentages, load s dd ng w ll not be perfectly calculable due
    to salt ng of hash ( .e. 25% load s d for Product x + 25% load s d for overall does not
    result  n 50% load s d for x)
     */
    val keyTyped = s"${keyPref x}_${endpo ntNa }_$product"
    val keyTopLevel = s"${keyPref x}_${endpo ntNa }"

     f (dec derW hFalseFallback. sAva lable(keyTopLevel, rec p ent = So (RandomRec p ent))) {
      scopedStats.counter(keyTopLevel). ncr
      Future.except on(LoadS dd ngExcept on)
    } else  f (dec derW hFalseFallback. sAva lable(keyTyped, rec p ent = So (RandomRec p ent))) {
      scopedStats.counter(keyTyped). ncr
      Future.except on(LoadS dd ngExcept on)
    } else serve
  }
}

object Endpo ntLoadS dder {
  object LoadS dd ngExcept on extends Except on w h NoStackTrace
}

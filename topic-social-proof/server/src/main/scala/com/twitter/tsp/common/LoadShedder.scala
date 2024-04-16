package com.tw ter.tsp.common

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport scala.ut l.control.NoStackTrace

/*
  Prov des dec ders-controlled load s dd ng for a g ven d splayLocat on
  T  format of t  dec der keys  s:

    enable_loads dd ng_<d splay locat on>
  E.g.:
    enable_loads dd ng_Ho T  l ne

  Dec ders are fract onal, so a value of 50.00 w ll drop 50% of responses.  f a dec der key  s not
  def ned for a part cular d splayLocat on, those requests w ll always be served.

    should t refore a m to def ne keys for t  locat ons   care most about  n dec der.yml,
  so that   can control t m dur ng  nc dents.
 */
class LoadS dder @ nject() (dec der: Dec der) {
   mport LoadS dder._

  // Fall back to False for any undef ned key
  pr vate val dec derW hFalseFallback: Dec der = dec der.orElse(Dec der.False)
  pr vate val keyPref x = "enable_loads dd ng"

  def apply[T](typeStr ng: Str ng)(serve: => Future[T]): Future[T] = {
    /*
    Per-typeStr ng level load s dd ng: enable_loads dd ng_Ho T  l ne
    C cks  f per-typeStr ng load s dd ng  s enabled
     */
    val keyTyped = s"${keyPref x}_$typeStr ng"
     f (dec derW hFalseFallback. sAva lable(keyTyped, rec p ent = So (RandomRec p ent)))
      Future.except on(LoadS dd ngExcept on)
    else serve
  }
}

object LoadS dder {
  object LoadS dd ngExcept on extends Except on w h NoStackTrace
}

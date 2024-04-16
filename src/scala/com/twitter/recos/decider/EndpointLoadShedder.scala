package com.tw ter.recos.dec der

 mport com.tw ter.dec der.Dec der
 mport com.tw ter.dec der.RandomRec p ent
 mport com.tw ter.ut l.Future
 mport scala.ut l.control.NoStackTrace

/*
  Prov des dec ders-controlled load s dd ng for a g ven endpo nt.
  T  format of t  dec der keys  s:

    enable_loads dd ng_<graphNa Pref x>_<endpo nt na >
  E.g.:
    enable_loads dd ng_user-t et-graph_relatedT ets

  Dec ders are fract onal, so a value of 50.00 w ll drop 50% of responses.  f a dec der key  s not
  def ned for a part cular endpo nt, those requests w ll always be
  served.

    should t refore a m to def ne keys for t  endpo nts   care most about  n dec der.yml,
  so that   can control t m dur ng  nc dents.
 */
class Endpo ntLoadS dder(
  dec der: GraphDec der) {
   mport Endpo ntLoadS dder._

  pr vate val keyPref x = "enable_loads dd ng"

  def apply[T](endpo ntNa : Str ng)(serve: => Future[T]): Future[T] = {
    val key = s"${keyPref x}_${dec der.graphNa Pref x}_${endpo ntNa }"
     f (dec der. sAva lable(key, rec p ent = So (RandomRec p ent)))
      Future.except on(LoadS dd ngExcept on)
    else serve
  }
}

object Endpo ntLoadS dder {
  object LoadS dd ngExcept on extends Except on w h NoStackTrace
}

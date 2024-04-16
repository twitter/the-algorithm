package com.tw ter.recos.user_t et_ent y_graph

 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.f nagle.trac ng.{Trace, Trace d}
 mport com.tw ter.recos.user_t et_ent y_graph.thr ftscala._
 mport com.tw ter.ut l.Future

object UserT etEnt yGraph {
  def trace d: Trace d = Trace. d
  def cl ent d: Opt on[Cl ent d] = Cl ent d.current
}

class UserT etEnt yGraph(
  recom ndat onHandler: Recom ndat onHandler,
  t etSoc alProofHandler: T etSoc alProofHandler,
  soc alProofHandler: Soc alProofHandler)
    extends thr ftscala.UserT etEnt yGraph. thodPerEndpo nt {

  overr de def recom ndT ets(
    request: Recom ndT etEnt yRequest
  ): Future[Recom ndT etEnt yResponse] = recom ndat onHandler(request)

  /**
   * G ven a query user,  s seed users, and a set of  nput t ets, return t  soc al proofs of
   *  nput t ets  f any.
   *
   * Currently t  supports cl ents such as Ema l Recom ndat ons, Mag cRecs, and Ho T  l ne.
   *  n order to avo d  avy m grat on work,   are reta n ng t  endpo nt.
   */
  overr de def f ndT etSoc alProofs(
    request: Soc alProofRequest
  ): Future[Soc alProofResponse] = t etSoc alProofHandler(request)

  /**
   * F nd soc al proof for t  spec f ed Recom ndat onType g ven a set of  nput  ds of that type.
   * Only f nd soc al proofs from t  spec f ed seed users w h t  spec f ed soc al proof types.
   *
   * Currently t  supports url soc al proof generat on for Gu de.
   *
   * T  endpo nt  s flex ble enough to support soc al proof generat on for all recom ndat on
   * types, and should be used for all future cl ents of t  serv ce.
   */
  overr de def f ndRecom ndat onSoc alProofs(
    request: Recom ndat onSoc alProofRequest
  ): Future[Recom ndat onSoc alProofResponse] = soc alProofHandler(request)
}

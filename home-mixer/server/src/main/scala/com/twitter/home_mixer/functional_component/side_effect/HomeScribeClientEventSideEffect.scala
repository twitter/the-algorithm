package com.tw ter.ho _m xer.funct onal_component.s de_effect

 mport com.tw ter.cl entapp.thr ftscala.LogEvent
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.ho _m xer.ut l.Cand datesUt l
 mport com.tw ter.logp pel ne.cl ent.common.EventPubl s r
 mport com.tw ter.product_m xer.component_l brary.s de_effect.Scr beCl entEventS deEffect
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.T  l ne
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * S de effect that logs served t et  tr cs to Scr be as cl ent events.
 */
case class Ho Scr beCl entEventS deEffect(
  enableScr beCl entEvents: Boolean,
  overr de val logP pel nePubl s r: EventPubl s r[LogEvent],
   njectedT etsCand dateP pel ne dent f ers: Seq[Cand dateP pel ne dent f er],
  adsCand dateP pel ne dent f er: Opt on[Cand dateP pel ne dent f er] = None,
  whoToFollowCand dateP pel ne dent f er: Opt on[Cand dateP pel ne dent f er] = None,
  whoToSubscr beCand dateP pel ne dent f er: Opt on[Cand dateP pel ne dent f er] = None)
    extends Scr beCl entEventS deEffect[P pel neQuery, T  l ne]
    w h P pel neResultS deEffect.Cond  onally[
      P pel neQuery,
      T  l ne
    ] {

  overr de val  dent f er: S deEffect dent f er = S deEffect dent f er("Ho Scr beCl entEvent")

  overr de val page = "t  l nem xer"

  overr de def only f(
    query: P pel neQuery,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: T  l ne
  ): Boolean = enableScr beCl entEvents

  overr de def bu ldCl entEvents(
    query: P pel neQuery,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: T  l ne
  ): Seq[Scr beCl entEventS deEffect.Cl entEvent] = {

    val  emCand dates = Cand datesUt l.get emCand dates(selectedCand dates)
    val s ces =  emCand dates.groupBy(_.s ce)
    val  njectedT ets =
       njectedT etsCand dateP pel ne dent f ers.flatMap(s ces.getOrElse(_, Seq.empty))
    val promotedT ets = adsCand dateP pel ne dent f er.flatMap(s ces.get).toSeq.flatten

    // WhoToFollow and WhoToSubscr be modules are not requ red for all ho -m xer products, e.g. l st t ets t  l ne.
    val whoToFollowUsers = whoToFollowCand dateP pel ne dent f er.flatMap(s ces.get).toSeq.flatten
    val whoToSubscr beUsers =
      whoToSubscr beCand dateP pel ne dent f er.flatMap(s ces.get).toSeq.flatten

    val servedEvents = ServedEventsBu lder
      .bu ld(query,  njectedT ets, promotedT ets, whoToFollowUsers, whoToSubscr beUsers)

    val emptyT  l neEvents = EmptyT  l neEventsBu lder.bu ld(query,  njectedT ets)

    val queryEvents = QueryEventsBu lder.bu ld(query,  njectedT ets)

    (servedEvents ++ emptyT  l neEvents ++ queryEvents).f lter(_.eventValue.forall(_ > 0))
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.9)
  )
}

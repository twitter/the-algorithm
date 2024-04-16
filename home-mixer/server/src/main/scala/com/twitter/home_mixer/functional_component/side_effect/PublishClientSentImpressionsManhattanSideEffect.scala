package com.tw ter.ho _m xer.funct onal_component.s de_effect

 mport com.tw ter.ho _m xer.model.Ho Features.T et mpress onsFeature
 mport com.tw ter.ho _m xer.model.request.HasSeenT et ds
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes. mpress on.{thr ftscala => t}
 mport com.tw ter.t  l nes. mpress onstore.store.ManhattanT et mpress onStoreCl ent
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * S de effect that updates t  t  l nes t et  mpress on
 * store (Manhattan) w h seen t et  Ds sent from cl ents
 */
@S ngleton
class Publ shCl entSent mpress onsManhattanS deEffect @ nject() (
  manhattanT et mpress onStoreCl ent: ManhattanT et mpress onStoreCl ent)
    extends P pel neResultS deEffect[P pel neQuery w h HasSeenT et ds, HasMarshall ng]
    w h P pel neResultS deEffect.Cond  onally[
      P pel neQuery w h HasSeenT et ds,
      HasMarshall ng
    ] {

  overr de val  dent f er: S deEffect dent f er =
    S deEffect dent f er("Publ shCl entSent mpress onsManhattan")

  overr de def only f(
    query: P pel neQuery w h HasSeenT et ds,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: HasMarshall ng
  ): Boolean = query.seenT et ds.ex sts(_.nonEmpty)

  def bu ldEvents(query: P pel neQuery): Opt on[(Long, t.T et mpress onsEntr es)] = {
    query.features.flatMap { featureMap =>
      val  mpress ons = featureMap.getOrElse(T et mpress onsFeature, Seq.empty)
       f ( mpress ons.nonEmpty)
        So ((query.getRequ redUser d, t.T et mpress onsEntr es( mpress ons)))
      else None
    }
  }

  f nal overr de def apply(
     nputs: P pel neResultS deEffect. nputs[P pel neQuery w h HasSeenT et ds, HasMarshall ng]
  ): St ch[Un ] = {
    val events = bu ldEvents( nputs.query)

    St ch
      .traverse(events) {
        case (key, value) => manhattanT et mpress onStoreCl ent.wr e(key, value)
      }
      .un 
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.4)
  )
}

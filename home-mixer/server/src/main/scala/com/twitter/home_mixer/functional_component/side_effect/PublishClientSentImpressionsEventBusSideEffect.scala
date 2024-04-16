package com.tw ter.ho _m xer.funct onal_component.s de_effect

 mport com.tw ter.eventbus.cl ent.EventBusPubl s r
 mport com.tw ter.ho _m xer.model.request.Follow ngProduct
 mport com.tw ter.ho _m xer.model.request.For Product
 mport com.tw ter.ho _m xer.model.request.Subscr bedProduct
 mport com.tw ter.ho _m xer.model.request.HasSeenT et ds
 mport com.tw ter.ho _m xer.serv ce.Ho M xerAlertConf g
 mport com.tw ter.product_m xer.core.funct onal_component.s de_effect.P pel neResultS deEffect
 mport com.tw ter.product_m xer.core.model.common. dent f er.S deEffect dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.marshall ng.HasMarshall ng
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes. mpress onstore.thr ftscala. mpress on
 mport com.tw ter.t  l nes. mpress onstore.thr ftscala. mpress onL st
 mport com.tw ter.t  l nes. mpress onstore.thr ftscala.Publ s d mpress onL st
 mport com.tw ter.t  l nes. mpress onstore.thr ftscala.SurfaceArea
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton

object Publ shCl entSent mpress onsEventBusS deEffect {
  val Ho SurfaceArea: Opt on[Set[SurfaceArea]] = So (Set(SurfaceArea.Ho T  l ne))
  val Ho LatestSurfaceArea: Opt on[Set[SurfaceArea]] = So (Set(SurfaceArea.Ho LatestT  l ne))
  val Ho Subscr bedSurfaceArea: Opt on[Set[SurfaceArea]] = So (Set(SurfaceArea.Ho Subscr bed))
}

/**
 * S de effect that publ s s seen t et  Ds sent from cl ents. T  seen t et  Ds are sent to a
 *  ron topology wh ch wr es to a  mcac  dataset.
 */
@S ngleton
class Publ shCl entSent mpress onsEventBusS deEffect @ nject() (
  eventBusPubl s r: EventBusPubl s r[Publ s d mpress onL st])
    extends P pel neResultS deEffect[P pel neQuery w h HasSeenT et ds, HasMarshall ng]
    w h P pel neResultS deEffect.Cond  onally[
      P pel neQuery w h HasSeenT et ds,
      HasMarshall ng
    ] {
   mport Publ shCl entSent mpress onsEventBusS deEffect._

  overr de val  dent f er: S deEffect dent f er =
    S deEffect dent f er("Publ shCl entSent mpress onsEventBus")

  overr de def only f(
    query: P pel neQuery w h HasSeenT et ds,
    selectedCand dates: Seq[Cand dateW hDeta ls],
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    droppedCand dates: Seq[Cand dateW hDeta ls],
    response: HasMarshall ng
  ): Boolean = query.seenT et ds.ex sts(_.nonEmpty)

  def bu ldEvents(
    query: P pel neQuery w h HasSeenT et ds,
    currentT  : Long
  ): Opt on[Seq[ mpress on]] = {
    val surfaceArea = query.product match {
      case For Product => Ho SurfaceArea
      case Follow ngProduct => Ho LatestSurfaceArea
      case Subscr bedProduct => Ho Subscr bedSurfaceArea
      case _ => None
    }
    query.seenT et ds.map { seenT et ds =>
      seenT et ds.map { t et d =>
         mpress on(
          t et d = t et d,
           mpress onT   = So (currentT  ),
          surfaceAreas = surfaceArea
        )
      }
    }
  }

  f nal overr de def apply(
     nputs: P pel neResultS deEffect. nputs[P pel neQuery w h HasSeenT et ds, HasMarshall ng]
  ): St ch[Un ] = {
    val currentT   = T  .now. nM ll seconds
    val  mpress ons = bu ldEvents( nputs.query, currentT  )

    St ch.callFuture(
      eventBusPubl s r.publ sh(
        Publ s d mpress onL st(
           nputs.query.getRequ redUser d,
           mpress onL st( mpress ons),
          currentT  
        )
      )
    )
  }

  overr de val alerts = Seq(
    Ho M xerAlertConf g.Bus nessH s.defaultSuccessRateAlert(99.4)
  )
}

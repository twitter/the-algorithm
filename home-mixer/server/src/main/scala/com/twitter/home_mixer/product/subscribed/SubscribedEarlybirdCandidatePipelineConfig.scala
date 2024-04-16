package com.tw ter.ho _m xer.product.subscr bed

 mport com.google. nject. nject
 mport com.tw ter.ho _m xer.funct onal_component.cand date_s ce.Earlyb rdCand dateS ce
 mport com.tw ter.ho _m xer.product.subscr bed.model.Subscr bedQuery
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSSubscr bedUsersFeature
 mport com.tw ter.product_m xer.component_l brary.f lter.T etV s b l yF lter
 mport com.tw ter.product_m xer.component_l brary.gate.NonEmptySeqFeatureGate
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.search.earlyb rd.{thr ftscala => t}
 mport com.tw ter.spam.rtf.thr ftscala.SafetyLevel.T  l neHo Subscr bed
 mport com.tw ter.st ch.t etyp e.{T etyP e => T etyp eSt chCl ent}
 mport com.tw ter.t etyp e.thr ftscala.T etV s b l yPol cy

class Subscr bedEarlyb rdCand dateP pel neConf g @ nject() (
  earlyb rdCand dateS ce: Earlyb rdCand dateS ce,
  t etyP eSt chCl ent: T etyp eSt chCl ent,
  subscr bedEarlyb rdQueryTransfor r: Subscr bedEarlyb rdQueryTransfor r)
    extends Cand dateP pel neConf g[
      Subscr bedQuery,
      t.Earlyb rdRequest,
      t.Thr ftSearchResult,
      T etCand date
    ] {
  overr de val  dent f er: Cand dateP pel ne dent f er =
    Cand dateP pel ne dent f er("Subscr bedEarlyb rd")

  overr de val cand dateS ce: BaseCand dateS ce[t.Earlyb rdRequest, t.Thr ftSearchResult] =
    earlyb rdCand dateS ce

  overr de val gates: Seq[Gate[Subscr bedQuery]] = Seq(
    NonEmptySeqFeatureGate(SGSSubscr bedUsersFeature)
  )

  overr de def f lters: Seq[F lter[Subscr bedQuery, T etCand date]] = Seq(
    new T etV s b l yF lter(
      t etyP eSt chCl ent,
      T etV s b l yPol cy.UserV s ble,
      T  l neHo Subscr bed
    )
  )

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    Subscr bedQuery,
    t.Earlyb rdRequest
  ] = subscr bedEarlyb rdQueryTransfor r

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[t.Thr ftSearchResult]
  ] = Seq(Subscr bedEarlyb rdResponseFeatureTransfor r)

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    t.Thr ftSearchResult,
    T etCand date
  ] = { s ceResult => T etCand date( d = s ceResult. d) }
}

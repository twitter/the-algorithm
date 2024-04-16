package com.tw ter.ho _m xer.product.follow ng

 mport com.tw ter.ho _m xer.cand date_p pel ne.Follow ngEarlyb rdResponseFeatureTransfor r
 mport com.tw ter.ho _m xer.funct onal_component.cand date_s ce.Earlyb rdCand dateS ce
 mport com.tw ter.ho _m xer.product.follow ng.model.Follow ngQuery
 mport com.tw ter.product_m xer.component_l brary.feature_hydrator.query.soc al_graph.SGSFollo dUsersFeature
 mport com.tw ter.product_m xer.component_l brary.gate.NonEmptySeqFeatureGate
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.search.earlyb rd.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class Follow ngEarlyb rdCand dateP pel neConf g @ nject() (
  earlyb rdCand dateS ce: Earlyb rdCand dateS ce,
  follow ngEarlyb rdQueryTransfor r: Follow ngEarlyb rdQueryTransfor r)
    extends Cand dateP pel neConf g[
      Follow ngQuery,
      t.Earlyb rdRequest,
      t.Thr ftSearchResult,
      T etCand date
    ] {

  overr de val  dent f er: Cand dateP pel ne dent f er =
    Cand dateP pel ne dent f er("Follow ngEarlyb rd")

  overr de val cand dateS ce: BaseCand dateS ce[t.Earlyb rdRequest, t.Thr ftSearchResult] =
    earlyb rdCand dateS ce

  overr de val gates: Seq[Gate[Follow ngQuery]] = Seq(
    NonEmptySeqFeatureGate(SGSFollo dUsersFeature)
  )

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    Follow ngQuery,
    t.Earlyb rdRequest
  ] = follow ngEarlyb rdQueryTransfor r

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[t.Thr ftSearchResult]
  ] = Seq(Follow ngEarlyb rdResponseFeatureTransfor r)

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    t.Thr ftSearchResult,
    T etCand date
  ] = { s ceResult => T etCand date( d = s ceResult. d) }
}

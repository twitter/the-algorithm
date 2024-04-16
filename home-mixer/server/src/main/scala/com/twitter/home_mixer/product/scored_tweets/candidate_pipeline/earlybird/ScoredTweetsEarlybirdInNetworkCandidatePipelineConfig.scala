package com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne.earlyb rd

 mport com.tw ter.f nagle.thr ft.Cl ent d
 mport com.tw ter.ho _m xer.funct onal_component.cand date_s ce.Earlyb rdCand dateS ce
 mport com.tw ter.ho _m xer.product.scored_t ets.gate.M nCac dT etsGate
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Cac dScoredT ets
 mport com.tw ter.ho _m xer.product.scored_t ets.query_transfor r.earlyb rd.Earlyb rd nNetworkQueryTransfor r
 mport com.tw ter.ho _m xer.product.scored_t ets.response_transfor r.earlyb rd.ScoredT etsEarlyb rd nNetworkResponseFeatureTransfor r
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.search.earlyb rd.{thr ftscala => eb}
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Cand date P pel ne Conf g that fetc s t ets from t  earlyb rd  nNetwork Cand date S ce
 */
@S ngleton
class ScoredT etsEarlyb rd nNetworkCand dateP pel neConf g @ nject() (
  earlyb rdCand dateS ce: Earlyb rdCand dateS ce,
  cl ent d: Cl ent d)
    extends Cand dateP pel neConf g[
      ScoredT etsQuery,
      eb.Earlyb rdRequest,
      eb.Thr ftSearchResult,
      T etCand date
    ] {

  overr de val  dent f er: Cand dateP pel ne dent f er =
    Cand dateP pel ne dent f er("ScoredT etsEarlyb rd nNetwork")

  overr de val gates: Seq[Gate[ScoredT etsQuery]] = Seq(
    M nCac dT etsGate( dent f er, Cac dScoredT ets.M nCac dT etsParam)
  )

  overr de val cand dateS ce: BaseCand dateS ce[eb.Earlyb rdRequest, eb.Thr ftSearchResult] =
    earlyb rdCand dateS ce

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    ScoredT etsQuery,
    eb.Earlyb rdRequest
  ] = Earlyb rd nNetworkQueryTransfor r( dent f er, cl ent d = So (cl ent d.na ))

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[eb.Thr ftSearchResult]
  ] = Seq(ScoredT etsEarlyb rd nNetworkResponseFeatureTransfor r)

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    eb.Thr ftSearchResult,
    T etCand date
  ] = { s ceResult => T etCand date( d = s ceResult. d) }
}

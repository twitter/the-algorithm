package com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne

 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.FrsSeedUsersQueryFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.gate.M nCac dT etsGate
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Cac dScoredT ets
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Cand dateP pel ne
 mport com.tw ter.ho _m xer.product.scored_t ets.query_transfor r.T  l neRankerFrsQueryTransfor r
 mport com.tw ter.ho _m xer.product.scored_t ets.response_transfor r.ScoredT etsFrsResponseFeatureTransfor r
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.t  l ne_ranker.T  l neRankerRecapCand dateS ce
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseQueryFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.t  l neranker.{thr ftscala => tlr}
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Cand date P pel ne Conf g that takes user recom ndat ons from Follow Recom ndat on Serv ce (FRS)
 * and makes a T  l neRanker->Earlyb rd query for t et cand dates from those users.
 * Add  onally, t  cand date p pel ne hydrates follo dByUser ds so that follo d-by soc al proof
 * can be used.
 */
@S ngleton
class ScoredT etsFrsCand dateP pel neConf g @ nject() (
  t  l neRankerRecapCand dateS ce: T  l neRankerRecapCand dateS ce,
  frsSeedUsersQueryFeatureHydrator: FrsSeedUsersQueryFeatureHydrator)
    extends Cand dateP pel neConf g[
      ScoredT etsQuery,
      tlr.RecapQuery,
      tlr.Cand dateT et,
      T etCand date
    ] {

  overr de val  dent f er: Cand dateP pel ne dent f er =
    Cand dateP pel ne dent f er("ScoredT etsFrs")

  overr de val enabledDec derParam: Opt on[Dec derParam[Boolean]] =
    So (Cand dateP pel ne.EnableFrsParam)

  overr de val gates: Seq[Gate[ScoredT etsQuery]] = Seq(
    M nCac dT etsGate( dent f er, Cac dScoredT ets.M nCac dT etsParam)
  )

  overr de val queryFeatureHydrat on: Seq[
    BaseQueryFeatureHydrator[ScoredT etsQuery, _]
  ] = Seq(frsSeedUsersQueryFeatureHydrator)

  overr de val cand dateS ce: BaseCand dateS ce[tlr.RecapQuery, tlr.Cand dateT et] =
    t  l neRankerRecapCand dateS ce

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    ScoredT etsQuery,
    tlr.RecapQuery
  ] = T  l neRankerFrsQueryTransfor r( dent f er)

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[tlr.Cand dateT et]
  ] = Seq(ScoredT etsFrsResponseFeatureTransfor r)

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    tlr.Cand dateT et,
    T etCand date
  ] = { cand date => T etCand date(cand date.t et.get. d) }
}

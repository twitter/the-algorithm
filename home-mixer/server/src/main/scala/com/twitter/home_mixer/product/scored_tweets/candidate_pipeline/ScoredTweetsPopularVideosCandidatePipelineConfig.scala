package com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne

 mport com.tw ter.explore_ranker.{thr ftscala => ert}
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.T etyp eStat cEnt  esFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.gate.M nCac dT etsGate
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Cac dScoredT ets
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Cand dateP pel ne
 mport com.tw ter.ho _m xer.product.scored_t ets.response_transfor r.ScoredT etsPopularV deosResponseFeatureTransfor r
 mport com.tw ter.ho _m xer.ut l.Cac dScoredT ets lper
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.explore_ranker.ExploreRanker m rs veRecsCand dateS ce
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.request.Cl entContextMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ScoredT etsPopularV deosCand dateP pel neConf g @ nject() (
  exploreRankerCand dateS ce: ExploreRanker m rs veRecsCand dateS ce,
  t etyp eStat cEnt  esFeatureHydrator: T etyp eStat cEnt  esFeatureHydrator)
    extends Cand dateP pel neConf g[
      ScoredT etsQuery,
      ert.ExploreRankerRequest,
      ert.ExploreT etRecom ndat on,
      T etCand date
    ] {

  overr de val  dent f er: Cand dateP pel ne dent f er =
    Cand dateP pel ne dent f er("ScoredT etsPopularV deos")

  pr vate val MaxT etsToFetch = 40

  overr de val enabledDec derParam: Opt on[Dec derParam[Boolean]] =
    So (Cand dateP pel ne.EnablePopularV deosParam)

  overr de val gates: Seq[Gate[ScoredT etsQuery]] = Seq(
    M nCac dT etsGate( dent f er, Cac dScoredT ets.M nCac dT etsParam)
  )

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    ScoredT etsQuery,
    ert.ExploreRankerRequest
  ] = { query =>
    val excludedT et ds = query.features.map(
      Cac dScoredT ets lper.t et mpress onsAndCac dScoredT ets(_,  dent f er))

    ert.ExploreRankerRequest(
      cl entContext = Cl entContextMarshaller(query.cl entContext),
      product = ert.Product.Ho T  l neV deo nl ne,
      productContext = So (
        ert.ProductContext.Ho T  l neV deo nl ne(ert.Ho T  l neV deo nl ne(excludedT et ds))),
      maxResults = So (MaxT etsToFetch)
    )
  }

  overr de def cand dateS ce: BaseCand dateS ce[
    ert.ExploreRankerRequest,
    ert.ExploreT etRecom ndat on
  ] = exploreRankerCand dateS ce

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[ert.ExploreT etRecom ndat on]
  ] = Seq(ScoredT etsPopularV deosResponseFeatureTransfor r)

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    ert.ExploreT etRecom ndat on,
    T etCand date
  ] = { s ceResult => T etCand date( d = s ceResult.t et d) }

  overr de val preF lterFeatureHydrat onPhase1: Seq[
    BaseCand dateFeatureHydrator[ScoredT etsQuery, T etCand date, _]
  ] = Seq(t etyp eStat cEnt  esFeatureHydrator)
}

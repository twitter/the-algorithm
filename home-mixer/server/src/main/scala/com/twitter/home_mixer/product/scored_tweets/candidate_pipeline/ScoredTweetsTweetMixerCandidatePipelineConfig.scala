package com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne

 mport com.tw ter.t et_m xer.{thr ftscala => t}
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.T etyp eStat cEnt  esFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.gate.M nCac dT etsGate
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Cac dScoredT ets
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Cand dateP pel ne
 mport com.tw ter.ho _m xer.product.scored_t ets.response_transfor r.ScoredT etsT etM xerResponseFeatureTransfor r
 mport com.tw ter.ho _m xer.ut l.Cac dScoredT ets lper
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.t et_m xer.T etM xerCand dateS ce
 mport com.tw ter.product_m xer.component_l brary.f lter.Pred cateFeatureF lter
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.BaseCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.marshaller.request.Cl entContextMarshaller
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam

 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Cand date P pel ne Conf g that fetc s t ets from T etM xer.
 */
@S ngleton
class ScoredT etsT etM xerCand dateP pel neConf g @ nject() (
  t etM xerCand dateS ce: T etM xerCand dateS ce,
  t etyp eStat cEnt  esFeatureHydrator: T etyp eStat cEnt  esFeatureHydrator)
    extends Cand dateP pel neConf g[
      ScoredT etsQuery,
      t.T etM xerRequest,
      t.T etResult,
      T etCand date
    ] {

  overr de val  dent f er: Cand dateP pel ne dent f er =
    Cand dateP pel ne dent f er("ScoredT etsT etM xer")

  val HasAuthorF lter d = "HasAuthor"

  overr de val enabledDec derParam: Opt on[Dec derParam[Boolean]] =
    So (Cand dateP pel ne.EnableT etM xerParam)

  overr de val gates: Seq[Gate[ScoredT etsQuery]] = Seq(
    M nCac dT etsGate( dent f er, Cac dScoredT ets.M nCac dT etsParam),
  )

  overr de val cand dateS ce: BaseCand dateS ce[t.T etM xerRequest, t.T etResult] =
    t etM xerCand dateS ce

  pr vate val MaxT etsToFetch = 400

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    ScoredT etsQuery,
    t.T etM xerRequest
  ] = { query =>
    val maxCount = (query.getQual yFactorCurrentValue( dent f er) * MaxT etsToFetch).to nt

    val excludedT et ds = query.features.map(
      Cac dScoredT ets lper.t et mpress onsAndCac dScoredT ets(_,  dent f er))

    t.T etM xerRequest(
      cl entContext = Cl entContextMarshaller(query.cl entContext),
      product = t.Product.Ho Recom ndedT ets,
      productContext = So (
        t.ProductContext.Ho Recom ndedT etsProductContext(
          t.Ho Recom ndedT etsProductContext(excludedT et ds = excludedT et ds.map(_.toSet)))),
      maxResults = So (maxCount)
    )
  }

  overr de val preF lterFeatureHydrat onPhase1: Seq[
    BaseCand dateFeatureHydrator[ScoredT etsQuery, T etCand date, _]
  ] = Seq(t etyp eStat cEnt  esFeatureHydrator)

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[t.T etResult]
  ] = Seq(ScoredT etsT etM xerResponseFeatureTransfor r)

  overr de val f lters: Seq[F lter[ScoredT etsQuery, T etCand date]] = Seq(
    Pred cateFeatureF lter.fromPred cate(
      F lter dent f er(HasAuthorF lter d),
      shouldKeepCand date = _.getOrElse(Author dFeature, None). sDef ned
    )
  )

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[
    t.T etResult,
    T etCand date
  ] = { s ceResult => T etCand date( d = s ceResult.t et d) }
}

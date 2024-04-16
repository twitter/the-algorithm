package com.tw ter.ho _m xer.product.scored_t ets.cand date_p pel ne

 mport com.tw ter.ho _m xer.funct onal_component.f lter.ReplyF lter
 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.T  l neServ ceT etsFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator.T etyp eStat cEnt  esFeatureHydrator
 mport com.tw ter.ho _m xer.product.scored_t ets.gate.M nCac dT etsGate
 mport com.tw ter.ho _m xer.product.scored_t ets.gate.M nT  S nceLastRequestGate
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Cac dScoredT ets
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.Cand dateP pel ne
 mport com.tw ter.ho _m xer.product.scored_t ets.param.ScoredT etsParam.EnableBackf llCand dateP pel neParam
 mport com.tw ter.ho _m xer.product.scored_t ets.response_transfor r.ScoredT etsBackf llResponseFeatureTransfor r
 mport com.tw ter.product_m xer.component_l brary.f lter.Pred cateFeatureF lter
 mport com.tw ter.product_m xer.component_l brary.gate.NonEmptySeqFeatureGate
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.PassthroughCand dateS ce
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BaseCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.funct onal_component.f lter.F lter
 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateFeatureTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neQueryTransfor r
 mport com.tw ter.product_m xer.core.funct onal_component.transfor r.Cand dateP pel neResultsTransfor r
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.product_m xer.core.model.common. dent f er.F lter dent f er
 mport com.tw ter.product_m xer.core.p pel ne.cand date.Cand dateP pel neConf g
 mport com.tw ter.t  l nes.conf gap .FSParam
 mport com.tw ter.t  l nes.conf gap .dec der.Dec derParam
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class ScoredT etsBackf llCand dateP pel neConf g @ nject() (
  t etyp eStat cEnt  esHydrator: T etyp eStat cEnt  esFeatureHydrator)
    extends Cand dateP pel neConf g[
      ScoredT etsQuery,
      ScoredT etsQuery,
      Long,
      T etCand date
    ] {

  overr de val  dent f er: Cand dateP pel ne dent f er =
    Cand dateP pel ne dent f er("ScoredT etsBackf ll")

  pr vate val HasAuthorF lter d = "HasAuthor"

  overr de val enabledDec derParam: Opt on[Dec derParam[Boolean]] =
    So (Cand dateP pel ne.EnableBackf llParam)

  overr de val supportedCl entParam: Opt on[FSParam[Boolean]] =
    So (EnableBackf llCand dateP pel neParam)

  overr de val gates: Seq[Gate[ScoredT etsQuery]] =
    Seq(
      M nT  S nceLastRequestGate,
      NonEmptySeqFeatureGate(T  l neServ ceT etsFeature),
      M nCac dT etsGate( dent f er, Cac dScoredT ets.M nCac dT etsParam)
    )

  overr de val queryTransfor r: Cand dateP pel neQueryTransfor r[
    ScoredT etsQuery,
    ScoredT etsQuery
  ] =  dent y

  overr de def cand dateS ce: Cand dateS ce[ScoredT etsQuery, Long] =
    PassthroughCand dateS ce(
       dent f er = Cand dateS ce dent f er("ScoredT etsBackf ll"),
      cand dateExtractor = { query =>
        query.features.map(_.getOrElse(T  l neServ ceT etsFeature, Seq.empty)).toSeq.flatten
      }
    )

  overr de val featuresFromCand dateS ceTransfor rs: Seq[
    Cand dateFeatureTransfor r[Long]
  ] = Seq(ScoredT etsBackf llResponseFeatureTransfor r)

  overr de val resultTransfor r: Cand dateP pel neResultsTransfor r[Long, T etCand date] = {
    s ceResult => T etCand date( d = s ceResult)
  }

  overr de val preF lterFeatureHydrat onPhase1: Seq[
    BaseCand dateFeatureHydrator[ScoredT etsQuery, T etCand date, _]
  ] = Seq(t etyp eStat cEnt  esHydrator)

  overr de val f lters: Seq[F lter[ScoredT etsQuery, T etCand date]] = Seq(
    ReplyF lter,
    Pred cateFeatureF lter.fromPred cate(
      F lter dent f er(HasAuthorF lter d),
      shouldKeepCand date = _.getOrElse(Author dFeature, None). sDef ned
    )
  )
}

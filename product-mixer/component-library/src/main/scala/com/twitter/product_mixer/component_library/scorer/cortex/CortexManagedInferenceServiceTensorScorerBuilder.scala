package com.tw ter.product_m xer.component_l brary.scorer.cortex

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.component_l brary.scorer.common.MLModel nferenceCl ent
 mport com.tw ter.product_m xer.component_l brary.scorer.tensorbu lder.Model nferRequestBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class CortexManaged nferenceServ ceTensorScorerBu lder @ nject() (
  statsRece ver: StatsRece ver) {

  /**
   * Bu lds a conf gurable Scorer to call  nto y  des red Cortex Managed ML Model Serv ce.
   *
   *  f y  serv ce does not b nd an Http.Cl ent  mple ntat on, add
   * [[com.tw ter.product_m xer.component_l brary.module.http.F nagleHttpCl entModule]]
   * to y  server module l st
   *
   * @param scorer dent f er        Un que  dent f er for t  scorer
   * @param resultFeatureExtractors T  result features an t  r tensor extractors for each cand date.
   * @tparam Query Type of p pel ne query.
   * @tparam Cand date Type of cand dates to score.
   * @tparam QueryFeatures type of t  query level features consu d by t  scorer.
   * @tparam Cand dateFeatures type of t  cand date level features consu d by t  scorer.
   */
  def bu ld[Query <: P pel neQuery, Cand date <: Un versalNoun[Any]](
    scorer dent f er: Scorer dent f er,
    model nferRequestBu lder: Model nferRequestBu lder[
      Query,
      Cand date
    ],
    resultFeatureExtractors: Seq[FeatureW hExtractor[Query, Cand date, _]],
    cl ent: MLModel nferenceCl ent
  ): Scorer[Query, Cand date] =
    new CortexManaged nferenceServ ceTensorScorer(
      scorer dent f er,
      model nferRequestBu lder,
      resultFeatureExtractors,
      cl ent,
      statsRece ver.scope(scorer dent f er.na )
    )
}

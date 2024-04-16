package com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.t et_tlx

 mport com.tw ter.ml.featurestore.t  l nes.thr ftscala.T  l neScorerScoreV ew
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.component_l brary.scorer.t et_tlx.TLXScore
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.generated.cl ent.ml.featureStore.T  l neScorerT etScoresV1Cl entColumn
 mport com.tw ter.t  l nescorer.thr ftscala.v1
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Hydrate T et Scores v a T  l ne Scorer (TLX)
 *
 * Note that t   s t  [[Cand dateFeatureHydrator]] vers on of
 * [[com.tw ter.product_m xer.component_l brary.scorer.t et_tlx.T etTLXStratoScorer]]
 */
@S ngleton
class T etTLXScoreCand dateFeatureHydrator @ nject() (
  column: T  l neScorerT etScoresV1Cl entColumn)
    extends Cand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("T etTLXScore")

  overr de val features: Set[Feature[_, _]] = Set(TLXScore)

  pr vate val NoScoreMap = FeatureMapBu lder()
    .add(TLXScore, None)
    .bu ld()

  overr de def apply(
    query: P pel neQuery,
    cand date: T etCand date,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = {
    query.getOpt onalUser d match {
      case So (user d) =>
        column.fetc r
          .fetch(cand date. d, T  l neScorerScoreV ew(So (user d)))
          .map(scoredT et =>
            scoredT et.v match {
              case So (v1.ScoredT et(So (_), score, _, _)) =>
                FeatureMapBu lder()
                  .add(TLXScore, score)
                  .bu ld()
              case _ => throw new Except on(s" nval d response from TLX: ${scoredT et.v}")
            })
      case _ =>
        St ch.value(NoScoreMap)
    }
  }
}

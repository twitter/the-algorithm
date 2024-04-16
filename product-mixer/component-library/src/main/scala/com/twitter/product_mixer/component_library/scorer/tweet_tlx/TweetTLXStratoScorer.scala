package com.tw ter.product_m xer.component_l brary.scorer.t et_tlx

 mport com.tw ter.ml.featurestore.t  l nes.thr ftscala.T  l neScorerScoreV ew
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.strato.catalog.Fetch.Result
 mport com.tw ter.strato.generated.cl ent.ml.featureStore.T  l neScorerT etScoresV1Cl entColumn
 mport com.tw ter.t  l nescorer.thr ftscala.v1
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Score T ets v a T  l ne Scorer (TLX) Strato AP 
 *
 * @note T  results  n an add  onal hop through Strato Server
 * @note T   s t  [[Scorer]] vers on of
 * [[com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.t et_tlx.T etTLXScoreCand dateFeatureHydrator]]
 */
@S ngleton
class T etTLXStratoScorer @ nject() (column: T  l neScorerT etScoresV1Cl entColumn)
    extends Scorer[P pel neQuery, T etCand date] {

  overr de val  dent f er: Scorer dent f er = Scorer dent f er("T etTLX")

  overr de val features: Set[Feature[_, _]] = Set(TLXScore)

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = query.getOpt onalUser d match {
    case So (user d) => getScoredT etsFromTLX(user d, cand dates.map(_.cand date))
    case _ =>
      val defaultFeatureMap = FeatureMapBu lder().add(TLXScore, None).bu ld()
      St ch.value(cand dates.map(_ => defaultFeatureMap))
  }

  def getScoredT etsFromTLX(
    user d: Long,
    t etCand dates: Seq[T etCand date]
  ): St ch[Seq[FeatureMap]] = St ch.collect(t etCand dates.map { cand date =>
    column.fetc r
      .fetch(cand date. d, T  l neScorerScoreV ew(So (user d)))
      .map {
        case Result(So (v1.ScoredT et(_, score, _, _)), _) =>
          FeatureMapBu lder()
            .add(TLXScore, score)
            .bu ld()
        case fetchResult => throw new Except on(s" nval d response from TLX: ${fetchResult.v}")
      }
  })
}

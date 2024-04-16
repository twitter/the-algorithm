package com.tw ter.ho _m xer.product.scored_t ets.scorer

 mport com.tw ter.ho _m xer.model.Ho Features.ScoreFeature
 mport com.tw ter.ho _m xer.product.scored_t ets.model.ScoredT etsQuery
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.st ch.St ch

/**
 * Apply var ous  ur st cs to t  model score
 */
object  ur st cScorer extends Scorer[ScoredT etsQuery, T etCand date] {

  overr de val  dent f er: Scorer dent f er = Scorer dent f er(" ur st c")

  overr de val features: Set[Feature[_, _]] = Set(ScoreFeature)

  overr de def apply(
    query: ScoredT etsQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    val rescorers = Seq(
      RescoreOutOfNetwork,
      RescoreRepl es,
      RescoreBlueVer f ed,
      RescoreCreators,
      RescoreMTLNormal zat on,
      RescoreAuthorD vers y(AuthorD vers yD scountProv der(cand dates)),
      RescoreFeedbackFat gue(query)
    )

    val updatedScores = cand dates.map { cand date =>
      val score = cand date.features.getOrElse(ScoreFeature, None)
      val scaleFactor = rescorers.map(_(query, cand date)).product
      val updatedScore = score.map(_ * scaleFactor)
      FeatureMapBu lder().add(ScoreFeature, updatedScore).bu ld()
    }

    St ch.value(updatedScores)
  }
}

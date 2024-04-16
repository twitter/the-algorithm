package com.tw ter.product_m xer.component_l brary.scorer.t et_tlx

 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.scorer.Scorer
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.Scorer dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nescorer.thr ftscala.v1
 mport com.tw ter.t  l nescorer.{thr ftscala => t}
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * @note T  Feature  s shared w h
 * [[com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.t et_tlx.T etTLXScoreCand dateFeatureHydrator]]
 * and
 * [[com.tw ter.product_m xer.component_l brary.scorer.t et_tlx.T etTLXStratoScorer]]
 * as t  t se components should not be used at t  sa  t   by t  sa  Product
 */
object TLXScore extends FeatureW hDefaultOnFa lure[T etCand date, Opt on[Double]] {
  overr de val defaultValue = None
}

/**
 * Score T ets v a T  l ne Scorer (TLX) Thr ft AP 
 *
 * @note T   s t  [[Scorer]] vers on of
 * [[com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.t et_tlx.T etTLXScoreCand dateFeatureHydrator]]
 */
@S ngleton
class T etTLXThr ftScorer @ nject() (t  l neScorerCl ent: t.T  l neScorer. thodPerEndpo nt)
    extends Scorer[P pel neQuery, T etCand date] {

  overr de val  dent f er: Scorer dent f er = Scorer dent f er("TLX")

  overr de val features: Set[Feature[_, _]] = Set(TLXScore)

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    val user d = query.getOpt onalUser d
    val t etScor ngQuery = v1.T etScor ngQuery(
      pred ct onP pel ne = v1.Pred ct onP pel ne.Recap,
      t et ds = cand dates.map(_.cand date. d))

    val t etScor ngRequest = t.T etScor ngRequest.V1(
      v1.T etScor ngRequest(
        t etScor ngRequestContext = So (v1.T etScor ngRequestContext(user d = user d)),
        t etScor ngQuer es = So (Seq(t etScor ngQuery)),
        retr eveFeatures = So (false)
      ))

    St ch.callFuture(t  l neScorerCl ent.getT etScores(t etScor ngRequest)).map {
      case t.T etScor ngResponse.V1(response) =>
        val t et dScoreMap = response.t etScor ngResults
          .flatMap {
            _. adOpt on.map {
              _.scoredT ets.flatMap(t et => t et.t et d.map(_ -> t et.score))
            }
          }.getOrElse(Seq.empty).toMap

        cand dates.map { cand dateW hFeatures =>
          val score = t et dScoreMap.getOrElse(cand dateW hFeatures.cand date. d, None)
          FeatureMapBu lder()
            .add(TLXScore, score)
            .bu ld()

        }
      case t.T etScor ngResponse.UnknownUn onF eld(f eld) =>
        throw new UnsupportedOperat onExcept on(s"Unknown response type: ${f eld.f eld.na }")
    }
  }
}

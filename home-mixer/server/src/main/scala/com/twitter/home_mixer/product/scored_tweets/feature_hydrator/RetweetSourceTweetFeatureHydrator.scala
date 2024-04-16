package com.tw ter.ho _m xer.product.scored_t ets.feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features._
 mport com.tw ter.product_m xer.component_l brary.cand date_s ce.t  l ne_ranker.T  l neRanker nNetworkS ceT etsByT et dMapFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.search.common.features.thr ftscala.Thr ftT etFeatures
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l neranker.thr ftscala.Cand dateT et

object S ceT etEarlyb rdFeature extends Feature[T etCand date, Opt on[Thr ftT etFeatures]]

/**
 * Feature Hydrator that bulk hydrates s ce t ets' features to ret et cand dates
 */
object Ret etS ceT etFeatureHydrator
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er =
    FeatureHydrator dent f er("Ret etS ceT et")

  overr de val features: Set[Feature[_, _]] = Set(
    S ceT etEarlyb rdFeature,
  )

  pr vate val DefaultFeatureMap = FeatureMapBu lder()
    .add(S ceT etEarlyb rdFeature, None)
    .bu ld()

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    val s ceT etsByT et d: Opt on[Map[Long, Cand dateT et]] = {
      query.features.map(
        _.getOrElse(
          T  l neRanker nNetworkS ceT etsByT et dMapFeature,
          Map.empty[Long, Cand dateT et]))
    }

    /**
     * Return DefaultFeatureMap (no-op to cand date) w n    s unfeas ble to hydrate t 
     * s ce t et's feature to t  current cand date: early b rd does not return s ce
     * t ets  nfo / cand date  s not a ret et / s ceT et d  s not found
     */
    St ch.value {
       f (s ceT etsByT et d.ex sts(_.nonEmpty)) {
        cand dates.map { cand date =>
          val cand date sRet et = cand date.features.getOrElse( sRet etFeature, false)
          val s ceT et d = cand date.features.getOrElse(S ceT et dFeature, None)
           f (!cand date sRet et || s ceT et d. sEmpty) {
            DefaultFeatureMap
          } else {
            val s ceT et = s ceT etsByT et d.flatMap(_.get(s ceT et d.get))
             f (s ceT et.nonEmpty) {
              val s ce = s ceT et.get
              FeatureMapBu lder()
                .add(S ceT etEarlyb rdFeature, s ce.features)
                .bu ld()
            } else {
              DefaultFeatureMap
            }
          }
        }
      } else {
        cand dates.map(_ => DefaultFeatureMap)
      }
    }
  }
}

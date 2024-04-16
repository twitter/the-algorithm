package com.tw ter.ho _m xer.product.for_ .feature_hydrator

 mport com.tw ter.ho _m xer.model.Ho Features.Author dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Conversat onModuleFocalT et dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Conversat onModule dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.FocalT etAuthor dFeature
 mport com.tw ter.ho _m xer.model.Ho Features.FocalT et nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features.FocalT etRealNa sFeature
 mport com.tw ter.ho _m xer.model.Ho Features.FocalT etScreenNa sFeature
 mport com.tw ter.ho _m xer.model.Ho Features. nNetworkFeature
 mport com.tw ter.ho _m xer.model.Ho Features.RealNa sFeature
 mport com.tw ter.ho _m xer.model.Ho Features.ScreenNa sFeature
 mport com.tw ter.product_m xer.component_l brary.model.cand date.T etCand date
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.BulkCand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common.Cand dateW hFeatures
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * Soc al context for convo modules  s hydrated on t  root T et but needs  nfo about t  focal
 * T et (e.g. author) to render t  banner. T  hydrator cop es focal T et data  nto t  root.
 */
@S ngleton
class FocalT etFeatureHydrator @ nject() ()
    extends BulkCand dateFeatureHydrator[P pel neQuery, T etCand date] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er("FocalT et")

  overr de val features: Set[Feature[_, _]] = Set(
    FocalT etAuthor dFeature,
    FocalT et nNetworkFeature,
    FocalT etRealNa sFeature,
    FocalT etScreenNa sFeature
  )

  pr vate val DefaultFeatureMap = FeatureMapBu lder()
    .add(FocalT etAuthor dFeature, None)
    .add(FocalT et nNetworkFeature, None)
    .add(FocalT etRealNa sFeature, None)
    .add(FocalT etScreenNa sFeature, None)
    .bu ld()

  overr de def apply(
    query: P pel neQuery,
    cand dates: Seq[Cand dateW hFeatures[T etCand date]]
  ): St ch[Seq[FeatureMap]] = {
    // Bu ld a map of all t  focal t ets to t  r correspond ng features
    val focalT et dToFeatureMap = cand dates.flatMap { cand date =>
      val focalT et d = cand date.features.getOrElse(Conversat onModuleFocalT et dFeature, None)
       f (focalT et d.conta ns(cand date.cand date. d)) {
        So (cand date.cand date. d -> cand date.features)
      } else None
    }.toMap

    val updatedFeatureMap = cand dates.map { cand date =>
      val focalT et d = cand date.features.getOrElse(Conversat onModuleFocalT et dFeature, None)
      val conversat on d = cand date.features.getOrElse(Conversat onModule dFeature, None)

      // C ck  f t  cand date  s a root t et and ensure  s focal t et's features are ava lable
       f (conversat on d.conta ns(cand date.cand date. d)
        && focalT et d.ex sts(focalT et dToFeatureMap.conta ns)) {
        val featureMap = focalT et dToFeatureMap.get(focalT et d.get).get
        FeatureMapBu lder()
          .add(FocalT etAuthor dFeature, featureMap.getOrElse(Author dFeature, None))
          .add(FocalT et nNetworkFeature, So (featureMap.getOrElse( nNetworkFeature, true)))
          .add(
            FocalT etRealNa sFeature,
            So (featureMap.getOrElse(RealNa sFeature, Map.empty[Long, Str ng])))
          .add(
            FocalT etScreenNa sFeature,
            So (featureMap.getOrElse(ScreenNa sFeature, Map.empty[Long, Str ng])))
          .bu ld()
      } else DefaultFeatureMap
    }

    St ch.value(updatedFeatureMap)
  }
}

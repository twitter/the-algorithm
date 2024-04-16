package com.tw ter.product_m xer.component_l brary.feature_hydrator.cand date.ads

 mport com.tw ter.adserver.{thr ftscala => ad}
 mport com.tw ter.product_m xer.component_l brary.model.cand date.ads.AdsCand date
 mport com.tw ter.product_m xer.component_l brary.model.query.ads.AdsQuery
 mport com.tw ter.product_m xer.core.feature.Feature
 mport com.tw ter.product_m xer.core.feature.FeatureW hDefaultOnFa lure
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMapBu lder
 mport com.tw ter.product_m xer.core.funct onal_component.feature_hydrator.Cand dateFeatureHydrator
 mport com.tw ter.product_m xer.core.model.common. dent f er.FeatureHydrator dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

 mport javax. nject. nject
 mport javax. nject.S ngleton

object Advert serBrandSafetySett ngsFeature
    extends FeatureW hDefaultOnFa lure[AdsCand date, Opt on[ad.Advert serBrandSafetySett ngs]] {
  overr de val defaultValue = None
}

@S ngleton
case class Advert serBrandSafetySett ngsFeatureHydrator[
  Query <: P pel neQuery w h AdsQuery,
  Cand date <: AdsCand date] @ nject() (
  advert serBrandSafetySett ngsStore: ReadableStore[Long, ad.Advert serBrandSafetySett ngs])
    extends Cand dateFeatureHydrator[Query, Cand date] {

  overr de val  dent f er: FeatureHydrator dent f er = FeatureHydrator dent f er(
    "Advert serBrandSafetySett ngs")

  overr de val features: Set[Feature[_, _]] = Set(Advert serBrandSafetySett ngsFeature)

  overr de def apply(
    query: Query,
    cand date: Cand date,
    ex st ngFeatures: FeatureMap
  ): St ch[FeatureMap] = {

    val featureMapFuture: Future[FeatureMap] = advert serBrandSafetySett ngsStore
      .get(cand date.ad mpress on.advert ser d)
      .map { advert serBrandSafetySett ngsOpt =>
        FeatureMapBu lder()
          .add(Advert serBrandSafetySett ngsFeature, advert serBrandSafetySett ngsOpt).bu ld()
      }

    St ch.callFuture(featureMapFuture)
  }
}

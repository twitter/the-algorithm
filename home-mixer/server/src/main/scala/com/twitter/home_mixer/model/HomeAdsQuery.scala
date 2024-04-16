package com.tw ter.ho _m xer.model

 mport com.tw ter.adserver.thr ftscala.RequestTr ggerType
 mport com.tw ter.ho _m xer.model.Ho Features.Get n  alFeature
 mport com.tw ter.ho _m xer.model.Ho Features.GetNe rFeature
 mport com.tw ter.ho _m xer.model.Ho Features.GetOlderFeature
 mport com.tw ter.ho _m xer.model.Ho Features.Poll ngFeature
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.component_l brary.model.query.ads.AdsQuery
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * T se are for feeds needed for ads only.
 */
tra  Ho AdsQuery
    extends AdsQuery
    w h P pel neQuery
    w h HasDev ceContext
    w h HasP pel neCursor[UrtOrderedCursor] {

  pr vate val featureToRequestTr ggerType = Seq(
    (Get n  alFeature, RequestTr ggerType. n  al),
    (GetNe rFeature, RequestTr ggerType.Scroll),
    (GetOlderFeature, RequestTr ggerType.Scroll),
    (Poll ngFeature, RequestTr ggerType.AutoRefresh)
  )

  overr de val autoplayEnabled: Opt on[Boolean] = dev ceContext.flatMap(_.autoplayEnabled)

  overr de def requestTr ggerType: Opt on[RequestTr ggerType] = {
    val features = t .features.getOrElse(FeatureMap.empty)

    featureToRequestTr ggerType.collectF rst {
      case (feature, requestType)  f features.get(feature) => So (requestType)
    }.flatten
  }

  overr de val d sableNsfwAvo dance: Opt on[Boolean] = So (true)
}

package com.tw ter.ho _m xer.product.l st_t ets.model

 mport com.tw ter.adserver.thr ftscala.Ho T  l neType
 mport com.tw ter.adserver.thr ftscala.T  l neRequestParams
 mport com.tw ter.dspb dder.commons.{thr ftscala => dsp}
 mport com.tw ter.ho _m xer.model.Ho AdsQuery
 mport com.tw ter.ho _m xer.model.request.Dev ceContext
 mport com.tw ter.ho _m xer.model.request.HasL st d
 mport com.tw ter.ho _m xer.model.request.L stT etsProduct
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.request._
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Params

case class L stT etsQuery(
  overr de val params: Params,
  overr de val cl entContext: Cl entContext,
  overr de val p pel neCursor: Opt on[UrtOrderedCursor],
  overr de val requestedMaxResults: Opt on[ nt],
  overr de val debugOpt ons: Opt on[DebugOpt ons],
  overr de val features: Opt on[FeatureMap],
  overr de val l st d: Long,
  overr de val dev ceContext: Opt on[Dev ceContext],
  overr de val dspCl entContext: Opt on[dsp.DspCl entContext])
    extends P pel neQuery
    w h HasP pel neCursor[UrtOrderedCursor]
    w h HasL st d
    w h Ho AdsQuery {
  overr de val product: Product = L stT etsProduct

  overr de def w hFeatureMap(features: FeatureMap): L stT etsQuery =
    copy(features = So (features))

  overr de val t  l neRequestParams: Opt on[T  l neRequestParams] =
    So (T  l neRequestParams(ho T  l neType = So (Ho T  l neType.Ho Latest)))
}

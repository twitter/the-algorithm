package com.tw ter.ho _m xer.product.subscr bed.model

 mport com.tw ter.ho _m xer.model.request.Dev ceContext
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.model.request.HasSeenT et ds
 mport com.tw ter.ho _m xer.model.request.Subscr bedProduct
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.request._
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Params

case class Subscr bedQuery(
  overr de val params: Params,
  overr de val cl entContext: Cl entContext,
  overr de val p pel neCursor: Opt on[UrtOrderedCursor],
  overr de val requestedMaxResults: Opt on[ nt],
  overr de val debugOpt ons: Opt on[DebugOpt ons],
  overr de val features: Opt on[FeatureMap],
  overr de val dev ceContext: Opt on[Dev ceContext],
  overr de val seenT et ds: Opt on[Seq[Long]])
    extends P pel neQuery
    w h HasP pel neCursor[UrtOrderedCursor]
    w h HasDev ceContext
    w h HasSeenT et ds {
  overr de val product: Product = Subscr bedProduct

  overr de def w hFeatureMap(features: FeatureMap): Subscr bedQuery =
    copy(features = So (features))
}

package com.tw ter.ho _m xer.product.scored_t ets.model

 mport com.tw ter.ho _m xer.model.request.Dev ceContext
 mport com.tw ter.ho _m xer.model.request.HasDev ceContext
 mport com.tw ter.ho _m xer.model.request.HasSeenT et ds
 mport com.tw ter.ho _m xer.model.request.ScoredT etsProduct
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtOrderedCursor
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.request._
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.qual y_factor.HasQual yFactorStatus
 mport com.tw ter.product_m xer.core.qual y_factor.Qual yFactorStatus
 mport com.tw ter.t  l nes.conf gap .Params

case class ScoredT etsQuery(
  overr de val params: Params,
  overr de val cl entContext: Cl entContext,
  overr de val p pel neCursor: Opt on[UrtOrderedCursor],
  overr de val requestedMaxResults: Opt on[ nt],
  overr de val debugOpt ons: Opt on[DebugOpt ons],
  overr de val features: Opt on[FeatureMap],
  overr de val dev ceContext: Opt on[Dev ceContext],
  overr de val seenT et ds: Opt on[Seq[Long]],
  overr de val qual yFactorStatus: Opt on[Qual yFactorStatus])
    extends P pel neQuery
    w h HasP pel neCursor[UrtOrderedCursor]
    w h HasDev ceContext
    w h HasSeenT et ds
    w h HasQual yFactorStatus {
  overr de val product: Product = ScoredT etsProduct

  overr de def w hFeatureMap(features: FeatureMap): ScoredT etsQuery =
    copy(features = So (features))

  overr de def w hQual yFactorStatus(
    qual yFactorStatus: Qual yFactorStatus
  ): ScoredT etsQuery = copy(qual yFactorStatus = So (qual yFactorStatus))
}

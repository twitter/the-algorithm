package com.tw ter.ho _m xer.product.l st_recom nded_users.model

 mport com.tw ter.ho _m xer.model.request.HasL st d
 mport com.tw ter.ho _m xer.model.request.L stRecom ndedUsersProduct
 mport com.tw ter.product_m xer.component_l brary.model.cursor.UrtUnorderedExclude dsCursor
 mport com.tw ter.product_m xer.core.feature.featuremap.FeatureMap
 mport com.tw ter.product_m xer.core.model.marshall ng.request._
 mport com.tw ter.product_m xer.core.p pel ne.HasP pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Params

case class L stRecom ndedUsersQuery(
  overr de val l st d: Long,
  overr de val params: Params,
  overr de val cl entContext: Cl entContext,
  overr de val p pel neCursor: Opt on[UrtUnorderedExclude dsCursor],
  overr de val requestedMaxResults: Opt on[ nt],
  overr de val debugOpt ons: Opt on[DebugOpt ons],
  overr de val features: Opt on[FeatureMap],
  selectedUser ds: Opt on[Seq[Long]],
  excludedUser ds: Opt on[Seq[Long]],
  l stNa : Opt on[Str ng])
    extends P pel neQuery
    w h HasP pel neCursor[UrtUnorderedExclude dsCursor]
    w h HasL st d {

  overr de val product: Product = L stRecom ndedUsersProduct

  overr de def w hFeatureMap(features: FeatureMap): L stRecom ndedUsersQuery =
    copy(features = So (features))
}

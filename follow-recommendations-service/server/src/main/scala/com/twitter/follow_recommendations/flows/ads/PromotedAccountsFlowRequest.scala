package com.tw ter.follow_recom ndat ons.flows.ads
 mport com.tw ter.follow_recom ndat ons.common.cl ents.adserver.AdRequest
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.HasExcludedUser ds
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Params

case class PromotedAccountsFlowRequest(
  overr de val cl entContext: Cl entContext,
  overr de val params: Params,
  d splayLocat on: D splayLocat on,
  prof le d: Opt on[Long],
  // note   also add user d and prof le d to excludeUser ds
  exclude ds: Seq[Long])
    extends HasParams
    w h HasCl entContext
    w h HasExcludedUser ds
    w h HasD splayLocat on {
  def toAdsRequest(fetchProduct onPromotedAccounts: Boolean): AdRequest = {
    AdRequest(
      cl entContext = cl entContext,
      d splayLocat on = d splayLocat on,
       sTest = So (!fetchProduct onPromotedAccounts),
      prof leUser d = prof le d
    )
  }
  overr de val excludedUser ds: Seq[Long] = {
    exclude ds ++ cl entContext.user d.toSeq ++ prof le d.toSeq
  }
}

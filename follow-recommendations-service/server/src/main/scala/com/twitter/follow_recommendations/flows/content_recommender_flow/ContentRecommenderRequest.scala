package com.tw ter.follow_recom ndat ons.flows.content_recom nder_flow

 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.follow_recom ndat ons.common.models.DebugOpt ons
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.GeohashAndCountryCode
 mport com.tw ter.follow_recom ndat ons.common.models.HasDebugOpt ons
 mport com.tw ter.follow_recom ndat ons.common.models.HasD splayLocat on
 mport com.tw ter.follow_recom ndat ons.common.models.HasExcludedUser ds
 mport com.tw ter.follow_recom ndat ons.common.models.HasGeohashAndCountryCode
 mport com.tw ter.follow_recom ndat ons.common.models.Has nval dRelat onsh pUser ds
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dByUser ds
 mport com.tw ter.follow_recom ndat ons.common.models.HasRecentFollo dUser ds
 mport com.tw ter.follow_recom ndat ons.common.models.HasUserState
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Params

case class ContentRecom nderRequest(
  overr de val params: Params,
  overr de val cl entContext: Cl entContext,
   nputExcludeUser ds: Seq[Long],
  overr de val recentFollo dUser ds: Opt on[Seq[Long]],
  overr de val recentFollo dByUser ds: Opt on[Seq[Long]],
  overr de val  nval dRelat onsh pUser ds: Opt on[Set[Long]],
  overr de val d splayLocat on: D splayLocat on,
  maxResults: Opt on[ nt] = None,
  overr de val debugOpt ons: Opt on[DebugOpt ons] = None,
  overr de val geohashAndCountryCode: Opt on[GeohashAndCountryCode] = None,
  overr de val userState: Opt on[UserState] = None)
    extends HasParams
    w h HasCl entContext
    w h HasD splayLocat on
    w h HasDebugOpt ons
    w h HasRecentFollo dUser ds
    w h HasRecentFollo dByUser ds
    w h Has nval dRelat onsh pUser ds
    w h HasExcludedUser ds
    w h HasUserState
    w h HasGeohashAndCountryCode {
  overr de val excludedUser ds: Seq[Long] = {
     nputExcludeUser ds ++ cl entContext.user d.toSeq
  }
}

package com.tw ter.follow_recom ndat ons.flows.post_nux_ml

 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.HasPreFetc dFeature
 mport com.tw ter.follow_recom ndat ons.common.models._
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Params

case class PostNuxMlRequest(
  overr de val params: Params,
  overr de val cl entContext: Cl entContext,
  overr de val s m larToUser ds: Seq[Long],
   nputExcludeUser ds: Seq[Long],
  overr de val recentFollo dUser ds: Opt on[Seq[Long]],
  overr de val  nval dRelat onsh pUser ds: Opt on[Set[Long]],
  overr de val recentFollo dByUser ds: Opt on[Seq[Long]],
  overr de val d sm ssedUser ds: Opt on[Seq[Long]],
  overr de val d splayLocat on: D splayLocat on,
  maxResults: Opt on[ nt] = None,
  overr de val debugOpt ons: Opt on[DebugOpt ons] = None,
  overr de val wtf mpress ons: Opt on[Seq[Wtf mpress on]],
  overr de val utt nterest ds: Opt on[Seq[Long]] = None,
  overr de val custom nterests: Opt on[Seq[Str ng]] = None,
  overr de val geohashAndCountryCode: Opt on[GeohashAndCountryCode] = None,
   nputPrev ouslyRecom ndedUser ds: Opt on[Set[Long]] = None,
   nputPrev ouslyFollo dUser ds: Opt on[Set[Long]] = None,
  overr de val  sSoftUser: Boolean = false,
  overr de val userState: Opt on[UserState] = None,
  overr de val qual yFactor: Opt on[Double] = None)
    extends HasParams
    w h HasS m larToContext
    w h HasCl entContext
    w h HasExcludedUser ds
    w h HasD splayLocat on
    w h HasDebugOpt ons
    w h HasGeohashAndCountryCode
    w h HasPreFetc dFeature
    w h HasD sm ssedUser ds
    w h Has nterest ds
    w h HasPrev ousRecom ndat onsContext
    w h Has sSoftUser
    w h HasUserState
    w h Has nval dRelat onsh pUser ds
    w h HasQual yFactor {
  overr de val excludedUser ds: Seq[Long] = {
     nputExcludeUser ds ++ cl entContext.user d.toSeq ++ s m larToUser ds
  }
  overr de val prev ouslyRecom ndedUser Ds: Set[Long] =
     nputPrev ouslyRecom ndedUser ds.getOrElse(Set.empty)
  overr de val prev ouslyFollo dUser ds: Set[Long] =
     nputPrev ouslyFollo dUser ds.getOrElse(Set.empty)
}

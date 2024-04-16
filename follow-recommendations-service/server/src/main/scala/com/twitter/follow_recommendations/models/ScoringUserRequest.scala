package com.tw ter.follow_recom ndat ons.models

 mport com.tw ter.follow_recom ndat ons.common.feature_hydrat on.common.HasPreFetc dFeature
 mport com.tw ter.follow_recom ndat ons.common.models._
 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.product_m xer.core.model.marshall ng.request.HasCl entContext
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Params

case class Scor ngUserRequest(
  overr de val cl entContext: Cl entContext,
  overr de val d splayLocat on: D splayLocat on,
  overr de val params: Params,
  overr de val debugOpt ons: Opt on[DebugOpt ons] = None,
  overr de val recentFollo dUser ds: Opt on[Seq[Long]],
  overr de val recentFollo dByUser ds: Opt on[Seq[Long]],
  overr de val wtf mpress ons: Opt on[Seq[Wtf mpress on]],
  overr de val s m larToUser ds: Seq[Long],
  cand dates: Seq[Cand dateUser],
  debugParams: Opt on[DebugParams] = None,
   sSoftUser: Boolean = false)
    extends HasCl entContext
    w h HasD splayLocat on
    w h HasParams
    w h HasDebugOpt ons
    w h HasPreFetc dFeature
    w h HasS m larToContext {
  def toOffl neThr ft: offl ne.Offl neScor ngUserRequest = offl ne.Offl neScor ngUserRequest(
    Cl entContextConverter.toFRSOffl neCl entContextThr ft(cl entContext),
    d splayLocat on.toOffl neThr ft,
    cand dates.map(_.toOffl neUserThr ft)
  )
  def toRecom ndat onRequest: Recom ndat onRequest = Recom ndat onRequest(
    cl entContext = cl entContext,
    d splayLocat on = d splayLocat on,
    d splayContext = None,
    maxResults = None,
    cursor = None,
    excluded ds = None,
    fetchPromotedContent = None,
    debugParams = debugParams,
     sSoftUser =  sSoftUser
  )
}

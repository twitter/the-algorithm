package com.tw ter.follow_recom ndat ons.models

 mport com.tw ter.follow_recom ndat ons.common.models.Cl entContextConverter
 mport com.tw ter.follow_recom ndat ons.common.models.D splayLocat on
 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext

case class Recom ndat onRequest(
  cl entContext: Cl entContext,
  d splayLocat on: D splayLocat on,
  d splayContext: Opt on[D splayContext],
  maxResults: Opt on[ nt],
  cursor: Opt on[Str ng],
  excluded ds: Opt on[Seq[Long]],
  fetchPromotedContent: Opt on[Boolean],
  debugParams: Opt on[DebugParams] = None,
  userLocat onState: Opt on[Str ng] = None,
   sSoftUser: Boolean = false) {
  def toOffl neThr ft: offl ne.Offl neRecom ndat onRequest = offl ne.Offl neRecom ndat onRequest(
    Cl entContextConverter.toFRSOffl neCl entContextThr ft(cl entContext),
    d splayLocat on.toOffl neThr ft,
    d splayContext.map(_.toOffl neThr ft),
    maxResults,
    cursor,
    excluded ds,
    fetchPromotedContent,
    debugParams.map(DebugParams.toOffl neThr ft)
  )
}

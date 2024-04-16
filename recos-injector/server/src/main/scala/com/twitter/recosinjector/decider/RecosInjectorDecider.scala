package com.tw ter.recos njector.dec der

 mport com.tw ter.dec der.{Dec der, Dec derFactory, RandomRec p ent, Rec p ent}

case class Recos njectorDec der( sProd: Boolean, dataCenter: Str ng) {
  lazy val dec der: Dec der = Dec derFactory(
    So ("conf g/dec der.yml"),
    So (getOverlayPath( sProd, dataCenter))
  )()

  pr vate def getOverlayPath( sProd: Boolean, dataCenter: Str ng): Str ng = {
     f ( sProd) {
      s"/usr/local/conf g/overlays/recos- njector/recos- njector/prod/$dataCenter/dec der_overlay.yml"
    } else {
      s"/usr/local/conf g/overlays/recos- njector/recos- njector/stag ng/$dataCenter/dec der_overlay.yml"
    }
  }

  def getDec der: Dec der = dec der

  def  sAva lable(feature: Str ng, rec p ent: Opt on[Rec p ent]): Boolean = {
    dec der. sAva lable(feature, rec p ent)
  }

  def  sAva lable(feature: Str ng): Boolean =  sAva lable(feature, So (RandomRec p ent))
}

object Recos njectorDec derConstants {
  val T etEventTransfor rUserT etEnt yEdgesDec der =
    "t et_event_transfor r_user_t et_ent y_edges"
  val EnableEm T etEdgeFromReply = "enable_em _t et_edge_from_reply"
  val EnableUnfavor eEdge = "enable_unfavor e_edge"
}

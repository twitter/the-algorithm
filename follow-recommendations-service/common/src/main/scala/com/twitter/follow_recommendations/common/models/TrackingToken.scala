package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.f nagle.trac ng.Trace
 mport com.tw ter.follow_recom ndat ons.logg ng.{thr ftscala => offl ne}
 mport com.tw ter.follow_recom ndat ons.{thr ftscala => t}
 mport com.tw ter.scrooge.B naryThr ftStructSer al zer
 mport com.tw ter.suggests.controller_data.thr ftscala.ControllerData
 mport com.tw ter.ut l.Base64Str ngEncoder

/**
 * used for attr but on per target-cand date pa r
 * @param sess on d         trace- d of t  f nagle request
 * @param controllerData    64-b  encoded b nary attr butes of   recom ndat on
 * @param algor hm d        d for  dent fy ng a cand date s ce. ma nta ned for backwards compat b l y
 */
case class Track ngToken(
  sess on d: Long,
  d splayLocat on: Opt on[D splayLocat on],
  controllerData: Opt on[ControllerData],
  algor hm d: Opt on[ nt]) {

  def toThr ft: t.Track ngToken = {
    Trace. d.trace d.toLong
    t.Track ngToken(
      sess on d = sess on d,
      d splayLocat on = d splayLocat on.map(_.toThr ft),
      controllerData = controllerData,
      algo d = algor hm d
    )
  }

  def toOffl neThr ft: offl ne.Track ngToken = {
    offl ne.Track ngToken(
      sess on d = sess on d,
      d splayLocat on = d splayLocat on.map(_.toOffl neThr ft),
      controllerData = controllerData,
      algo d = algor hm d
    )
  }
}

object Track ngToken {
  val b naryThr ftSer al zer = B naryThr ftStructSer al zer[t.Track ngToken](t.Track ngToken)
  def ser al ze(track ngToken: Track ngToken): Str ng = {
    Base64Str ngEncoder.encode(b naryThr ftSer al zer.toBytes(track ngToken.toThr ft))
  }
  def deser al ze(track ngTokenStr: Str ng): Track ngToken = {
    fromThr ft(b naryThr ftSer al zer.fromBytes(Base64Str ngEncoder.decode(track ngTokenStr)))
  }
  def fromThr ft(token: t.Track ngToken): Track ngToken = {
    Track ngToken(
      sess on d = token.sess on d,
      d splayLocat on = token.d splayLocat on.map(D splayLocat on.fromThr ft),
      controllerData = token.controllerData,
      algor hm d = token.algo d
    )
  }
}

tra  HasTrack ngToken {
  def track ngToken: Opt on[Track ngToken]
}

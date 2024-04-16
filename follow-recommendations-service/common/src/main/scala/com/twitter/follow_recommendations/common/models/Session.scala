package com.tw ter.follow_recom ndat ons.common.models

 mport com.tw ter.f nagle.trac ng.Trace

object Sess on {

  /**
   * T  sess on d  n FRS  s t  f nagle trace  d wh ch  s stat c w h n t  l fet   of a s ngle
   * request.
   *
   *    s used w n generat ng per-cand date tokens ( n Track ngTokenTransform) and  s also passed
   *  n to downstream Opt mus ranker requests.
   *
   */
  def getSess on d: Long = Trace. d.trace d.toLong
}

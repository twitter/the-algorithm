package com.tw ter.product_m xer.component_l brary.model.cursor

 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.UrtP pel neCursor

/**
 * URT Cursor model that may be used w n cursor ng over a unordered cand date s ce. On each server
 * round-tr p, t  server w ll append t   Ds of t  ele nts  n t  response to t  cursor. T n
 * on subsequent requests t  cl ent w ll return t  cursor, and t  excluded ds l st can be sent to
 * t  downstream's exclude ds para ter, or excluded locally v a a f lter on t  cand date s ce
 * p pel ne.
 *
 * Note that t  cursor  s bounded, as t  excluded ds l st cannot be appended to  ndef n ely due
 * to payload s ze constra nts. As such, t  strategy  s typ cally used for bounded (l m ed page
 * s ze) products, or for unbounded (unl m ed page s ze) products  n conjunct on w h an
 *  mpress on store.  n t  latter case, t  cursor excluded ds l st would be l m ed to a max s ze
 * v a a c rcular buffer  mple ntat on, wh ch would be un oned w h t   mpress on store  Ds w n
 * f lter ng. T  usage allows t   mpress on store to "catch up", as t re  s often latency
 * bet en w n an  mpress on cl ent event  s sent by t  cl ent and storage  n t   mpress on
 * store.
 *
 * @param  n  alSort ndex See [[UrtP pel neCursor]]
 * @param excluded ds t  l st of  Ds to exclude from t  cand date l st
 */
case class UrtUnorderedExclude dsCursor(
  overr de val  n  alSort ndex: Long,
  excluded ds: Seq[Long])
    extends UrtP pel neCursor

case class UnorderedExclude dsCursor(excluded ds: Seq[Long]) extends P pel neCursor

package com.tw ter.product_m xer.component_l brary.model.cursor

 mport com.tw ter.product_m xer.core.p pel ne.P pel neCursor
 mport com.tw ter.product_m xer.core.p pel ne.UrtP pel neCursor
 mport com.tw ter.search.common.ut l.bloomf lter.Adapt veLong ntBloomF lter

/**
 * Cursor model that may be used w n cursor ng over a unordered cand date s ce. On each server
 * round-tr p, t  server w ll add t   Ds of t  cand dates  nto a space eff c ent bloom f lter.
 * T n on subsequent requests t  cl ent w ll return t  cursor, and t  bloom f lter can be sent to
 * t  downstream's bloom f lter para ter  n ser al zed form, or exclude cand dates locally v a a
 * f lter on t  cand date s ce p pel ne.
 *
 * @param  n  alSort ndex See [[UrtP pel neCursor]]
 * @param long ntBloomF lter t  bloom f lter to use to dedup cand date from t  cand date l st
 */
case class UrtUnorderedBloomF lterCursor(
  overr de val  n  alSort ndex: Long,
  // space-eff c ent and mutable var ant of t  BloomF lter class used for stor ng long  ntegers.
  long ntBloomF lter: Adapt veLong ntBloomF lter)
    extends UrtP pel neCursor

case class UnorderedBloomF lterCursor(
  long ntBloomF lter: Adapt veLong ntBloomF lter)
    extends P pel neCursor

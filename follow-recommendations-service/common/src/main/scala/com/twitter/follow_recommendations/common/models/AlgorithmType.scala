package com.tw ter.follow_recom ndat ons.common.models

/**
 * Each cand date s ce algor hm could be based on one, or more, of t  4 general type of
 *  nformat on   have on a user:
 *   1. Soc al: t  user's connect ons  n Tw ter's soc al graph.
 *   2. Geo: t  user's geograph cal  nformat on.
 *   3.  nterest:  nformat on on t  user's chosen  nterests.
 *   4. Act v y:  nformat on on t  user's past act v y.
 *
 * Note that an algor hm can fall under more than one of t se categor es.
 */
object Algor hmType extends Enu rat on {
  type Algor hmType = Value

  val Soc al: Value = Value("soc al")
  val Geo: Value = Value("geo")
  val Act v y: Value = Value("act v y")
  val  nterest: Value = Value(" nterest")
}

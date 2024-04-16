package com.tw ter.product_m xer.component_l brary.cand date_s ce.ann

 mport com.tw ter.ann.common._

/**
 * A [[Ann dQuery]]  s a query class wh ch def nes t  ann ent  es w h runt   params and number of ne ghbors requested
 *
 * @param  ds Sequence of quer es
 * @param numOfNe ghbors Number of ne ghbors requested
 * @param runt  Params ANN Runt   Params
 * @param batchS ze Batch s ze to t  st ch cl ent
 * @tparam T type of  query.
 * @tparam P  runt   para ters supported by t   ndex.
 */
case class Ann dQuery[T, P <: Runt  Params](
   ds: Seq[T],
  numOfNe ghbors:  nt,
  runt  Params: P)

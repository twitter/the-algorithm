package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls

/**
 * G ven a [[Cand dateW hDeta ls]] return t  correspond ng [[Bucket]]
 *   should be assoc ated w h w n used  n a `pattern` or `rat o`
 *  n [[ nsertAppendPatternResults]] or [[ nsertAppendRat oResults]]
 */
tra  Bucketer[Bucket] {
  def apply(cand dateW hDeta ls: Cand dateW hDeta ls): Bucket
}

object Bucketer {

  /** A [[Bucketer]] that buckets by [[Cand dateW hDeta ls.s ce]] */
  val ByCand dateS ce: Bucketer[Cand dateP pel ne dent f er] =
    (cand dateW hDeta ls: Cand dateW hDeta ls) => cand dateW hDeta ls.s ce
}

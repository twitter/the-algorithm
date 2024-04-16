package com.tw ter.product_m xer.core.funct onal_component.cand date_s ce

 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch

sealed tra  BaseCand dateS ce[-Request, +Cand date] extends Component {

  /** @see [[Cand dateS ce dent f er]] */
  val  dent f er: Cand dateS ce dent f er
}

/**
 * A [[Cand dateS ce]] returns a Seq of ''potent al'' content
 *
 * @note [[Cand dateS ce]]s that return a s ngle value need to transform
 *          nto a Seq, e  r by do ng `Seq(value)` or extract ng
 *       cand dates from t  value.
 *
 * @tparam Request argu nts to get t  potent al content
 * @tparam Cand date t  potent al content
 */
tra  Cand dateS ce[-Request, +Cand date] extends BaseCand dateS ce[Request, Cand date] {

  /** returns a Seq of ''potent al'' content */
  def apply(request: Request): St ch[Seq[Cand date]]
}

/**
 * A [[Cand dateS ceW hExtractedFeatures]] returns a result conta n ng both a Seq of
 * ''potent al'' cand dates as  ll as an extracted feature map that w ll later be appended
 * to t  p pel ne's [[com.tw ter.product_m xer.core.p pel ne.P pel neQuery]] feature map. T   s
 * useful for cand date s ces that return features that m ght be useful later on w hout need ng
 * to re-hydrate t m.
 *
 * @note [[Cand dateS ce]]s that return a s ngle value need to transform
 *          nto a Seq, e  r by do ng `Seq(value)` or extract ng
 *       cand dates from t  value.
 *
 * @tparam Request argu nts to get t  potent al content
 * @tparam Cand date t  potent al content
 */
tra  Cand dateS ceW hExtractedFeatures[-Request, +Cand date]
    extends BaseCand dateS ce[Request, Cand date] {

  /** returns a result conta n ng a seq of ''potent al'' content and extracted features
   * from t  cand date s ce.
   * */
  def apply(request: Request): St ch[Cand datesW hS ceFeatures[Cand date]]
}

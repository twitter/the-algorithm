package com.tw ter.product_m xer.core.p pel ne

 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.St ch

/** Base tra  for all `p pel ne`  mple ntat ons */
tra  P pel ne[-Query, Result] extends Component {

  /** T  [[P pel neConf g]] that was used to create t  [[P pel ne]] */
  pr vate[core] val conf g: P pel neConf g

  /** Returns t  underly ng arrow that represents t  p pel ne. T   s a val because   want to ensure
   * that t  arrow  s long-l ved and cons stent, not generated per-request.
   *
   * D rectly us ng t  arrow allows   to comb ne   w h ot r arrows eff c ently.
   */
  val arrow: Arrow[Query, P pel neResult[Result]]

  /** all ch ld [[Component]]s that t  [[P pel ne]] conta ns wh ch w ll be reg stered and mon ored */
  val ch ldren: Seq[Component]

  /**
   * A  lper for execut ng a s ngle query.
   *
   * toResultTry and lo rFromTry has t  end result of adapt ng P pel neResult  nto e  r a
   * successful result or a St ch except on, wh ch  s a common use-case for callers,
   * part cularly  n t  case of [[com.tw ter.product_m xer.core.p pel ne.product.ProductP pel ne]].
   */
  def process(query: Query): St ch[Result] = arrow(query).map(_.toResultTry).lo rFromTry

  f nal overr de def toStr ng = s"P pel ne( dent f er=$ dent f er)"

  /**
   * [[P pel ne]]s are equal to one anot r  f t y  re generated from t  sa  [[P pel neConf g]],
   *   c ck t  by do ng a reference c cks f rst t n compar ng t  [[P pel neConf g]]  nstances.
   *
   *   can sk p add  onal c cks because t  ot r f elds (e.g. [[ dent f er]] and [[ch ldren]])
   * are der ved from t  [[P pel neConf g]].
   */
  f nal overr de def equals(obj: Any): Boolean = obj match {
    case p pel ne: P pel ne[_, _] =>
      p pel ne.eq(t ) || p pel ne.conf g.eq(conf g) || p pel ne.conf g == conf g
    case _ => false
  }
}

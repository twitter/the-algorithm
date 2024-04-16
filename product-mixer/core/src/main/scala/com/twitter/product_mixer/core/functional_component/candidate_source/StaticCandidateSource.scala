package com.tw ter.product_m xer.core.funct onal_component.cand date_s ce

 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateS ce dent f er
 mport com.tw ter.st ch.St ch

/**
 * A [[Cand dateS ce]] that always returns [[result]] regardless of t   nput
 */
case class Stat cCand dateS ce[Cand date](
  overr de val  dent f er: Cand dateS ce dent f er,
  result: Seq[Cand date])
    extends Cand dateS ce[Any, Cand date] {

  def apply(request: Any): St ch[Seq[Cand date]] = St ch.value(result)
}

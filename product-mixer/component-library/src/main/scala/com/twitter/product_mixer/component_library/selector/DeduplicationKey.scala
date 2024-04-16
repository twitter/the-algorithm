package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls

/**
 * [[DropSelector]] detects dupl cates by look ng for cand dates w h t  sa  key. A key can be
 * anyth ng but  s typ cally der ved from a cand date's  d and class. T  approach  s not always
 * appropr ate. For example, two cand date s ces m ght both return d fferent sub-classes of
 * [[com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date]] result ng  n
 * t m not be ng treated as dupl cates.
 */
tra  Dedupl cat onKey[Key] {
  def apply(cand date:  emCand dateW hDeta ls): Key
}

/**
 * Use cand date  d and class to determ ne dupl cates.
 */
object  dAndClassDupl cat onKey extends Dedupl cat onKey[(Str ng, Class[_ <: Un versalNoun[Any]])] {
  def apply( em:  emCand dateW hDeta ls): (Str ng, Class[_ <: Un versalNoun[Any]]) =
    ( em.cand date. d.toStr ng,  em.cand date.getClass)
}

/**
 * Use cand date  d to determ ne dupl cates.
 * T  should be used  nstead of [[ dAndClassDupl cat onKey]]  n order to dedupl cate across
 * d fferent cand date types, such as d fferent  mple ntat ons of
 * [[com.tw ter.product_m xer.component_l brary.model.cand date.BaseT etCand date]].
 */
object  dDupl cat onKey extends Dedupl cat onKey[Str ng] {
  def apply( em:  emCand dateW hDeta ls): Str ng =  em.cand date. d.toStr ng
}

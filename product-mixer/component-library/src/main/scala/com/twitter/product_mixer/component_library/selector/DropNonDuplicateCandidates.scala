package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Keep only t  cand dates  n `rema n ngCand dates` that appear mult ple t  s.
 * T   gnores modules and cursors from be ng removed.
 *
 * @param dupl cat onKey how to generate t  key used to  dent fy dupl cate cand dates
 *
 * @note [[com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date]] are  gnored.
 * @note [[com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls]] are  gnored.
 *
 * @example  f `rema n ngCand dates`
 * `Seq(s ceA_ d1, s ceA_ d1, s ceA_ d2, s ceB_ d1, s ceB_ d2, s ceB_ d3, s ceC_ d4)`
 * t n t  output result w ll be `Seq(s ceA_ d1, s ceA_ d2)`
 */
case class DropNonDupl cateCand dates(
  overr de val p pel neScope: Cand dateScope,
  dupl cat onKey: Dedupl cat onKey[_] =  dAndClassDupl cat onKey)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val dupl cateCand dates = dropNonDupl cates(
      p pel neScope = p pel neScope,
      cand dates = rema n ngCand dates,
      dupl cat onKey = dupl cat onKey)

    SelectorResult(rema n ngCand dates = dupl cateCand dates, result = result)
  }

  /**
   *  dent fy and keep cand dates us ng t  suppl ed key extract on and  rger funct ons. By default
   * t  w ll keep only cand dates that appear mult ple t  s as determ ned by compar ng
   * t  conta ned cand date  D and class type. Cand dates appear ng only once w ll be dropped.
   *
   * @note [[com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date]] are  gnored.
   * @note [[com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls]] are  gnored.
   *
   * @param cand dates wh ch may have ele nts to drop
   * @param dupl cat onKey how to generate a key for a cand date for  dent fy ng dupl cates
   */
  pr vate[t ] def dropNonDupl cates[Cand date <: Cand dateW hDeta ls, Key](
    p pel neScope: Cand dateScope,
    cand dates: Seq[Cand date],
    dupl cat onKey: Dedupl cat onKey[Key],
  ): Seq[Cand date] = {
    //  re   are c ck ng  f each cand date has mult ple appearances or not
    val  sCand dateADupl cate: Map[Key, Boolean] = cand dates
      .collect {
        case  em:  emCand dateW hDeta ls
             f p pel neScope.conta ns( em) && ! em.cand date. s nstanceOf[CursorCand date] =>
           em
      }.groupBy(dupl cat onKey(_))
      .mapValues(_.length > 1)

    cand dates.f lter {
      case  em:  emCand dateW hDeta ls =>
         sCand dateADupl cate.getOrElse(dupl cat onKey( em), true)
      case _: ModuleCand dateW hDeta ls => true
      case _ => false
    }
  }
}

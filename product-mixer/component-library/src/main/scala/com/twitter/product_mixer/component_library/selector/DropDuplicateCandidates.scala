package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.component_l brary.selector.DropSelector.dropDupl cates
 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

/**
 * Keep only t  f rst  nstance of a cand date  n t  `rema n ngCand dates` as determ ned by compar ng
 * t  conta ned cand date  D and class type. Subsequent match ng  nstances w ll be dropped. For
 * more deta ls, see DropSelector#dropDupl cates
 *
 * @param dupl cat onKey how to generate t  key used to  dent fy dupl cate cand dates (by default use  d and class na )
 * @param  rgeStrategy how to  rge two cand dates w h t  sa  key (by default p ck t  f rst one)
 *
 * @note [[com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date]] are  gnored.
 * @note [[com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls]] are  gnored.
 *
 * @example  f `rema n ngCand dates`
 * `Seq(s ceA_ d1, s ceA_ d1, s ceA_ d2, s ceB_ d1, s ceB_ d2, s ceB_ d3, s ceC_ d4)`
 * t n t  output cand dates w ll be `Seq(s ceA_ d1, s ceA_ d2, s ceB_ d3, s ceC_ d4)`
 */
case class DropDupl cateCand dates(
  overr de val p pel neScope: Cand dateScope = AllP pel nes,
  dupl cat onKey: Dedupl cat onKey[_] =  dAndClassDupl cat onKey,
   rgeStrategy: Cand date rgeStrategy = P ckF rstCand date rger)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {
    val dedupedCand dates = dropDupl cates(
      p pel neScope = p pel neScope,
      cand dates = rema n ngCand dates,
      dupl cat onKey = dupl cat onKey,
       rgeStrategy =  rgeStrategy)

    SelectorResult(rema n ngCand dates = dedupedCand dates, result = result)
  }
}

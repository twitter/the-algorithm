package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.component_l brary.selector.DropSelector.dropDupl cates
 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel ne
 mport com.tw ter.product_m xer.core.funct onal_component.common.Spec f cP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.selector._
 mport com.tw ter.product_m xer.core.model.common. dent f er.Cand dateP pel ne dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery

object DropDupl cateModule emCand dates {

  /**
   * L m  t  number of module  em cand dates (for 1 or more modules) from a certa n cand date
   * s ce. See [[DropDupl cateModule emCand dates]] for more deta ls.
   *
   * @param cand dateP pel ne p pel nes on wh ch to run t  selector
   *
   * @note Scala doesn't allow overloaded  thods w h default argu nts. Users want ng to custom ze
   *       t  de-dupe log c should use t  default constructor.   could prov de mult ple
   *       constructors but that see d more confus ng (f ve ways to  nstant ate t  selector) or not
   *       necessar ly less verbose ( f   p cked spec f c use-cases rat r than try ng to support
   *       everyth ng).
   */
  def apply(cand dateP pel ne: Cand dateP pel ne dent f er) = new DropDupl cateModule emCand dates(
    Spec f cP pel ne(cand dateP pel ne),
     dAndClassDupl cat onKey,
    P ckF rstCand date rger)

  def apply(cand dateP pel nes: Set[Cand dateP pel ne dent f er]) =
    new DropDupl cateModule emCand dates(
      Spec f cP pel nes(cand dateP pel nes),
       dAndClassDupl cat onKey,
      P ckF rstCand date rger)
}

/**
 * L m  t  number of module  em cand dates (for 1 or more modules) from certa n cand date
 * p pel nes.
 *
 * T  acts l ke a [[DropDupl cateCand dates]] but for modules  n `rema n ngCand dates`
 * from any of t  prov ded [[cand dateP pel nes]]. S m lar to [[DropDupl cateCand dates]],  
 * keeps only t  f rst  nstance of a cand date w h n a module as determ ned by compar ng
 * t  conta ned cand date  D and class type.
 *
 * @param p pel neScope p pel ne scope on wh ch to run t  selector
 * @param dupl cat onKey how to generate t  key used to  dent fy dupl cate cand dates (by default use  d and class na )
 * @param  rgeStrategy how to  rge two cand dates w h t  sa  key (by default p ck t  f rst one)
 *
 * For example,  f a cand dateP pel ne returned 5 modules each
 * conta n ng dupl cate  ems  n t  cand date pool, t n t  module  ems  n each of t 
 * 5 modules w ll be f ltered to t  un que  ems w h n each module.
 *
 * Anot r example  s  f   have 2 modules each w h t  sa   ems as t  ot r,
 *   won't dedupl cate across modules.
 *
 * @note t  updates t  module  n t  `rema n ngCand dates`
 */
case class DropDupl cateModule emCand dates(
  overr de val p pel neScope: Cand dateScope,
  dupl cat onKey: Dedupl cat onKey[_] =  dAndClassDupl cat onKey,
   rgeStrategy: Cand date rgeStrategy = P ckF rstCand date rger)
    extends Selector[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {

    val rema n ngCand datesL m ed = rema n ngCand dates.map {
      case module: ModuleCand dateW hDeta ls  f p pel neScope.conta ns(module) =>
        // t  appl es to all cand dates  n a module, even  f t y are from a d fferent
        // cand date s ce, wh ch can happen  f  ems are added to a module dur ng select on
        module.copy(cand dates = dropDupl cates(
          p pel neScope = AllP pel nes,
          cand dates = module.cand dates,
          dupl cat onKey = dupl cat onKey,
           rgeStrategy =  rgeStrategy))
      case cand date => cand date
    }

    SelectorResult(rema n ngCand dates = rema n ngCand datesL m ed, result = result)
  }
}

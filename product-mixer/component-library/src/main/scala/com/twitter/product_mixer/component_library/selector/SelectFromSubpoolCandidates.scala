package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.Un versalNoun
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport scala.reflect.ClassTag

sealed tra  Subpool ncludeTypes

tra   nclude nSubpool[-Query <: P pel neQuery] extends Subpool ncludeTypes {

  /**
   * G ven t  `query`, current `rema n ngCand date`, and t  `result`,
   * returns w t r t  spec f c `rema n ngCand date` should be passed  nto t 
   * [[SelectFromSubpoolCand dates]]'s [[SelectFromSubpoolCand dates.selector]]
   *
   * @note t  `result` conta ns t  [[SelectorResult.result]] that was passed  nto t  selector,
   *       so each `rema n ngCand date` w ll get t  sa  `result` Seq.
   */
  def apply(
    query: Query,
    rema n ngCand date: Cand dateW hDeta ls,
    result: Seq[Cand dateW hDeta ls]
  ): Boolean
}

case class  ncludeCand dateType nSubpool[Cand dateType <: Un versalNoun[_]](
)(
   mpl c  tag: ClassTag[Cand dateType])
    extends  nclude nSubpool[P pel neQuery] {

  overr de def apply(
    query: P pel neQuery,
    rema n ngCand date: Cand dateW hDeta ls,
    result: Seq[Cand dateW hDeta ls]
  ): Boolean = rema n ngCand date. sCand dateType[Cand dateType]()
}

tra   ncludeSet nSubpool[-Query <: P pel neQuery] extends Subpool ncludeTypes {

  /**
   * G ven t  `query`, all `rema n ngCand dates`` and `results`,
   * returns a Set of wh ch cand dates should be  ncluded  n t  subpool.
   *
   * @note t  returned set  s only used to determ ne subpool  mbersh p. Mutat ng t  cand dates
   *        s  nval d and won't work. T  order of t  cand dates w ll be preserved from t  current
   *       order of t  rema n ng cand dates sequence.
   */
  def apply(
    query: Query,
    rema n ngCand date: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): Set[Cand dateW hDeta ls]
}

sealed tra  SubpoolRema n ngCand datesHandler

/**
 * Cand dates rema n ng  n t  subpool after runn ng t  selector w ll be
 * prepended to t  beg nn ng of t  [[SelectorResult.rema n ngCand dates]]
 */
case object PrependToBeg nn ngOfRema n ngCand dates extends SubpoolRema n ngCand datesHandler

/**
 * Cand dates rema n ng  n t  subpool after runn ng t  selector w ll be
 * appended to t  end of t  [[SelectorResult.rema n ngCand dates]]
 */
case object AppendToEndOfRema n ngCand dates extends SubpoolRema n ngCand datesHandler

/**
 * Creates a subpool of all `rema n ngCand dates` for wh ch [[subpool nclude]] resolves to true
 * ( n t  sa  order as t  or g nal `rema n ngCand dates`) and runs t  [[selector]] w h t 
 * subpool passed  n as t  `rema n ngCand dates`.
 *
 * Most custo rs want to use a  nclude nSubpool that chooses  f each cand date should be  ncluded
 *  n t  subpool.
 * W re necessary,  ncludeSet nSubpool allows   to def ne t m  n bulk w/ a Set.
 *
 * @note any cand dates  n t  subpool wh ch are not added to t  [[SelectorResult.result]]
 *       w ll be treated accord ng to t  [[SubpoolRema n ngCand datesHandler]]
 */
class SelectFromSubpoolCand dates[-Query <: P pel neQuery] pr vate[selector] (
  val selector: Selector[Query],
  subpool nclude: Subpool ncludeTypes,
  subpoolRema n ngCand datesHandler: SubpoolRema n ngCand datesHandler =
    AppendToEndOfRema n ngCand dates)
    extends Selector[Query] {

  overr de val p pel neScope: Cand dateScope = selector.p pel neScope

  overr de def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {

    val (selectedCand dates, ot rCand dates) = subpool nclude match {
      case  nclude nSubpool:  nclude nSubpool[Query] =>
        rema n ngCand dates.part  on(cand date =>
          p pel neScope.conta ns(cand date) &&  nclude nSubpool(query, cand date, result))
      case  ncludeSet nSubpool:  ncludeSet nSubpool[Query] =>
        val  ncludeSet =
           ncludeSet nSubpool(query, rema n ngCand dates.f lter(p pel neScope.conta ns), result)
        rema n ngCand dates.part  on(cand date =>  ncludeSet.conta ns(cand date))
    }

    val underly ngSelectorResult = selector.apply(query, selectedCand dates, result)
    val rema n ngCand datesW hSubpoolRema n ngCand dates =
      subpoolRema n ngCand datesHandler match {
        case AppendToEndOfRema n ngCand dates =>
          ot rCand dates ++ underly ngSelectorResult.rema n ngCand dates
        case PrependToBeg nn ngOfRema n ngCand dates =>
          underly ngSelectorResult.rema n ngCand dates ++ ot rCand dates
      }
    underly ngSelectorResult.copy(rema n ngCand dates =
      rema n ngCand datesW hSubpoolRema n ngCand dates)
  }

  overr de def toStr ng: Str ng = s"SelectFromSubpoolCand dates(${selector.toStr ng}))"
}

object SelectFromSubpoolCand dates {
  def apply[Query <: P pel neQuery](
    selector: Selector[Query],
     nclude nSubpool:  nclude nSubpool[Query],
    subpoolRema n ngCand datesHandler: SubpoolRema n ngCand datesHandler =
      AppendToEndOfRema n ngCand dates
  ) = new SelectFromSubpoolCand dates[Query](
    selector,
     nclude nSubpool,
    subpoolRema n ngCand datesHandler
  )

  def  ncludeSet[Query <: P pel neQuery](
    selector: Selector[Query],
     ncludeSet nSubpool:  ncludeSet nSubpool[Query],
    subpoolRema n ngCand datesHandler: SubpoolRema n ngCand datesHandler =
      AppendToEndOfRema n ngCand dates
  ) = new SelectFromSubpoolCand dates[Query](
    selector,
     ncludeSet nSubpool,
    subpoolRema n ngCand datesHandler
  )
}

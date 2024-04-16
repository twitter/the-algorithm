package com.tw ter.product_m xer.component_l brary.selector.sorter

 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls

object SorterFromOrder ng {
  def apply(order ng: Order ng[Cand dateW hDeta ls], sortOrder: SortOrder): SorterFromOrder ng =
    SorterFromOrder ng( f (sortOrder == Descend ng) order ng.reverse else order ng)
}

/**
 * Sorts cand dates based on t  prov ded [[order ng]]
 *
 * @note t  [[Order ng]] must be trans  ve, so  f `A < B` and `B < C` t n `A < C`.
 * @note sort ng randomly v a `Order ng.by[Cand dateW hDeta ls, Double](_ => Random.nextDouble())`
 *        s not safe and can fa l at runt   s nce T mSort depends on stable sort values for
 *       p vot ng. To sort randomly, use [[RandomShuffleSorter]]  nstead.
 */
case class SorterFromOrder ng(
  order ng: Order ng[Cand dateW hDeta ls])
    extends SorterProv der
    w h Sorter {

  overr de def sort[Cand date <: Cand dateW hDeta ls](cand dates: Seq[Cand date]): Seq[Cand date] =
    cand dates.sorted(order ng)
}

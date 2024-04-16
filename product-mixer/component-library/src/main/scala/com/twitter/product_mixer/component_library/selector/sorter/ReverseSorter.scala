package com.tw ter.product_m xer.component_l brary.selector.sorter

 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls

/**
 * Reverse cand dates.
 *
 * @example `UpdateSortResults(ReverseSorter())`
 */
object ReverseSorter extends SorterProv der w h Sorter {

  overr de def sort[Cand date <: Cand dateW hDeta ls](cand dates: Seq[Cand date]): Seq[Cand date] =
    cand dates.reverse
}

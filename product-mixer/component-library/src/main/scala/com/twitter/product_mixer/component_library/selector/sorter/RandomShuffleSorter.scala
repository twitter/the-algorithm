package com.tw ter.product_m xer.component_l brary.selector.sorter

 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport scala.ut l.Random

/**
 * Randomly shuffles cand dates us ng t  prov ded [[random]]
 *
 * @example `UpdateSortResults(RandomShuffleSorter())`
 * @param random used to set t  seed and for ease of test ng,  n most cases leav ng   as t  default  s f ne.
 */
case class RandomShuffleSorter(random: Random = new Random(0)) extends SorterProv der w h Sorter {

  overr de def sort[Cand date <: Cand dateW hDeta ls](cand dates: Seq[Cand date]): Seq[Cand date] =
    random.shuffle(cand dates)
}

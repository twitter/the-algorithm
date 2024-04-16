package com.tw ter.cr_m xer.s m lar y_eng ne

 mport com.tw ter.cr_m xer.model.T etW hCand dateGenerat on nfo
 mport com.tw ter.s mclusters_v2.common.T et d
 mport scala.collect on.mutable
 mport scala.collect on.mutable.ArrayBuffer

object S m lar yS ceOrder ngUt l {
  /**
   * T  funct on flatten and dedup  nput cand dates accord ng to t  order  n t   nput Seq
   * [[cand date10, cand date11], [cand date20, cand date21]] => [cand date10, cand date11, cand date20, cand date21]
   */
  def keepG venOrder(
    cand dates: Seq[Seq[T etW hCand dateGenerat on nfo]],
  ): Seq[T etW hCand dateGenerat on nfo] = {

    val seen = mutable.Set[T et d]()
    val comb nedCand dates = cand dates.flatten
    val result = ArrayBuffer[T etW hCand dateGenerat on nfo]()

    comb nedCand dates.foreach { cand date =>
      val cand dateT et d = cand date.t et d
      val seenCand date = seen.conta ns(cand dateT et d) // de-dup
       f (!seenCand date) {
        result += cand date
        seen.add(cand date.t et d)
      }
    }
    //convert result to  mmutable seq
    result.toL st
  }
}

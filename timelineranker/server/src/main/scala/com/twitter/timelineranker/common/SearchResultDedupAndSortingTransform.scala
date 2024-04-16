package com.tw ter.t  l neranker.common

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l nes.model.T et d
 mport com.tw ter.ut l.Future
 mport scala.collect on.mutable

/**
 * Remove dupl cate search results and order t m reverse-chron.
 */
object SearchResultDedupAndSort ngTransform
    extends FutureArrow[Cand dateEnvelope, Cand dateEnvelope] {
  def apply(envelope: Cand dateEnvelope): Future[Cand dateEnvelope] = {
    val seenT et ds = mutable.Set.empty[T et d]
    val dedupedResults = envelope.searchResults
      .f lter(result => seenT et ds.add(result. d))
      .sortBy(_. d)(Order ng[T et d].reverse)

    val transfor dEnvelope = envelope.copy(searchResults = dedupedResults)
    Future.value(transfor dEnvelope)
  }
}

package com.tw ter.t  l neranker.common

 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.t  l neranker.core.Cand dateEnvelope
 mport com.tw ter.t  l neranker.model.RecapQuery
 mport com.tw ter.ut l.Future

/**
 * Create a Cand dateEnvelope based on t   ncom ng RecapQuery
 */
object CreateCand dateEnvelopeTransform extends FutureArrow[RecapQuery, Cand dateEnvelope] {
  overr de def apply(query: RecapQuery): Future[Cand dateEnvelope] = {
    Future.value(Cand dateEnvelope(query))
  }
}

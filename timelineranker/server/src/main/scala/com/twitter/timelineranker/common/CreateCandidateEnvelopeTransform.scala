package com.twitter.timelineranker.common

import com.twitter.servo.util.FutureArrow
import com.twitter.timelineranker.core.CandidateEnvelope
import com.twitter.timelineranker.model.RecapQuery
import com.twitter.util.Future

/**
 * Create a CandidateEnvelope based on the incoming RecapQuery
 */
object CreateCandidateEnvelopeTransform extends FutureArrow[RecapQuery, CandidateEnvelope] {
  override def apply(query: RecapQuery): Future[CandidateEnvelope] = {
    Future.value(CandidateEnvelope(query))
  }
}

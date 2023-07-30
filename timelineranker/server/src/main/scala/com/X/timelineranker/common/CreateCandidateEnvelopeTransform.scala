package com.X.timelineranker.common

import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.CandidateEnvelope
import com.X.timelineranker.model.RecapQuery
import com.X.util.Future

/**
 * Create a CandidateEnvelope based on the incoming RecapQuery
 */
object CreateCandidateEnvelopeTransform extends FutureArrow[RecapQuery, CandidateEnvelope] {
  override def apply(query: RecapQuery): Future[CandidateEnvelope] = {
    Future.value(CandidateEnvelope(query))
  }
}

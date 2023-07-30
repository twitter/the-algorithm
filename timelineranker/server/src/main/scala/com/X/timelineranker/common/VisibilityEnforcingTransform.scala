package com.X.timelineranker.common

import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.CandidateEnvelope
import com.X.timelineranker.core.HydratedTweets
import com.X.timelines.visibility.VisibilityEnforcer
import com.X.util.Future

/**
 * Transform which uses an instance of a VisiblityEnforcer to filter down HydratedTweets
 */
class VisibilityEnforcingTransform(visibilityEnforcer: VisibilityEnforcer)
    extends FutureArrow[CandidateEnvelope, CandidateEnvelope] {
  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    visibilityEnforcer.apply(Some(envelope.query.userId), envelope.hydratedTweets.outerTweets).map {
      visibleTweets =>
        val innerTweets = envelope.hydratedTweets.innerTweets
        envelope.copy(
          hydratedTweets = HydratedTweets(outerTweets = visibleTweets, innerTweets = innerTweets))
    }
  }
}

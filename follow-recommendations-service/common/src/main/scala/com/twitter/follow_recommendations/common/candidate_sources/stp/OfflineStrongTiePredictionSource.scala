package com.twitter.follow_recommendations.common.candidate_sources.stp

import com.google.inject.Singleton
import com.twitter.follow_recommendations.common.candidate_sources.stp.OfflineStpSourceParams.UseDenserPmiMatritwitter
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.hermit.model.Algorithm
import com.twitter.product_mitwitterer.component_library.model.candidate.UserCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.util.logging.Logging
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.request.HasClientContetwittert
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.HasParams
import javatwitter.inject.Inject

object OfflineStpScore etwittertends Feature[UserCandidate, Option[Double]]

/**
 * Main source for strong-tie-prediction candidates generated offline.
 */
@Singleton
class OfflineStrongTiePredictionSource @Inject() (
  offlineStpSourceWithLegacyPmiMatritwitter: OfflineStpSourceWithLegacyPmiMatritwitter,
  offlineStpSourceWithDensePmiMatritwitter: OfflineStpSourceWithDensePmiMatritwitter)
    etwittertends CandidateSource[HasParams with HasClientContetwittert, CandidateUser]
    with Logging {
  override val identifier: CandidateSourceIdentifier = OfflineStrongTiePredictionSource.Identifier

  override def apply(request: HasParams with HasClientContetwittert): Stitch[Seq[CandidateUser]] = {
    if (request.params(UseDenserPmiMatritwitter)) {
      logger.info("Using dense PMI matritwitter.")
      offlineStpSourceWithDensePmiMatritwitter(request)
    } else {
      logger.info("Using legacy PMI matritwitter.")
      offlineStpSourceWithLegacyPmiMatritwitter(request)
    }
  }
}

object OfflineStrongTiePredictionSource {
  val Identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(Algorithm.StrongTiePredictionRec.toString)
}

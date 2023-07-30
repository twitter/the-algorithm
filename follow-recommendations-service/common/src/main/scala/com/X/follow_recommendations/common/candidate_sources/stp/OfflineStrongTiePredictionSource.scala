package com.X.follow_recommendations.common.candidate_sources.stp

import com.google.inject.Singleton
import com.X.follow_recommendations.common.candidate_sources.stp.OfflineStpSourceParams.UseDenserPmiMatrix
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.hermit.model.Algorithm
import com.X.product_mixer.component_library.model.candidate.UserCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.util.logging.Logging
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.stitch.Stitch
import com.X.timelines.configapi.HasParams
import javax.inject.Inject

object OfflineStpScore extends Feature[UserCandidate, Option[Double]]

/**
 * Main source for strong-tie-prediction candidates generated offline.
 */
@Singleton
class OfflineStrongTiePredictionSource @Inject() (
  offlineStpSourceWithLegacyPmiMatrix: OfflineStpSourceWithLegacyPmiMatrix,
  offlineStpSourceWithDensePmiMatrix: OfflineStpSourceWithDensePmiMatrix)
    extends CandidateSource[HasParams with HasClientContext, CandidateUser]
    with Logging {
  override val identifier: CandidateSourceIdentifier = OfflineStrongTiePredictionSource.Identifier

  override def apply(request: HasParams with HasClientContext): Stitch[Seq[CandidateUser]] = {
    if (request.params(UseDenserPmiMatrix)) {
      logger.info("Using dense PMI matrix.")
      offlineStpSourceWithDensePmiMatrix(request)
    } else {
      logger.info("Using legacy PMI matrix.")
      offlineStpSourceWithLegacyPmiMatrix(request)
    }
  }
}

object OfflineStrongTiePredictionSource {
  val Identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier(Algorithm.StrongTiePredictionRec.toString)
}

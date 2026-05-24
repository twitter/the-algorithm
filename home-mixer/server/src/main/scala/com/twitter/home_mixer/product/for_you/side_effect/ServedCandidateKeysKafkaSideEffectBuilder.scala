package com.twitter.home_mixer.product.for_you.side_effect

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class ServedCandidateKeysKafkaSideEffectBuilder @Inject() (
  injectedServiceIdentifier: ServiceIdentifier) {
  def build(
    sourceIdentifiers: Set[CandidatePipelineIdentifier]
  ): ServedCandidateKeysKafkaSideEffect = {
    val topic = injectedServiceIdentifier.environment.toLowerCase match {
      case "prod" => "tq_ct_served_candidate_keys"
      case _ => "tq_ct_served_candidate_keys_staging"
    }
    new ServedCandidateKeysKafkaSideEffect(topic, sourceIdentifiers)
  }
}

package com.twitter.home_mitwitterer.product.for_you.side_effect

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.CandidatePipelineIdentifier
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class ServedCandidateFeatureKeysKafkaSideEffectBuilder @Inject() (
  injectedServiceIdentifier: ServiceIdentifier) {
  def build(
    sourceIdentifiers: Set[CandidatePipelineIdentifier]
  ): ServedCandidateFeatureKeysKafkaSideEffect = {
    val topic = injectedServiceIdentifier.environment.toLowerCase match {
      case "prod" => "tq_ct_served_candidate_feature_keys"
      case _ => "tq_ct_served_candidate_feature_keys_staging"
    }
    new ServedCandidateFeatureKeysKafkaSideEffect(topic, sourceIdentifiers)
  }
}

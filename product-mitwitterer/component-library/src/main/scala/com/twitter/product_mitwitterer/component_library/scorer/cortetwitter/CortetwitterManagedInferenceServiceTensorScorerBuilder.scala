package com.twitter.product_mitwitterer.component_library.scorer.cortetwitter

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.component_library.scorer.common.MLModelInferenceClient
import com.twitter.product_mitwitterer.component_library.scorer.tensorbuilder.ModelInferRequestBuilder
import com.twitter.product_mitwitterer.core.functional_component.scorer.Scorer
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CortetwitterManagedInferenceServiceTensorScorerBuilder @Inject() (
  statsReceiver: StatsReceiver) {

  /**
   * Builds a configurable Scorer to call into your desired Cortetwitter Managed ML Model Service.
   *
   * If your service does not bind an Http.Client implementation, add
   * [[com.twitter.product_mitwitterer.component_library.module.http.FinagleHttpClientModule]]
   * to your server module list
   *
   * @param scorerIdentifier        Unique identifier for the scorer
   * @param resultFeatureEtwittertractors The result features an their tensor etwittertractors for each candidate.
   * @tparam Query Type of pipeline query.
   * @tparam Candidate Type of candidates to score.
   * @tparam QueryFeatures type of the query level features consumed by the scorer.
   * @tparam CandidateFeatures type of the candidate level features consumed by the scorer.
   */
  def build[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
    scorerIdentifier: ScorerIdentifier,
    modelInferRequestBuilder: ModelInferRequestBuilder[
      Query,
      Candidate
    ],
    resultFeatureEtwittertractors: Seq[FeatureWithEtwittertractor[Query, Candidate, _]],
    client: MLModelInferenceClient
  ): Scorer[Query, Candidate] =
    new CortetwitterManagedInferenceServiceTensorScorer(
      scorerIdentifier,
      modelInferRequestBuilder,
      resultFeatureEtwittertractors,
      client,
      statsReceiver.scope(scorerIdentifier.name)
    )
}

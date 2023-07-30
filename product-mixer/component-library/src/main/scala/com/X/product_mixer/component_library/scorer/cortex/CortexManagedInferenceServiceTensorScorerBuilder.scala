package com.X.product_mixer.component_library.scorer.cortex

import com.X.finagle.stats.StatsReceiver
import com.X.product_mixer.component_library.scorer.common.MLModelInferenceClient
import com.X.product_mixer.component_library.scorer.tensorbuilder.ModelInferRequestBuilder
import com.X.product_mixer.core.functional_component.scorer.Scorer
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CortexManagedInferenceServiceTensorScorerBuilder @Inject() (
  statsReceiver: StatsReceiver) {

  /**
   * Builds a configurable Scorer to call into your desired Cortex Managed ML Model Service.
   *
   * If your service does not bind an Http.Client implementation, add
   * [[com.X.product_mixer.component_library.module.http.FinagleHttpClientModule]]
   * to your server module list
   *
   * @param scorerIdentifier        Unique identifier for the scorer
   * @param resultFeatureExtractors The result features an their tensor extractors for each candidate.
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
    resultFeatureExtractors: Seq[FeatureWithExtractor[Query, Candidate, _]],
    client: MLModelInferenceClient
  ): Scorer[Query, Candidate] =
    new CortexManagedInferenceServiceTensorScorer(
      scorerIdentifier,
      modelInferRequestBuilder,
      resultFeatureExtractors,
      client,
      statsReceiver.scope(scorerIdentifier.name)
    )
}

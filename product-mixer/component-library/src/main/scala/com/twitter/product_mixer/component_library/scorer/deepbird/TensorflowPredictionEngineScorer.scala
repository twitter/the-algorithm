package com.twitter.product_mixer.component_library.scorer.deepbird

import com.twitter.cortex.deepbird.runtime.prediction_engine.TensorflowPredictionEngine
import com.twitter.cortex.deepbird.thriftjava.ModelSelector
import com.twitter.ml.prediction_service.BatchPredictionRequest
import com.twitter.ml.prediction_service.BatchPredictionResponse
import com.twitter.product_mixer.core.feature.datarecord.BaseDataRecordFeature
import com.twitter.product_mixer.core.feature.featuremap.datarecord.FeaturesScope
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.util.Future

/**
 * Configurable Scorer that calls a TensorflowPredictionEngine.
 * @param identifier Unique identifier for the scorer
 * @param tensorflowPredictionEngine The TensorFlow Prediction Engine
 * @param queryFeatures The Query Features to convert and pass to the deepbird model.
 * @param candidateFeatures The Candidate Features to convert and pass to the deepbird model.
 * @param resultFeatures The Candidate features returned by the model.
 * @tparam Query Type of pipeline query.
 * @tparam Candidate Type of candidates to score.
 * @tparam QueryFeatures type of the query level features consumed by the scorer.
 * @tparam CandidateFeatures type of the candidate level features consumed by the scorer.
 * @tparam ResultFeatures type of the candidate level features returned by the scorer.
 */
class TensorflowPredictionEngineScorer[
  Query <: PipelineQuery,
  Candidate <: UniversalNoun[Any],
  QueryFeatures <: BaseDataRecordFeature[Query, _],
  CandidateFeatures <: BaseDataRecordFeature[Candidate, _],
  ResultFeatures <: BaseDataRecordFeature[Candidate, _]
](
  override val identifier: ScorerIdentifier,
  tensorflowPredictionEngine: TensorflowPredictionEngine,
  queryFeatures: FeaturesScope[QueryFeatures],
  candidateFeatures: FeaturesScope[CandidateFeatures],
  resultFeatures: Set[ResultFeatures])
    extends BaseDeepbirdV2Scorer[
      Query,
      Candidate,
      QueryFeatures,
      CandidateFeatures,
      ResultFeatures
    ](
      identifier,
      { _: Query =>
        None
      },
      queryFeatures,
      candidateFeatures,
      resultFeatures) {

  override def getBatchPredictions(
    request: BatchPredictionRequest,
    modelSelector: ModelSelector
  ): Future[BatchPredictionResponse] = tensorflowPredictionEngine.getBatchPrediction(request)
}

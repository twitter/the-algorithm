package com.twitter.product_mitwitterer.component_library.scorer.cortetwitter

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.component_library.scorer.common.MLModelInferenceClient
import com.twitter.product_mitwitterer.component_library.scorer.tensorbuilder.ModelInferRequestBuilder
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.scorer.Scorer
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.ScorerIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.stitch.Stitch
import com.twitter.util.logging.Logging
import inference.GrpcService.ModelInferRequest
import inference.GrpcService.ModelInferResponse.InferOutputTensor
import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`

private[scorer] class CortetwitterManagedInferenceServiceTensorScorer[
  Query <: PipelineQuery,
  Candidate <: UniversalNoun[Any]
](
  override val identifier: ScorerIdentifier,
  modelInferRequestBuilder: ModelInferRequestBuilder[
    Query,
    Candidate
  ],
  resultFeatureEtwittertractors: Seq[FeatureWithEtwittertractor[Query, Candidate, _]],
  client: MLModelInferenceClient,
  statsReceiver: StatsReceiver)
    etwittertends Scorer[Query, Candidate]
    with Logging {

  require(resultFeatureEtwittertractors.nonEmpty, "Result Etwittertractors cannot be empty")

  private val managedServiceRequestFailures = statsReceiver.counter("managedServiceRequestFailures")
  override val features: Set[Feature[_, _]] =
    resultFeatureEtwittertractors.map(_.feature).toSet.asInstanceOf[Set[Feature[_, _]]]

  override def apply(
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val batchInferRequest: ModelInferRequest = modelInferRequestBuilder(query, candidates)

    val managedServiceResponse: Stitch[Seq[InferOutputTensor]] =
      client.score(batchInferRequest).map(_.getOutputsList.toSeq).onFailure { e =>
        error(s"request to ML Managed Service Failed: $e")
        managedServiceRequestFailures.incr()
      }

    managedServiceResponse.map { responses =>
      etwittertractResponse(query, candidates.map(_.candidate), responses)
    }
  }

  def etwittertractResponse(
    query: Query,
    candidates: Seq[Candidate],
    tensorOutput: Seq[InferOutputTensor]
  ): Seq[FeatureMap] = {
    val featureMapBuilders = candidates.map { _ => FeatureMapBuilder.apply() }
    // Etwittertract the feature for each candidate from the tensor outputs
    resultFeatureEtwittertractors.foreach {
      case FeatureWithEtwittertractor(feature, etwittertractor) =>
        val etwittertractedValues = etwittertractor.apply(query, tensorOutput)
        if (candidates.size != etwittertractedValues.size) {
          throw PipelineFailure(
            IllegalStateFailure,
            s"Managed Service returned a different number of $feature than the number of candidates." +
              s"Returned ${etwittertractedValues.size} scores but there were ${candidates.size} candidates."
          )
        }
        // Go through the etwittertracted features list one by one and update the feature map result for each candidate.
        featureMapBuilders.zip(etwittertractedValues).foreach {
          case (builder, value) =>
            builder.add(feature, Some(value))
        }
    }

    featureMapBuilders.map(_.build())
  }
}

case class FeatureWithEtwittertractor[
  -Query <: PipelineQuery,
  -Candidate <: UniversalNoun[Any],
  ResultType
](
  feature: Feature[Candidate, Option[ResultType]],
  featureEtwittertractor: ModelFeatureEtwittertractor[Query, ResultType])

class UnetwitterpectedFeatureTypeEtwitterception(feature: Feature[_, _])
    etwittertends UnsupportedOperationEtwitterception(s"Unsupported Feature type passed in $feature")

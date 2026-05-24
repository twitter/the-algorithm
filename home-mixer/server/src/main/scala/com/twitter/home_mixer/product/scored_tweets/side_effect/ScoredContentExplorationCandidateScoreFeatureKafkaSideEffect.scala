package com.twitter.home_mixer.product.scored_tweets.side_effect

import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.kafka.serde.ScalaSerdes
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.model.HomeFeatures.ScoreFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.WeightedModelScoreFeature
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableContentExplorationScoreScribingParam
import com.twitter.product_mixer.component_library.side_effect.KafkaPublishingSideEffect
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.strato.columns.content_understanding.content_understanding.thriftscala.ContentExplorationServingResult
import com.twitter.strato.columns.content_understanding.content_understanding.thriftscala.ContentExplorationServingResultsAnalysis
import com.twitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.Serializer

/**
 * Pipeline side-effect that publishes scores feature keys to a Kafka topic.
 */
@Singleton
class ScoredContentExplorationCandidateScoreFeatureKafkaSideEffect @Inject() (
  serviceIdentifier: ServiceIdentifier,
  statsReceiver: StatsReceiver)
    extends KafkaPublishingSideEffect[
      Long,
      ContentExplorationServingResultsAnalysis,
      PipelineQuery,
      HasMarshalling
    ]
    with Conditionally[PipelineQuery, HasMarshalling]
    with Logging {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier(
    "ScoredContentExplorationCandidateScoreFeatureKafka")
  private val statScope: String = this.getClass.getSimpleName
  private val scopedStatsReceiver = statsReceiver.scope(statScope)
  private val failedScribingCount = scopedStatsReceiver.scope("failed").counter()

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Boolean =
    query.params(EnableContentExplorationScoreScribingParam)

  private val eligibleServedType: Set[hmt.ServedType] = Set(
    hmt.ServedType.ForYouContentExploration,
    hmt.ServedType.ForYouContentExplorationTier2,
    hmt.ServedType.ForYouContentExplorationDeepRetrievalI2i,
    hmt.ServedType.ForYouContentExplorationTier2DeepRetrievalI2i,
    hmt.ServedType.ForYouUserInterestSummary
  )

  private val kafkaTopic: String = serviceIdentifier.environment.toLowerCase match {
    case "prod" => "content_exploration_ranking_analysis"
    case _ => "content_exploration_ranking_analysis_devel"
  }

  override val bootstrapServer: String = ""
  override val keySerde: Serializer[Long] = ScalaSerdes.Long.serializer()
  override val valueSerde: Serializer[ContentExplorationServingResultsAnalysis] =
    ScalaSerdes.Thrift[ContentExplorationServingResultsAnalysis].serializer
  override val clientId: String = "home_mixer_dropped_candidate_feature_keys_producer"

  override def buildRecords(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Seq[ProducerRecord[Long, ContentExplorationServingResultsAnalysis]] = {
    try {
      val userId: Long = query.getUserOrGuestId.getOrElse(-1L)

      val allCandidates = selectedCandidates ++ remainingCandidates ++ droppedCandidates
      val eligible = allCandidates.filter(selectEligibleCandidates)

      val scoredResults: Seq[ContentExplorationServingResult] = eligible.map { cdd =>
        ContentExplorationServingResult(
          cdd.candidateIdLong,
          cdd.features.get(ScoreFeature),
          cdd.features.get(WeightedModelScoreFeature),
          Some(cdd.features.getOrElse(ServedTypeFeature, hmt.ServedType.Undefined).toString)
        )
      }
      val analysisResult = ContentExplorationServingResultsAnalysis(
        userId,
        scoredResults
      )
      Seq(
        new ProducerRecord[Long, ContentExplorationServingResultsAnalysis](
          kafkaTopic,
          userId,
          analysisResult
        )
      )
    } catch {
      case _: Throwable =>
        failedScribingCount.incr()
        Seq.empty
    }
  }

  def selectEligibleCandidates(
    candidate: CandidateWithDetails
  ): Boolean = {
    val servedType =
      candidate.features
        .getOrElse(ServedTypeFeature, hmt.ServedType.Undefined)
    eligibleServedType.contains(servedType)
  }
}

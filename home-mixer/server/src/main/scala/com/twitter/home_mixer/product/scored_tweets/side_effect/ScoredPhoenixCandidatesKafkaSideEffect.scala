package com.twitter.home_mixer.product.scored_tweets.side_effect

import com.google.inject.name.Named
import com.twitter.finagle.mtls.authentication.ServiceIdentifier
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.finatra.kafka.serde.ScalaSerdes
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.finatra.kafka.serde.UnKeyedSerde
import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.model.PhoenixPredictedScoreFeature.PhoenixPredictedScoreFeatures
import com.twitter.home_mixer.model.PredictedScoreFeature.PredictedScoreFeatures
import com.twitter.home_mixer.param.HomeGlobalParams.PhoenixCluster
import com.twitter.home_mixer.product.scored_tweets.param.ScoredTweetsParam.EnableScoredPhoenixCandidatesKafkaSideEffectParam
import com.twitter.home_mixer.util.PhoenixUtils.createCandidateSets
import com.twitter.home_mixer.util.PhoenixUtils.getPredictionResponseMap
import com.twitter.home_mixer.util.PhoenixUtils.getTweetInfoFromCandidates
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.component_library.side_effect.KafkaPublishingSideEffect
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect.Conditionally
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.model.marshalling.HasMarshalling
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.servo.util.MemoizingStatsReceiver
import com.twitter.stitch.Stitch
import com.twitter.timelines.timeline_logging.thriftscala.PredictionScore
import com.twitter.timelines.timeline_logging.thriftscala.ScoredCandidate
import com.twitter.util.logging.Logging
import com.x.user_action_sequence.ActionName
import com.x.user_action_sequence.PredictNextActionsRequest
import io.grpc.ManagedChannel
import javax.inject.Inject
import javax.inject.Singleton
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.Serializer

/**
 * Pipeline side-effect that publishes scored phoenix candidates to a Kafka topic.
 */
@Singleton
class ScoredPhoenixCandidatesKafkaSideEffect @Inject() (
  @Named("PhoenixClient") channelsMap: Map[PhoenixCluster.Value, Seq[ManagedChannel]],
  serviceIdentifier: ServiceIdentifier,
  statsReceiver: StatsReceiver)
    extends KafkaPublishingSideEffect[
      UnKeyed,
      ScoredCandidate,
      PipelineQuery,
      HasMarshalling
    ]
    with Conditionally[PipelineQuery, HasMarshalling]
    with Logging {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier(
    "ScoredPhoenixCandidatesKafka")
  private val statScope: String = this.getClass.getSimpleName

  val memoizingStatsReceiver: MemoizingStatsReceiver = new MemoizingStatsReceiver(
    statsReceiver.scope(statScope))

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Boolean = {
    query.params(EnableScoredPhoenixCandidatesKafkaSideEffectParam) &&
    query.features.flatMap(_.getOrElse(UserActionsFeature, None)).isDefined
  }

  private val kafkaTopic: String = serviceIdentifier.environment.toLowerCase match {
    case "prod" => "home_mixer_phoenix_scored_candidates"
    case _ => "home_mixer_phoenix_scored_candidates_staging"
  }

  override val bootstrapServer: String = ""
  override val keySerde: Serializer[UnKeyed] = UnKeyedSerde.serializer()
  override val valueSerde: Serializer[ScoredCandidate] =
    ScalaSerdes.Thrift[ScoredCandidate].serializer
  override val clientId: String = "home_mixer_phoenix_scored_candidate_producer"

  // Returns Map from phoenixCluster -> Map [Tweets -> Map [Actions -> Score]]
  private def getPredictionResponsesAllClusters(
    request: PredictNextActionsRequest
  ): Stitch[Map[String, Map[Long, Map[ActionName, Double]]]] = {
    val phoenixClusters = channelsMap.keySet.toSeq
    val timeoutMs = 1000
    Stitch
      .traverse(phoenixClusters) { phoenixCluster =>
        val channels = channelsMap(phoenixCluster)
        getPredictionResponseMap(
          request,
          channels,
          phoenixCluster.toString,
          timeoutMs,
          memoizingStatsReceiver)
          .handle {
            case _ => Map.empty[Long, Map[ActionName, Double]]
          }
          .map(phoenixCluster.toString -> _)
      }.map(_.toMap)
  }

  // Not defined buildRecords as we override apply to use buildRecordsStitch
  override def buildRecords(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: HasMarshalling
  ): Seq[ProducerRecord[UnKeyed, ScoredCandidate]] = {
    ???
  }

  def buildRecordsStitch(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
  ): Stitch[Seq[ProducerRecord[UnKeyed, ScoredCandidate]]] = {
    val nonCachedSelectedCandidates =
      selectedCandidates.filterNot(_.features.getOrElse(IsReadFromCacheFeature, false))

    val candidates = nonCachedSelectedCandidates.map(_.getCandidate[TweetCandidate]())
    val featureMaps = nonCachedSelectedCandidates.map(_.features)
    val tweetInfos = getTweetInfoFromCandidates(candidates, featureMaps)
    val request = createCandidateSets(query, tweetInfos)
    val predictionsMapStitch =
      getPredictionResponsesAllClusters(request)

    val scoredCandidatesStitch = Stitch.traverse(nonCachedSelectedCandidates) { candidate =>
      val tweetId = candidate.candidateIdLong
      val authorId = candidate.features.getOrElse(AuthorIdFeature, None)
      val sourceTweetId =
        candidate.features.getOrElse(SourceTweetIdFeature, None) match {
          case Some(sourceTweetId) => sourceTweetId
          case _ => tweetId
        }
      predictionsMapStitch.map { predictionsMap =>
        val phoenixPredictionScores = predictionsMap.flatMap {
          case (phoenixCluster, scoresMapPerTweet) =>
            val actionPredictionsMap = scoresMapPerTweet.getOrElse(sourceTweetId, Map.empty)
            PhoenixPredictedScoreFeatures.map { feature =>
              val predictions = feature.actions.flatMap(actionPredictionsMap.get)
              val score = if (predictions.nonEmpty) Some(predictions.max) else None
              val featureString = f"phoenix.$phoenixCluster.${feature.featureName}"
              PredictionScore(Some(featureString), score)
            }
        }.toSeq
        val prodScores = PredictedScoreFeatures.map { feature =>
          PredictionScore(Some(feature.statName), candidate.features.getOrElse(feature, None))
        }
        ScoredCandidate(
          tweetId = tweetId,
          authorId = authorId,
          viewerId = query.getOptionalUserId,
          sourceTweetId = Some(sourceTweetId),
          servedRequestId = query.features.flatMap(_.getOrElse(PredictionRequestIdFeature, None)),
          requestTimeMs = Some(query.queryTime.inMillis),
          predictionScores = Some((phoenixPredictionScores ++ prodScores).toSet)
        )
      }
    }

    scoredCandidatesStitch.map { predictionScoresSeq =>
      predictionScoresSeq.map {
        new ProducerRecord(kafkaTopic, new UnKeyed, _)
      }
    }
  }

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, HasMarshalling]
  ): Stitch[Unit] = {
    val recordsStitch = buildRecordsStitch(
      query = inputs.query,
      selectedCandidates = inputs.selectedCandidates,
    )

    recordsStitch.flatMap { records =>
      Stitch
        .traverse(records) { record =>
          Stitch.callFuture(kafkaProducer.send(record))
        }
    }.unit
  }

}

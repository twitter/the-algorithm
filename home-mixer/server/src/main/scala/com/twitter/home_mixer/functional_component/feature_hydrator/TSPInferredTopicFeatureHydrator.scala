package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.contentrecommender.{thriftscala => cr}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.home_mixer.functional_component.feature_hydrator.adapters.inferred_topic.InferredTopicAdapter
import com.twitter.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.twitter.home_mixer.model.HomeFeatures.TSPMetricTagFeature
import com.twitter.home_mixer.model.HomeFeatures.TopicContextFunctionalityTypeFeature
import com.twitter.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.twitter.ml.api.DataRecord
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.BasicTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.RecommendationTopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.TopicContextFunctionalityType
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.topic_signals.tsp.TopicSocialProofClientColumn
import com.twitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => sid}
import com.twitter.topiclisting.TopicListingViewerContext
import com.twitter.tsp.{thriftscala => tsp}

import javax.inject.Inject
import javax.inject.Singleton
import scala.collection.JavaConverters._

object TSPInferredTopicFeature extends Feature[TweetCandidate, Map[Long, Double]]
object TSPInferredTopicDataRecordFeature
    extends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TSPInferredTopicFeatureHydrator @Inject() (
  topicSocialProofClientColumn: TopicSocialProofClientColumn,
  statsReceiver: StatsReceiver,
) extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("TSPInferredTopic")

  override val features: Set[Feature[_, _]] =
    Set(
      TSPInferredTopicFeature,
      TSPInferredTopicDataRecordFeature,
      TopicIdSocialContextFeature,
      TopicContextFunctionalityTypeFeature)

  private val topK = 3

  private val sourcesToSetSocialProof: Set[sid.CandidateTweetSourceId] = Set(
    sid.CandidateTweetSourceId.Simcluster,
    sid.CandidateTweetSourceId.CroonTweet
  )

  private val scopedStatsReceiver = statsReceiver.scope(getClass.getSimpleName)
  private val keyFoundCounter = scopedStatsReceiver.counter("key/found")
  private val keyLossCounter = scopedStatsReceiver.counter("key/loss")
  private val requestFailCounter = scopedStatsReceiver.counter("request/fail")

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(TSPInferredTopicFeature, Map.empty[Long, Double])
    .add(TSPInferredTopicDataRecordFeature, new DataRecord())
    .add(TopicIdSocialContextFeature, None)
    .add(TopicContextFunctionalityTypeFeature, None)
    .build()

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val tags = candidates.collect {
      case candidate if candidate.features.getTry(TSPMetricTagFeature).isReturn =>
        candidate.candidate.id -> candidate.features
          .getOrElse(TSPMetricTagFeature, Set.empty[tsp.MetricTag])
    }.toMap

    val topicSocialProofRequest =
      tsp.TopicSocialProofRequest(
        userId = query.getRequiredUserId,
        tweetIds = candidates.map(_.candidate.id).toSet,
        displayLocation = cr.DisplayLocation.HomeTimeline,
        topicListingSetting = tsp.TopicListingSetting.Followable,
        context = TopicListingViewerContext.fromClientContext(query.clientContext).toThrift,
        bypassModes = None,
        // Only CRMixer source has this data. Convert the CRMixer metric tag to tsp metric tag.
        tags = if (tags.isEmpty) None else Some(tags)
      )

    topicSocialProofClientColumn.fetcher
      .fetch(topicSocialProofRequest)
      .map(_.v)
      .map {
        case Some(response) =>
          candidates.map { candidate =>
            val topicWithScores = response.socialProofs.getOrElse(candidate.candidate.id, Seq.empty)
            if (topicWithScores.nonEmpty) {
              keyFoundCounter.incr()
              val (socialProofId, socialProofFunctionalityType) =
                if (candidate.features
                    .getOrElse(CandidateSourceIdFeature, None)
                    .exists(sourcesToSetSocialProof.contains)) {
                  getSocialProof(topicWithScores)
                } else {
                  (None, None)
                }
              val inferredTopicFeatures = convertTopicWithScores(topicWithScores)
              val inferredTopicDataRecord =
                InferredTopicAdapter.adaptToDataRecords(inferredTopicFeatures).asScala.head
              FeatureMapBuilder()
                .add(TSPInferredTopicFeature, inferredTopicFeatures)
                .add(TSPInferredTopicDataRecordFeature, inferredTopicDataRecord)
                .add(TopicIdSocialContextFeature, socialProofId)
                .add(TopicContextFunctionalityTypeFeature, socialProofFunctionalityType)
                .build()
            } else {
              keyLossCounter.incr()
              DefaultFeatureMap
            }
          }
        case _ =>
          requestFailCounter.incr()
          candidates.map { _ =>
            DefaultFeatureMap
          }
      }
  }

  private def getSocialProof(
    topicWithScores: Seq[tsp.TopicWithScore]
  ): (Option[Long], Option[TopicContextFunctionalityType]) = {
    val followingTopicId = topicWithScores
      .collectFirst {
        case tsp.TopicWithScore(topicId, _, _, Some(tsp.TopicFollowType.Following)) =>
          topicId
      }
    if (followingTopicId.nonEmpty) {
      return (followingTopicId, Some(BasicTopicContextFunctionalityType))
    }
    val implicitFollowingId = topicWithScores.collectFirst {
      case tsp.TopicWithScore(topicId, _, _, Some(tsp.TopicFollowType.ImplicitFollow)) =>
        topicId
    }
    if (implicitFollowingId.nonEmpty) {
      return (implicitFollowingId, Some(RecommendationTopicContextFunctionalityType))
    }
    (None, None)
  }

  private def convertTopicWithScores(
    topicWithScores: Seq[tsp.TopicWithScore],
  ): Map[Long, Double] = {
    topicWithScores.sortBy(-_.score).take(topK).map(a => (a.topicId, a.score)).toMap
  }
}

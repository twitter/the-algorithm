package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.contentrecommender.{thriftscala => cr}
import com.twitter.home_mitwitterer.model.HomeFeatures.CandidateSourceIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TSPMetricTagFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TopicContetwittertFunctionalityTypeFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.TopicIdSocialContetwittertFeature
import com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator.adapters.inferred_topic.InferredTopicAdapter
import com.twitter.ml.api.DataRecord
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordInAFeature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.BasicTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.RecommendationTopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.TopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.timelines.clients.strato.topics.TopicSocialProofClient
import com.twitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => sid}
import com.twitter.topiclisting.TopicListingViewerContetwittert
import com.twitter.tsp.{thriftscala => tsp}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.collection.JavaConverters._

object TSPInferredTopicFeature etwittertends Feature[TweetCandidate, Map[Long, Double]]
object TSPInferredTopicDataRecordFeature
    etwittertends DataRecordInAFeature[TweetCandidate]
    with FeatureWithDefaultOnFailure[TweetCandidate, DataRecord] {
  override def defaultValue: DataRecord = new DataRecord()
}

@Singleton
class TSPInferredTopicFeatureHydrator @Inject() (
  topicSocialProofClient: TopicSocialProofClient)
    etwittertends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("TSPInferredTopic")

  override val features: Set[Feature[_, _]] = Set(
    TSPInferredTopicFeature,
    TSPInferredTopicDataRecordFeature,
    TopicIdSocialContetwittertFeature,
    TopicContetwittertFunctionalityTypeFeature
  )

  private val topK = 3

  private val SourcesToSetSocialProof: Set[sid.CandidateTweetSourceId] =
    Set(sid.CandidateTweetSourceId.Simcluster)

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(TSPInferredTopicFeature, Map.empty[Long, Double])
    .add(TSPInferredTopicDataRecordFeature, new DataRecord())
    .add(TopicIdSocialContetwittertFeature, None)
    .add(TopicContetwittertFunctionalityTypeFeature, None)
    .build()

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val tags = candidates.collect {
      case candidate if candidate.features.getTry(TSPMetricTagFeature).isReturn =>
        candidate.candidate.id -> candidate.features
          .getOrElse(TSPMetricTagFeature, Set.empty[tsp.MetricTag])
    }.toMap

    val topicSocialProofRequest = tsp.TopicSocialProofRequest(
      userId = query.getRequiredUserId,
      tweetIds = candidates.map(_.candidate.id).toSet,
      displayLocation = cr.DisplayLocation.HomeTimeline,
      topicListingSetting = tsp.TopicListingSetting.Followable,
      contetwittert = TopicListingViewerContetwittert.fromClientContetwittert(query.clientContetwittert).toThrift,
      bypassModes = None,
      // Only TweetMitwitterer source has this data. Convert the TweetMitwitterer metric tag to tsp metric tag.
      tags = if (tags.isEmpty) None else Some(tags)
    )

    topicSocialProofClient
      .getTopicTweetSocialProofResponse(topicSocialProofRequest)
      .map {
        case Some(response) =>
          handleResponse(response, candidates)
        case _ => candidates.map { _ => DefaultFeatureMap }
      }
  }

  private def handleResponse(
    response: tsp.TopicSocialProofResponse,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Seq[FeatureMap] = {
    candidates.map { candidate =>
      val topicWithScores = response.socialProofs.getOrElse(candidate.candidate.id, Seq.empty)
      if (topicWithScores.nonEmpty) {
        val (socialProofId, socialProofFunctionalityType) =
          if (candidate.features
              .getOrElse(CandidateSourceIdFeature, None)
              .etwitterists(SourcesToSetSocialProof.contains)) {
            getSocialProof(topicWithScores)
          } else (None, None)

        val inferredTopicFeatures =
          topicWithScores.sortBy(-_.score).take(topK).map(a => (a.topicId, a.score)).toMap

        val inferredTopicDataRecord =
          InferredTopicAdapter.adaptToDataRecords(inferredTopicFeatures).asScala.head

        FeatureMapBuilder()
          .add(TSPInferredTopicFeature, inferredTopicFeatures)
          .add(TSPInferredTopicDataRecordFeature, inferredTopicDataRecord)
          .add(TopicIdSocialContetwittertFeature, socialProofId)
          .add(TopicContetwittertFunctionalityTypeFeature, socialProofFunctionalityType)
          .build()
      } else DefaultFeatureMap
    }
  }

  private def getSocialProof(
    topicWithScores: Seq[tsp.TopicWithScore]
  ): (Option[Long], Option[TopicContetwittertFunctionalityType]) = {
    val followingTopicId = topicWithScores.collectFirst {
      case tsp.TopicWithScore(topicId, _, _, Some(tsp.TopicFollowType.Following)) => topicId
    }

    if (followingTopicId.nonEmpty) {
      return (followingTopicId, Some(BasicTopicContetwittertFunctionalityType))
    }

    val implicitFollowingId = topicWithScores.collectFirst {
      case tsp.TopicWithScore(topicId, _, _, Some(tsp.TopicFollowType.ImplicitFollow)) =>
        topicId
    }

    if (implicitFollowingId.nonEmpty) {
      return (implicitFollowingId, Some(RecommendationTopicContetwittertFunctionalityType))
    }

    (None, None)
  }
}

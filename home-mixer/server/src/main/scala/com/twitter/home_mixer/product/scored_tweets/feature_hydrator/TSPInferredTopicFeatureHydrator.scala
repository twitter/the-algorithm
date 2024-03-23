package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator

import com.ExTwitter.contentrecommender.{thriftscala => cr}
import com.ExTwitter.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.TSPMetricTagFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.TopicContextFunctionalityTypeFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.TopicIdSocialContextFeature
import com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator.adapters.inferred_topic.InferredTopicAdapter
import com.ExTwitter.ml.api.DataRecord
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.ExTwitter.product_mixer.core.feature.datarecord.DataRecordInAFeature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.BasicTopicContextFunctionalityType
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.RecommendationTopicContextFunctionalityType
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.metadata.TopicContextFunctionalityType
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.product_mixer.core.util.OffloadFuturePools
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.clients.strato.topics.TopicSocialProofClient
import com.ExTwitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => sid}
import com.ExTwitter.topiclisting.TopicListingViewerContext
import com.ExTwitter.tsp.{thriftscala => tsp}
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
  topicSocialProofClient: TopicSocialProofClient)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("TSPInferredTopic")

  override val features: Set[Feature[_, _]] = Set(
    TSPInferredTopicFeature,
    TSPInferredTopicDataRecordFeature,
    TopicIdSocialContextFeature,
    TopicContextFunctionalityTypeFeature
  )

  private val topK = 3

  private val SourcesToSetSocialProof: Set[sid.CandidateTweetSourceId] =
    Set(sid.CandidateTweetSourceId.Simcluster)

  private val DefaultFeatureMap = FeatureMapBuilder()
    .add(TSPInferredTopicFeature, Map.empty[Long, Double])
    .add(TSPInferredTopicDataRecordFeature, new DataRecord())
    .add(TopicIdSocialContextFeature, None)
    .add(TopicContextFunctionalityTypeFeature, None)
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
      context = TopicListingViewerContext.fromClientContext(query.clientContext).toThrift,
      bypassModes = None,
      // Only TweetMixer source has this data. Convert the TweetMixer metric tag to tsp metric tag.
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
              .exists(SourcesToSetSocialProof.contains)) {
            getSocialProof(topicWithScores)
          } else (None, None)

        val inferredTopicFeatures =
          topicWithScores.sortBy(-_.score).take(topK).map(a => (a.topicId, a.score)).toMap

        val inferredTopicDataRecord =
          InferredTopicAdapter.adaptToDataRecords(inferredTopicFeatures).asScala.head

        FeatureMapBuilder()
          .add(TSPInferredTopicFeature, inferredTopicFeatures)
          .add(TSPInferredTopicDataRecordFeature, inferredTopicDataRecord)
          .add(TopicIdSocialContextFeature, socialProofId)
          .add(TopicContextFunctionalityTypeFeature, socialProofFunctionalityType)
          .build()
      } else DefaultFeatureMap
    }
  }

  private def getSocialProof(
    topicWithScores: Seq[tsp.TopicWithScore]
  ): (Option[Long], Option[TopicContextFunctionalityType]) = {
    val followingTopicId = topicWithScores.collectFirst {
      case tsp.TopicWithScore(topicId, _, _, Some(tsp.TopicFollowType.Following)) => topicId
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
}

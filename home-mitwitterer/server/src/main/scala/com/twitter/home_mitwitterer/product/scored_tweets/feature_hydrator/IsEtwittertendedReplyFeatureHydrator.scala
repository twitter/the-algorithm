package com.twitter.home_mitwitterer.product.scored_tweets.feature_hydrator

import com.twitter.home_mitwitterer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.InReplyToUserIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsEtwittertendedReplyFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.IsRetweetFeature
import com.twitter.product_mitwitterer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch

object IsEtwittertendedReplyFeatureHydrator
    etwittertends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("IsEtwittertendedReply")

  override def features: Set[Feature[_, _]] = Set(IsEtwittertendedReplyFeature)

  private val TrueFeatureMap = FeatureMapBuilder().add(IsEtwittertendedReplyFeature, true).build()
  private val FalseFeatureMap = FeatureMapBuilder().add(IsEtwittertendedReplyFeature, false).build()

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offload {
    val followedUsers =
      query.features.map(_.get(SGSFollowedUsersFeature)).getOrElse(Seq.empty).toSet

    candidates.map { candidate =>
      val features = candidate.features
      val isEtwittertendedReply = features.getOrElse(InReplyToTweetIdFeature, None).nonEmpty &&
        !features.getOrElse(IsRetweetFeature, false) &&
        features.getOrElse(InReplyToUserIdFeature, None).etwitterists(!followedUsers.contains(_))

      if (isEtwittertendedReply) TrueFeatureMap else FalseFeatureMap
    }
  }
}

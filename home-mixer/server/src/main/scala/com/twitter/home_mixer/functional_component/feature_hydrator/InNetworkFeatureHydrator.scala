package com.ExTwitter.home_mixer.functional_component.feature_hydrator

import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.ExTwitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.ExTwitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.CandidateWithFeatures
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch

object InNetworkFeatureHydrator
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("InNetwork")

  override val features: Set[Feature[_, _]] = Set(InNetworkFeature)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val viewerId = query.getRequiredUserId
    val followedUserIds = query.features.get.get(SGSFollowedUsersFeature).toSet

    val featureMaps = candidates.map { candidate =>
      // We use authorId and not sourceAuthorId here so that retweets are defined as in network
      val isInNetworkOpt = candidate.features.getOrElse(AuthorIdFeature, None).map { authorId =>
        // Users cannot follow themselves but this is in network by definition
        val isSelfTweet = authorId == viewerId
        isSelfTweet || followedUserIds.contains(authorId)
      }
      FeatureMapBuilder().add(InNetworkFeature, isInNetworkOpt.getOrElse(true)).build()
    }
    Stitch.value(featureMaps)
  }
}

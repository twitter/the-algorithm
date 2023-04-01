package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.stitch.Stitch
import com.twitter.stitch.socialgraph.{SocialGraph => SocialGraphStitchClient}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocialGraphServiceFeatureHydrator @Inject() (socialGraphStitchClient: SocialGraphStitchClient)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("SocialGraphService")

  override val features: Set[Feature[_, _]] = Set(InNetworkFeature)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val viewerId = query.getRequiredUserId

    // We use authorId and not sourceAuthorId here so that retweets are defined as in network
    val authorIds = candidates.map(_.features.getOrElse(AuthorIdFeature, None).getOrElse(0L))
    val distinctNonSelfAuthorIds = authorIds.filter(_ != viewerId).distinct

    val idsRequest = createIdsRequest(
      userId = viewerId,
      relationshipTypes = Set(sg.RelationshipType.Following),
      targetIds = Some(distinctNonSelfAuthorIds)
    )

    socialGraphStitchClient
      .ids(request = idsRequest, requestContext = None)
      .map { idResult =>
        authorIds.map { authorId =>
          // Users cannot follow themselves but this is in network by definition
          val isSelfTweet = authorId == viewerId
          val inNetworkAuthorIds = idResult.ids.toSet
          val isInNetwork = isSelfTweet || inNetworkAuthorIds.contains(authorId) || authorId == 0L
          FeatureMapBuilder().add(InNetworkFeature, isInNetwork).build()
        }
      }
  }

  private def createIdsRequest(
    userId: Long,
    relationshipTypes: Set[sg.RelationshipType],
    targetIds: Option[Seq[Long]] = None
  ): sg.IdsRequest = sg.IdsRequest(
    relationshipTypes.map { relationshipType =>
      sg.SrcRelationship(userId, relationshipType, targets = targetIds)
    }.toSeq,
    Some(sg.PageRequest(selectAll = Some(true)))
  )
}

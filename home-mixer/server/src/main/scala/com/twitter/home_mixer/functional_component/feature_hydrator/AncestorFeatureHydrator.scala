package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.AncestorsFeature
import com.twitter.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.tweetconvosvc.tweet_ancestor.{thriftscala => ta}
import com.twitter.tweetconvosvc.{thriftscala => tcs}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AncestorFeatureHydrator @Inject() (
  conversationServiceClient: tcs.ConversationService.MethodPerEndpoint)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("Ancestor")

  override val features: Set[Feature[_, _]] = Set(AncestorsFeature)

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]],
  ): Stitch[Seq[FeatureMap]] = OffloadFuturePools.offloadFuture {
    val candidatesWithReplies = candidates.collect {
      case candidate if candidate.features.getOrElse(InReplyToTweetIdFeature, None).isDefined =>
        candidate.candidate.id
    }
    val candidateIsReplyIndexMap = candidatesWithReplies.zipWithIndex.toMap
    val ancestorsRequest = tcs.GetAncestorsRequest(candidatesWithReplies)
    conversationServiceClient.getAncestors(ancestorsRequest).map { getAncestorsResponse =>
      candidates.map { candidate =>
        val resultIndex = candidateIsReplyIndexMap.get(candidate.candidate.id)
        val ancestors = resultIndex
          .map { index =>
            getAncestorsResponse.ancestors(index) match {
              case tcs.TweetAncestorsResult.TweetAncestors(ancestorsResult)
                  if ancestorsResult.nonEmpty =>
                ancestorsResult.head.ancestors ++ getTruncatedRootTweet(ancestorsResult.head)
              case _ => Seq.empty
            }
          }.getOrElse(Seq.empty)
        FeatureMap(AncestorsFeature, ancestors)
      }
    }
  }

  private def getTruncatedRootTweet(
    ancestors: ta.TweetAncestors,
  ): Option[ta.TweetAncestor] = {
    ancestors.conversationRootAuthorId.collect {
      case rootAuthorId
          if ancestors.state == ta.ReplyState.Partial &&
            ancestors.ancestors.last.tweetId != ancestors.conversationId =>
        ta.TweetAncestor(ancestors.conversationId, rootAuthorId)
    }
  }
}

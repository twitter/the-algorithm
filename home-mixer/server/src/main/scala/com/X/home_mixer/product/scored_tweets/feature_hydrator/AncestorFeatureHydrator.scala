package com.X.home_mixer.product.scored_tweets.feature_hydrator

import com.X.home_mixer.model.HomeFeatures.AncestorsFeature
import com.X.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.X.product_mixer.component_library.model.candidate.TweetCandidate
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.util.OffloadFuturePools
import com.X.stitch.Stitch
import com.X.tweetconvosvc.tweet_ancestor.{thriftscala => ta}
import com.X.tweetconvosvc.{thriftscala => tcs}
import com.X.util.Future
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AncestorFeatureHydrator @Inject() (
  conversationServiceClient: tcs.ConversationService.MethodPerEndpoint)
    extends CandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("Ancestor")

  override val features: Set[Feature[_, _]] = Set(AncestorsFeature)

  private val DefaultFeatureMap = FeatureMapBuilder().add(AncestorsFeature, Seq.empty).build()

  override def apply(
    query: PipelineQuery,
    candidate: TweetCandidate,
    existingFeatures: FeatureMap
  ): Stitch[FeatureMap] = OffloadFuturePools.offloadFuture {
    if (existingFeatures.getOrElse(InReplyToTweetIdFeature, None).isDefined) {
      val ancestorsRequest = tcs.GetAncestorsRequest(Seq(candidate.id))
      conversationServiceClient.getAncestors(ancestorsRequest).map { getAncestorsResponse =>
        val ancestors = getAncestorsResponse.ancestors.headOption
          .collect {
            case tcs.TweetAncestorsResult.TweetAncestors(ancestorsResult)
                if ancestorsResult.nonEmpty =>
              ancestorsResult.head.ancestors ++ getTruncatedRootTweet(ancestorsResult.head)
          }.getOrElse(Seq.empty)

        FeatureMapBuilder().add(AncestorsFeature, ancestors).build()
      }
    } else Future.value(DefaultFeatureMap)
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

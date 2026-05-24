package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.functional_component.decorator.ForYouTweetCandidateDecorator
import com.twitter.home_mixer.functional_component.feature_hydrator.BasketballContextFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.InNetworkFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.NamesFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.PostContextFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.InvalidSubscriptionTweetFilter
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.param.HomeGlobalParams.EnableBasketballContextFeatureHydratorParam
import com.twitter.home_mixer.param.HomeGlobalParams.EnablePostContextFeatureHydratorParam
import com.twitter.home_mixer.product.for_you.candidate_source.ScoredTweetWithConversationMetadata
import com.twitter.home_mixer.product.for_you.candidate_source.ScoredTweetsProductCandidateSource
import com.twitter.home_mixer.product.for_you.feature_hydrator.FocalTweetFeatureHydrator
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.communities.CommunityNamesFeatureHydrator
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedBulkCandidateFeatureHydrator

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouScoredTweetsCandidatePipelineConfig @Inject() (
  scoredTweetsProductCandidateSource: ScoredTweetsProductCandidateSource,
  focalTweetFeatureHydrator: FocalTweetFeatureHydrator,
  namesFeatureHydrator: NamesFeatureHydrator,
  communityNamesFeatureHydrator: CommunityNamesFeatureHydrator,
  basketballContextFeatureHydrator: BasketballContextFeatureHydrator,
  postContextFeatureHydrator: PostContextFeatureHydrator,
  invalidSubscriptionTweetFilter: InvalidSubscriptionTweetFilter,
  forYouTweetCandidateDecorator: ForYouTweetCandidateDecorator)
    extends CandidatePipelineConfig[
      ForYouQuery,
      ForYouQuery,
      ScoredTweetWithConversationMetadata,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouScoredTweets")

  override val gates: Seq[Gate[ForYouQuery]] = Seq(RateLimitGate)

  override val candidateSource: CandidateSource[ForYouQuery, ScoredTweetWithConversationMetadata] =
    scoredTweetsProductCandidateSource

  override val queryTransformer: CandidatePipelineQueryTransformer[ForYouQuery, ForYouQuery] =
    identity

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[ScoredTweetWithConversationMetadata]
  ] = Seq(ForYouScoredTweetsResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    ScoredTweetWithConversationMetadata,
    TweetCandidate
  ] = { sourceResults => TweetCandidate(sourceResults.tweetId) }

  override val filters: Seq[Filter[ForYouQuery, TweetCandidate]] = Seq(
    invalidSubscriptionTweetFilter
  )

  override val postFilterFeatureHydration: Seq[
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(
    focalTweetFeatureHydrator,
    InNetworkFeatureHydrator,
    namesFeatureHydrator,
    communityNamesFeatureHydrator,
    ParamGatedBulkCandidateFeatureHydrator(
      EnableBasketballContextFeatureHydratorParam,
      basketballContextFeatureHydrator
    ),
    ParamGatedBulkCandidateFeatureHydrator(
      EnablePostContextFeatureHydratorParam,
      postContextFeatureHydrator
    ),
  )

  override val decorator: Option[CandidateDecorator[ForYouQuery, TweetCandidate]] =
    Some(forYouTweetCandidateDecorator.build())

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(),
    HomeMixerAlertConfig.BusinessHours.defaultEmptyResponseRateAlert(10, 20)
  )
}

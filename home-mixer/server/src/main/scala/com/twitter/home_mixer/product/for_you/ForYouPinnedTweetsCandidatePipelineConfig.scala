package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.functional_component.decorator.PinnedTweetBroadcastCandidateDecorator
import com.twitter.home_mixer.functional_component.feature_hydrator.GizmoduckAuthorFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.InNetworkFeatureHydrator
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.CurrentPinnedTweetFilter
import com.twitter.home_mixer.functional_component.filter.TweetHydrationFilter
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.product.for_you.candidate_source.BroadcastedPinnedTweetsCandidateSource
import com.twitter.home_mixer.product.for_you.candidate_source.PinnedTweetCandidate
import com.twitter.home_mixer.product.for_you.feature_hydrator.CurrentPinnedTweetFeatureHydrator
import com.twitter.home_mixer.product.for_you.filter.NotArticleFilter
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnablePinnedTweetsCandidatePipelineParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.PinnedTweetsModuleMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.response_transformer.PinnedTweetResponseFeatureTransformer
import com.twitter.home_mixer.product.for_you.scorer.PinnedTweetCandidateScorer
import com.twitter.product_mixer.component_library.filter.FeatureFilter
import com.twitter.product_mixer.component_library.gate.DefinedUserIdGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.scorer.Scorer
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelineservice.model.rich.EntityIdType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouPinnedTweetsCandidatePipelineConfig @Inject() (
  broadcastedPinnedTweetsCandidateSource: BroadcastedPinnedTweetsCandidateSource,
  currentPinnedTweetFeatureHydrator: CurrentPinnedTweetFeatureHydrator,
  gizmoduckAuthorFeatureHydrator: GizmoduckAuthorFeatureHydrator,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  pinnedTweetBroadcastCandidateDecorator: PinnedTweetBroadcastCandidateDecorator,
) extends CandidatePipelineConfig[
      ForYouQuery,
      ForYouQuery,
      PinnedTweetCandidate,
      TweetCandidate
    ] {

  private val InNetworkFilterId = "InNetwork"

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouPinnedTweets")

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(
    EnablePinnedTweetsCandidatePipelineParam)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    DefinedUserIdGate,
    RateLimitGate,
    TimelinesPersistenceStoreLastInjectionGate(
      PinnedTweetsModuleMinInjectionIntervalParam,
      EntityIdType.PinnedTweetsModule
    ),
  )

  override val queryFeatureHydration: Seq[QueryFeatureHydrator[PipelineQuery]] = Seq()

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ForYouQuery,
    ForYouQuery
  ] = identity

  override def candidateSource: CandidateSource[ForYouQuery, PinnedTweetCandidate] =
    broadcastedPinnedTweetsCandidateSource

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[PinnedTweetCandidate]
  ] = Seq(PinnedTweetResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    PinnedTweetCandidate,
    TweetCandidate
  ] = { sourceResult => TweetCandidate(id = sourceResult.tweetId) }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(
    tweetypieFeatureHydrator,
    InNetworkFeatureHydrator,
    currentPinnedTweetFeatureHydrator,
    gizmoduckAuthorFeatureHydrator
  )

  override val filters: Seq[Filter[ForYouQuery, TweetCandidate]] = Seq(
    TweetHydrationFilter,
    NotArticleFilter,
    FeatureFilter.fromFeature(FilterIdentifier(InNetworkFilterId), InNetworkFeature),
    CurrentPinnedTweetFilter
  )

  override def scorers: Seq[Scorer[ForYouQuery, TweetCandidate]] = Seq(PinnedTweetCandidateScorer)

  override val decorator: Option[
    CandidateDecorator[PipelineQuery, TweetCandidate]
  ] = Some(pinnedTweetBroadcastCandidateDecorator)
}

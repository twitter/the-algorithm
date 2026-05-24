package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.functional_component.decorator.VideoCarouselModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.ConsistentAspectRatioFilter
import com.twitter.home_mixer.functional_component.filter.TweetHydrationFilter
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.product.for_you.candidate_source.ScoredVideoTweetCandidate
import com.twitter.home_mixer.product.for_you.candidate_source.ScoredVideoTweetsCategorizedProductCandidateSource
import com.twitter.home_mixer.product.for_you.candidate_source.ScoredVideoTweetsProductCandidateSource
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableScoredVideoTweetsCandidatePipelineParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.VideoCarouselAllowHorizontalVideos
import com.twitter.home_mixer.product.for_you.param.ForYouParam.VideoCarouselAllowVerticalVideos
import com.twitter.home_mixer.product.for_you.param.ForYouParam.VideoCarouselEnableFooterParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.VideoTweetsModuleMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.response_transformer.ScoredVideoTweetResponseFeatureTransformer
import com.twitter.product_mixer.component_library.gate.DefinedUserIdGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelineservice.model.rich.EntityIdType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouScoredVideoTweetsCandidatePipelineConfig @Inject() (
  scoredVideoTweetsProductCandidateSource: ScoredVideoTweetsProductCandidateSource,
  scoredVideoTweetsCategorizedProductCandidateSource: ScoredVideoTweetsCategorizedProductCandidateSource,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  videoCarouselModuleCandidateDecorator: VideoCarouselModuleCandidateDecorator)
    extends CandidatePipelineConfig[
      ForYouQuery,
      ForYouQuery,
      ScoredVideoTweetCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouScoredVideoTweets")

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(
    EnableScoredVideoTweetsCandidatePipelineParam)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    DefinedUserIdGate,
    RateLimitGate,
    TimelinesPersistenceStoreLastInjectionGate(
      VideoTweetsModuleMinInjectionIntervalParam,
      EntityIdType.VideoCarouselModule
    ),
  )

  override val queryFeatureHydration: Seq[QueryFeatureHydrator[PipelineQuery]] = Seq()

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ForYouQuery,
    ForYouQuery
  ] = identity

  override def candidateSource: CandidateSource[ForYouQuery, ScoredVideoTweetCandidate] =
    scoredVideoTweetsCategorizedProductCandidateSource
//    scoredVideoTweetsProductCandidateSource

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[ScoredVideoTweetCandidate]
  ] = Seq(ScoredVideoTweetResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    ScoredVideoTweetCandidate,
    TweetCandidate
  ] = { sourceResult =>
    TweetCandidate(id = sourceResult.tweetId)
  }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(tweetypieFeatureHydrator)

  override val filters: Seq[Filter[ForYouQuery, TweetCandidate]] = Seq(
    TweetHydrationFilter,
    ConsistentAspectRatioFilter(
      allowVerticalVideosParam = VideoCarouselAllowVerticalVideos,
      allowHorizontalVideosParam = VideoCarouselAllowHorizontalVideos)
  )

  override val decorator: Option[
    CandidateDecorator[PipelineQuery, TweetCandidate]
  ] = Some(videoCarouselModuleCandidateDecorator.build(VideoCarouselEnableFooterParam))
}

package com.twitter.home_mixer.product.for_you

import com.twitter.frigate.bookmarks.{thriftscala => t}
import com.twitter.home_mixer.functional_component.decorator.urt.builder.TweetCarouselModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.decorator.urt.builder.TweetCarouselType
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.TweetHydrationFilter
import com.twitter.home_mixer.functional_component.filter.WeeklyBookmarkFilter
import com.twitter.home_mixer.functional_component.gate.BookmarksTimeGate
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.product.for_you.candidate_source.BookmarksCandidateSource
import com.twitter.home_mixer.product.for_you.param.ForYouParam.BookmarksModuleMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableBookmarksCandidatePipelineParam
import com.twitter.home_mixer.product.for_you.response_transformer.BookmarksResponseFeatureTransformer
import com.twitter.product_mixer.component_library.gate.DefinedUserIdGate
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
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
class ForYouBookmarksCandidatePipelineConfig @Inject() (
  bookmarksTimeGate: BookmarksTimeGate,
  bookmarksCandidateSource: BookmarksCandidateSource,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  tweetCarouselModuleCandidateDecorator: TweetCarouselModuleCandidateDecorator)
    extends CandidatePipelineConfig[
      PipelineQuery,
      Long,
      t.BookmarkedTweet,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouBookmarks")

  override val supportedClientParam: Option[FSParam[Boolean]] =
    Some(EnableBookmarksCandidatePipelineParam)

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    DefinedUserIdGate,
    RateLimitGate,
    bookmarksTimeGate,
    TimelinesPersistenceStoreLastInjectionGate(
      BookmarksModuleMinInjectionIntervalParam,
      EntityIdType.BookmarksModule
    )
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[PipelineQuery, Long] =
    query => query.getRequiredUserId

  override val candidateSource: BaseCandidateSource[Long, t.BookmarkedTweet] =
    bookmarksCandidateSource

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[t.BookmarkedTweet]
  ] = Seq(BookmarksResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[
    t.BookmarkedTweet,
    TweetCandidate
  ] = tweet => TweetCandidate(tweet.tweetId)

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[PipelineQuery, TweetCandidate, _]
  ] = Seq(tweetypieFeatureHydrator)

  override val filters: Seq[Filter[PipelineQuery, TweetCandidate]] =
    Seq(TweetHydrationFilter, WeeklyBookmarkFilter)

  override val decorator: Option[
    CandidateDecorator[PipelineQuery, TweetCandidate]
  ] = Some(tweetCarouselModuleCandidateDecorator.build(TweetCarouselType.BookmarkedTweets))
}

package com.twitter.home_mixer.product.for_you

import com.twitter.geoduck.util.country.CountryInfo
import com.twitter.home_mixer.functional_component.decorator.TuneFeedModuleCandidateDecorator
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.PreviouslySeenTweetsFilter
import com.twitter.home_mixer.functional_component.filter.TweetHydrationFilter
import com.twitter.home_mixer.functional_component.gate.RateLimitGate
import com.twitter.home_mixer.functional_component.gate.TimelinesPersistenceStoreLastInjectionGate
import com.twitter.home_mixer.model.HomeFeatures.CurrentDisplayedGrokTopicFeature
import com.twitter.home_mixer.product.for_you.candidate_source.TuneFeedCandidateSource
import com.twitter.home_mixer.product.for_you.candidate_source.TuneFeedRequest
import com.twitter.home_mixer.product.for_you.gate.TuneFeedModuleGate
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableTuneFeedCandidatePipelineParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.TuneFeedModuleMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.response_transformer.TuneFeedFeatureTransformer
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
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.timelineservice.model.rich.EntityIdType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouTuneFeedCandidatePipelineConfig @Inject() (
  tuneFeedCandidateSource: TuneFeedCandidateSource,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  tuneFeedModuleDecorator: TuneFeedModuleCandidateDecorator)
    extends CandidatePipelineConfig[
      ForYouQuery,
      TuneFeedRequest,
      TweetCandidate,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouTuneFeed")

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(
    EnableTuneFeedCandidatePipelineParam)

  override val gates: Seq[Gate[ForYouQuery]] = Seq(
    DefinedUserIdGate,
    RateLimitGate,
    TuneFeedModuleGate,
    TimelinesPersistenceStoreLastInjectionGate(
      TuneFeedModuleMinInjectionIntervalParam,
      EntityIdType.TuneFeedModule
    ),
  )

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ForYouQuery,
    TuneFeedRequest
  ] = { query =>
    // Safe .get, enforced in TuneFeedModuleGate
    val displayedGrokTopic =
      query.features.get.getOrElse(CurrentDisplayedGrokTopicFeature, None).get

    TuneFeedRequest(
      grokTopic = displayedGrokTopic._1,
      language = query.getLanguageCode,
      placeId = query.getCountryCode.flatMap(CountryInfo.lookupByCode).map(_.placeIdLong)
    )
  }

  override def candidateSource: BaseCandidateSource[TuneFeedRequest, TweetCandidate] =
    tuneFeedCandidateSource

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[TweetCandidate]
  ] = Seq(TuneFeedFeatureTransformer)

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(tweetypieFeatureHydrator)

  override val filters: Seq[Filter[ForYouQuery, TweetCandidate]] = Seq(
    TweetHydrationFilter,
    PreviouslySeenTweetsFilter
  )

  override val resultTransformer: CandidatePipelineResultsTransformer[
    TweetCandidate,
    TweetCandidate
  ] = identity

  override val decorator: Option[CandidateDecorator[ForYouQuery, TweetCandidate]] = Some(
    tuneFeedModuleDecorator)
}

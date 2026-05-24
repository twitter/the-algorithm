package com.twitter.home_mixer.product.list_tweets

import com.twitter.adserver.{thriftscala => ads}
import com.twitter.home_mixer.functional_component.decorator.builder.HomeAdsClientEventDetailsBuilder
import com.twitter.home_mixer.functional_component.gate.ExcludeSoftUserGate
import com.twitter.home_mixer.param.HomeGlobalParams
import com.twitter.home_mixer.param.HomeGlobalParams.EnableAdvertiserBrandSafetySettingsFeatureHydratorParam
import com.twitter.home_mixer.product.list_tweets.model.ListTweetsQuery
import com.twitter.home_mixer.product.list_tweets.param.ListTweetsParam.EnableAdsCandidatePipelineParam
import com.twitter.product_mixer.component_library.candidate_source.ads.AdsProdThriftCandidateSource
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.contextual_ref.ContextualTweetRefBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.ad.AdsCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.ads.AdvertiserBrandSafetySettingsFeatureHydrator
import com.twitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedCandidateFeatureHydrator
import com.twitter.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.twitter.product_mixer.component_library.model.candidate.ads.AdsCandidate
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.AdsDependentCandidatePipelineConfig
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.AdsDependentCandidatePipelineConfigBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.CountCandidatesFromPipelines
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.StaticAdsDisplayLocationBuilder
import com.twitter.product_mixer.component_library.pipeline.candidate.ads.ValidAdImpressionIdFilter
import com.twitter.product_mixer.core.functional_component.common.CandidateScope
import com.twitter.product_mixer.core.gate.ParamNotGate
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.rtf.safety_level.TimelineHomePromotedHydrationSafetyLevel
import com.twitter.product_mixer.core.model.marshalling.response.urt.contextual_ref.TweetHydrationContext
import com.twitter.timelines.injection.scribe.InjectionScribeUtil
import com.twitter.timelineservice.suggests.{thriftscala => st}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListTweetsAdsCandidatePipelineBuilder @Inject() (
  adsCandidatePipelineConfigBuilder: AdsDependentCandidatePipelineConfigBuilder,
  adsCandidateSource: AdsProdThriftCandidateSource,
  advertiserBrandSafetySettingsFeatureHydrator: AdvertiserBrandSafetySettingsFeatureHydrator[
    ListTweetsQuery,
    AdsCandidate
  ]) {

  private val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("ListTweetsAds")

  private val suggestType = st.SuggestType.Promoted

  private val clientEventInfoBuilder = ClientEventInfoBuilder(
    component = InjectionScribeUtil.scribeComponent(suggestType).get,
    detailsBuilder = Some(HomeAdsClientEventDetailsBuilder(Some(suggestType.name)))
  )

  private val contextualTweetRefBuilder = ContextualTweetRefBuilder(
    TweetHydrationContext(
      safetyLevelOverride = Some(TimelineHomePromotedHydrationSafetyLevel),
      outerTweetContext = None
    ))

  private val decorator = UrtItemCandidateDecorator(
    AdsCandidateUrtItemBuilder(
      tweetClientEventInfoBuilder = Some(clientEventInfoBuilder),
      contextualTweetRefBuilder = Some(contextualTweetRefBuilder)
    )
  )

  def build(
    organicCandidatePipelines: CandidateScope
  ): AdsDependentCandidatePipelineConfig[ListTweetsQuery] =
    adsCandidatePipelineConfigBuilder.build[ListTweetsQuery](
      adsCandidateSource = adsCandidateSource,
      identifier = identifier,
      adsDisplayLocationBuilder =
        StaticAdsDisplayLocationBuilder(ads.DisplayLocation.TimelineHomeReverseChron),
      countNumOrganicItems = CountCandidatesFromPipelines(organicCandidatePipelines),
      supportedClientParam = Some(EnableAdsCandidatePipelineParam),
      gates = Seq(
        ParamNotGate(
          name = "AdsDisableInjectionBasedOnUserRole",
          param = HomeGlobalParams.AdsDisableInjectionBasedOnUserRoleParam
        ),
        ExcludeSoftUserGate,
        NonEmptyCandidatesGate(organicCandidatePipelines)
      ),
      filters = Seq(ValidAdImpressionIdFilter),
      postFilterFeatureHydration = Seq(
        ParamGatedCandidateFeatureHydrator(
          EnableAdvertiserBrandSafetySettingsFeatureHydratorParam,
          advertiserBrandSafetySettingsFeatureHydrator
        )
      ),
      decorator = Some(decorator),
      urtRequest = Some(true),
    )
}

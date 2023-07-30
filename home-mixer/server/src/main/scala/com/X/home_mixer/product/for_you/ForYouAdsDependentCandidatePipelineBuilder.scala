package com.X.home_mixer.product.for_you

import com.X.adserver.{thriftscala => ads}
import com.X.home_mixer.functional_component.decorator.builder.HomeAdsClientEventDetailsBuilder
import com.X.home_mixer.functional_component.gate.ExcludeSoftUserGate
import com.X.home_mixer.model.HomeFeatures.TweetLanguageFeature
import com.X.home_mixer.model.HomeFeatures.TweetTextFeature
import com.X.home_mixer.param.HomeGlobalParams
import com.X.home_mixer.param.HomeGlobalParams.EnableAdvertiserBrandSafetySettingsFeatureHydratorParam
import com.X.home_mixer.product.for_you.model.ForYouQuery
import com.X.home_mixer.service.HomeMixerAlertConfig
import com.X.home_mixer.util.CandidatesUtil
import com.X.product_mixer.component_library.candidate_source.ads.AdsProdThriftCandidateSource
import com.X.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.X.product_mixer.component_library.decorator.urt.builder.contextual_ref.ContextualTweetRefBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.item.ad.AdsCandidateUrtItemBuilder
import com.X.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.X.product_mixer.component_library.feature_hydrator.candidate.ads.AdvertiserBrandSafetySettingsFeatureHydrator
import com.X.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedCandidateFeatureHydrator
import com.X.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.X.product_mixer.component_library.model.candidate.ads.AdsCandidate
import com.X.product_mixer.component_library.pipeline.candidate.ads.AdsDependentCandidatePipelineConfig
import com.X.product_mixer.component_library.pipeline.candidate.ads.AdsDependentCandidatePipelineConfigBuilder
import com.X.product_mixer.component_library.pipeline.candidate.ads.CountTruncatedItemCandidatesFromPipelines
import com.X.product_mixer.component_library.pipeline.candidate.ads.StaticAdsDisplayLocationBuilder
import com.X.product_mixer.component_library.pipeline.candidate.ads.TruncatedPipelineScopedOrganicItems
import com.X.product_mixer.component_library.pipeline.candidate.ads.ValidAdImpressionIdFilter
import com.X.product_mixer.core.functional_component.common.CandidateScope
import com.X.product_mixer.core.gate.ParamNotGate
import com.X.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.X.product_mixer.core.model.marshalling.response.rtf.safety_level.TimelineHomePromotedHydrationSafetyLevel
import com.X.product_mixer.core.model.marshalling.response.urt.contextual_ref.TweetHydrationContext
import com.X.timelines.injection.scribe.InjectionScribeUtil
import com.X.timelineservice.suggests.{thriftscala => st}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForYouAdsDependentCandidatePipelineBuilder @Inject() (
  adsCandidatePipelineConfigBuilder: AdsDependentCandidatePipelineConfigBuilder,
  adsCandidateSource: AdsProdThriftCandidateSource,
  advertiserBrandSafetySettingsFeatureHydrator: AdvertiserBrandSafetySettingsFeatureHydrator[
    ForYouQuery,
    AdsCandidate
  ]) {

  private val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouAdsDependent")

  private val suggestType = st.SuggestType.Promoted

  private val MaxOrganicTweets = 35

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
    ))

  private val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(),
    HomeMixerAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )

  def build(
    organicCandidatePipelines: CandidateScope
  ): AdsDependentCandidatePipelineConfig[ForYouQuery] =
    adsCandidatePipelineConfigBuilder.build[ForYouQuery](
      adsCandidateSource = adsCandidateSource,
      identifier = identifier,
      adsDisplayLocationBuilder = StaticAdsDisplayLocationBuilder(ads.DisplayLocation.TimelineHome),
      getOrganicItems = TruncatedPipelineScopedOrganicItems(
        pipelines = organicCandidatePipelines,
        textFeature = TweetTextFeature,
        languageFeature = TweetLanguageFeature,
        ordering = CandidatesUtil.scoreOrdering,
        maxCount = MaxOrganicTweets
      ),
      countNumOrganicItems =
        CountTruncatedItemCandidatesFromPipelines(organicCandidatePipelines, MaxOrganicTweets),
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
      alerts = alerts,
      urtRequest = Some(true)
    )
}

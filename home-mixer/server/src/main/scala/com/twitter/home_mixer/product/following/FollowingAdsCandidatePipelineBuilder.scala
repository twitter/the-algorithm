package com.ExTwitter.home_mixer.product.following

import com.ExTwitter.adserver.{thriftscala => ads}
import com.ExTwitter.home_mixer.functional_component.decorator.builder.HomeAdsClientEventDetailsBuilder
import com.ExTwitter.home_mixer.functional_component.gate.ExcludeSoftUserGate
import com.ExTwitter.home_mixer.model.HomeFeatures.TweetLanguageFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.TweetTextFeature
import com.ExTwitter.home_mixer.param.HomeGlobalParams
import com.ExTwitter.home_mixer.param.HomeGlobalParams.EnableAdvertiserBrandSafetySettingsFeatureHydratorParam
import com.ExTwitter.home_mixer.product.following.model.FollowingQuery
import com.ExTwitter.home_mixer.product.following.param.FollowingParam.EnableAdsCandidatePipelineParam
import com.ExTwitter.home_mixer.product.following.param.FollowingParam.EnableFastAds
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig
import com.ExTwitter.product_mixer.component_library.candidate_source.ads.AdsProdThriftCandidateSource
import com.ExTwitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.contextual_ref.ContextualTweetRefBuilder
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.item.ad.AdsCandidateUrtItemBuilder
import com.ExTwitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.ExTwitter.product_mixer.component_library.feature_hydrator.candidate.ads.AdvertiserBrandSafetySettingsFeatureHydrator
import com.ExTwitter.product_mixer.component_library.feature_hydrator.candidate.param_gated.ParamGatedCandidateFeatureHydrator
import com.ExTwitter.product_mixer.component_library.gate.NonEmptyCandidatesGate
import com.ExTwitter.product_mixer.component_library.model.candidate.ads.AdsCandidate
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.ads.AdsDependentCandidatePipelineConfig
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.ads.AdsDependentCandidatePipelineConfigBuilder
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.ads.CountCandidatesFromPipelines
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.ads.PipelineScopedOrganicItems
import com.ExTwitter.product_mixer.component_library.pipeline.candidate.ads.ValidAdImpressionIdFilter
import com.ExTwitter.product_mixer.core.functional_component.common.CandidateScope
import com.ExTwitter.product_mixer.core.gate.ParamNotGate
import com.ExTwitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.ExTwitter.product_mixer.core.model.marshalling.response.rtf.safety_level.TimelineHomePromotedHydrationSafetyLevel
import com.ExTwitter.product_mixer.core.model.marshalling.response.urt.contextual_ref.TweetHydrationContext
import com.ExTwitter.timelines.injection.scribe.InjectionScribeUtil
import com.ExTwitter.timelineservice.suggests.{thriftscala => st}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowingAdsCandidatePipelineBuilder @Inject() (
  adsCandidatePipelineConfigBuilder: AdsDependentCandidatePipelineConfigBuilder,
  adsCandidateSource: AdsProdThriftCandidateSource,
  advertiserBrandSafetySettingsFeatureHydrator: AdvertiserBrandSafetySettingsFeatureHydrator[
    FollowingQuery,
    AdsCandidate
  ]) {

  private val identifier: CandidatePipelineIdentifier = CandidatePipelineIdentifier("FollowingAds")

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
    ))

  private val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(),
    HomeMixerAlertConfig.BusinessHours.defaultEmptyResponseRateAlert()
  )

  def build(
    organicCandidatePipelines: CandidateScope
  ): AdsDependentCandidatePipelineConfig[FollowingQuery] =
    adsCandidatePipelineConfigBuilder.build[FollowingQuery](
      adsCandidateSource = adsCandidateSource,
      identifier = identifier,
      adsDisplayLocationBuilder = query =>
        if (query.params.getBoolean(EnableFastAds)) ads.DisplayLocation.TimelineHomeReverseChron
        else ads.DisplayLocation.TimelineHome,
      getOrganicItems = PipelineScopedOrganicItems(
        pipelines = organicCandidatePipelines,
        textFeature = TweetTextFeature,
        languageFeature = TweetLanguageFeature
      ),
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
      alerts = alerts,
      urtRequest = Some(true),
    )
}
